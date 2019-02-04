from py4j.java_gateway import java_import, JavaObject
from py4j.java_collections import JavaList

from keanu.algorithm._proposal_distribution import ProposalDistribution
from keanu.context import KeanuContext
from keanu.tensor import Tensor
from keanu.vertex.base import Vertex
from keanu.net import BayesNet, ProbabilisticModel, ProbabilisticModelWithGradient
from typing import Any, Iterable, Dict, List, Tuple, Generator, cast
from keanu.vartypes import sample_types, sample_generator_types, numpy_types
from keanu.plots import traceplot
from itertools import tee

k = KeanuContext()

java_import(k.jvm_view(), "io.improbable.keanu.algorithms.mcmc.MetropolisHastings")
java_import(k.jvm_view(), "io.improbable.keanu.algorithms.mcmc.nuts.NUTS")
java_import(k.jvm_view(), "io.improbable.keanu.algorithms.mcmc.RollBackToCachedValuesOnRejection")


class PosteriorSamplingAlgorithm:

    def __init__(self, sampler: JavaObject):
        self._sampler = sampler

    def get_sampler(self) -> JavaObject:
        return self._sampler


class MetropolisHastingsSampler(PosteriorSamplingAlgorithm):

    def __init__(self,
                 proposal_distribution: str,
                 latents: Iterable[Vertex],
                 proposal_listeners=[],
                 proposal_distribution_sigma: numpy_types = None):

        if (proposal_distribution is None and len(proposal_listeners) > 0):
            raise TypeError("If you pass in proposal_listeners you must also specify proposal_distribution")

        builder: JavaObject = k.jvm_view().MetropolisHastings.builder()

        latents, latents_copy = tee(latents)

        proposal_distribution_object = ProposalDistribution(
            type_=proposal_distribution,
            sigma=proposal_distribution_sigma,
            latents=latents_copy,
            listeners=proposal_listeners)

        rejection_strategy = k.jvm_view().RollBackToCachedValuesOnRejection(k.to_java_object_list(latents))

        builder = builder.proposalDistribution(proposal_distribution_object.unwrap())
        builder = builder.rejectionStrategy(rejection_strategy)

        super().__init__(builder.build())


class NUTSSampler(PosteriorSamplingAlgorithm):

    def __init__(self,
                 adapt_count: int = None,
                 target_acceptance_prob: float = None,
                 adapt_enabled: bool = None,
                 initial_step_size: float = None,
                 max_tree_height: int = None):

        builder: JavaObject = k.jvm_view().NUTS.builder()

        if adapt_count is not None:
            builder.adaptCount(adapt_count)

        if target_acceptance_prob is not None:
            builder.targetAcceptanceProb(target_acceptance_prob)

        if adapt_enabled is not None:
            builder.adaptEnabled(adapt_enabled)

        if initial_step_size is not None:
            builder.initialStepSize(initial_step_size)

        if max_tree_height is not None:
            builder.maxTreeHeight(max_tree_height)

        super().__init__(builder.build())


def sample(net: BayesNet,
           sample_from: Iterable[Vertex],
           sampling_algorithm: PosteriorSamplingAlgorithm = None,
           draws: int = 500,
           drop: int = 0,
           down_sample_interval: int = 1,
           plot: bool = False,
           ax: Any = None) -> sample_types:

    sample_from_copy = list(sample_from)

    __check_if_vertices_are_labelled(sample_from_copy)

    if sampling_algorithm is None:
        sampling_algorithm = MetropolisHastingsSampler(proposal_distribution="prior", latents=sample_from_copy)

    vertices_unwrapped: JavaList = k.to_java_object_list(sample_from_copy)

    probabilistic_model = ProbabilisticModel(net) if isinstance(
        sampling_algorithm, MetropolisHastingsSampler) else ProbabilisticModelWithGradient(net)

    network_samples: JavaObject = sampling_algorithm.get_sampler().getPosteriorSamples(
        probabilistic_model.unwrap(), vertices_unwrapped, draws).drop(drop).downSample(down_sample_interval)

    vertex_samples = {
        # label can't be None. See __check_if_vertices_are_labelled
        cast(str, Vertex._get_python_label(vertex_unwrapped)): list(
            map(Tensor._to_ndarray,
                network_samples.get(vertex_unwrapped).asList())) for vertex_unwrapped in vertices_unwrapped
    }

    if plot:
        traceplot(vertex_samples, ax=ax)

    return vertex_samples


def generate_samples(net: BayesNet,
                     sample_from: Iterable[Vertex],
                     sampling_algorithm: PosteriorSamplingAlgorithm = None,
                     drop: int = 0,
                     down_sample_interval: int = 1,
                     live_plot: bool = False,
                     refresh_every: int = 100,
                     ax: Any = None) -> sample_generator_types:

    sample_from_copy = list(sample_from)

    __check_if_vertices_are_labelled(sample_from_copy)

    if sampling_algorithm is None:
        sampling_algorithm = MetropolisHastingsSampler(proposal_distribution="prior", latents=sample_from_copy)

    vertices_unwrapped: JavaList = k.to_java_object_list(sample_from_copy)

    probabilistic_model = ProbabilisticModel(net) if isinstance(
        sampling_algorithm, MetropolisHastingsSampler) else ProbabilisticModelWithGradient(net)
    samples: JavaObject = sampling_algorithm.get_sampler().generatePosteriorSamples(probabilistic_model.unwrap(),
                                                                                    vertices_unwrapped)
    samples = samples.dropCount(drop).downSampleInterval(down_sample_interval)
    sample_iterator: JavaObject = samples.stream().iterator()
    return _samples_generator(
        sample_iterator, vertices_unwrapped, live_plot=live_plot, refresh_every=refresh_every, ax=ax)


def _samples_generator(sample_iterator: JavaObject, vertices_unwrapped: JavaList, live_plot: bool, refresh_every: int,
                       ax: Any) -> sample_generator_types:
    traces = []
    x0 = 0
    while (True):
        network_sample = sample_iterator.next()
        sample = {
            # label can't be None. See __check_if_vertices_are_labelled
            cast(str, Vertex._get_python_label(vertex_unwrapped)): Tensor._to_ndarray(
                network_sample.get(vertex_unwrapped)) for vertex_unwrapped in vertices_unwrapped
        }

        if live_plot:
            traces.append(sample)
            if len(traces) % refresh_every == 0:
                joined_trace = {k: [t[k] for t in traces] for k in sample.keys()}
                if ax is None:
                    ax = traceplot(joined_trace, x0=x0)
                else:
                    traceplot(joined_trace, ax=ax, x0=x0)
                x0 += refresh_every
                traces = []

        yield sample


def __check_if_vertices_are_labelled(vertices: List[Vertex]) -> None:
    if any(vertex.get_label() == None for vertex in vertices):
        raise ValueError("Vertices in sample_from must be labelled.")
