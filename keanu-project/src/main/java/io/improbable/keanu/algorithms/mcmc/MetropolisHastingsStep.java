package io.improbable.keanu.algorithms.mcmc;

import io.improbable.keanu.algorithms.graphtraversal.VertexValuePropagation;
import io.improbable.keanu.algorithms.mcmc.proposal.Proposal;
import io.improbable.keanu.algorithms.mcmc.proposal.ProposalDistribution;
import io.improbable.keanu.network.LambdaSection;
import io.improbable.keanu.network.NetworkSnapshot;
import io.improbable.keanu.vertices.ProbabilityCalculator;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.dbl.KeanuRandom;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
class MetropolisHastingsStep {

    //Temperature for standard MH step accept/reject calculation
    private static final double DEFAULT_TEMPERATURE = 1.0;

    private final ProposalDistribution proposalDistribution;
    private final boolean useCacheOnRejection;
    private final Map<Vertex, LambdaSection> affectedVerticesCache;
    private final KeanuRandom random;

    /**
     * @param latentVertices       Vertices that are unknown/hidden variables
     * @param proposalDistribution The proposal distribution
     * @param useCacheOnRejection  True if caching values of the network such that recalculation isn't required
     *                             on step rejection
     * @param random               Source of randomness
     */
    MetropolisHastingsStep(List<? extends Vertex> latentVertices,
                           ProposalDistribution proposalDistribution,
                           boolean useCacheOnRejection,
                           KeanuRandom random) {

        this.proposalDistribution = proposalDistribution;
        this.useCacheOnRejection = useCacheOnRejection;
        this.random = random;
        this.affectedVerticesCache = createVerticesAffectedByCache(
            latentVertices,
            useCacheOnRejection
        );
    }

    public StepResult step(final Set<Vertex> chosenVertices,
                           final double logProbabilityBeforeStep) {
        return step(chosenVertices, logProbabilityBeforeStep, DEFAULT_TEMPERATURE);
    }

    /**
     * @param chosenVertices           vertices to get a proposed change for
     * @param logProbabilityBeforeStep The log of the previous state's probability
     * @param temperature              Temperature for simulated annealing. This
     *                                 should be constant if no annealing is wanted
     * @return the log probability of the network after either accepting or rejecting the sample
     */
    public StepResult step(final Set<Vertex> chosenVertices,
                           final double logProbabilityBeforeStep,
                           final double temperature) {

        log.trace(String.format("Chosen vertices: %s", chosenVertices.stream()
            .map(Vertex::toString)
            .collect(Collectors.toList())));
        final double affectedVerticesLogProbOld = sumLogProbabilityOfAffected(chosenVertices, affectedVerticesCache);

        NetworkSnapshot preProposalSnapshot = null;
        if (useCacheOnRejection) {
            preProposalSnapshot = getSnapshotOfAllAffectedVertices(chosenVertices, affectedVerticesCache);
        }

        Proposal proposal = proposalDistribution.getProposal(chosenVertices, random);
        proposal.apply();
        VertexValuePropagation.cascadeUpdate(chosenVertices);

        final double affectedVerticesLogProbNew = sumLogProbabilityOfAffected(chosenVertices, affectedVerticesCache);

        if (!ProbabilityCalculator.isImpossibleLogProb(affectedVerticesLogProbNew)) {

            final double logProbabilityDelta = affectedVerticesLogProbNew - affectedVerticesLogProbOld;
            final double logProbabilityAfterStep = logProbabilityBeforeStep + logProbabilityDelta;

            final double pqxOld = proposalDistribution.logProbAtFromGivenTo(proposal);
            final double pqxNew = proposalDistribution.logProbAtToGivenFrom(proposal);

            final double annealFactor = (1.0 / temperature);
            final double hastingsCorrection = pqxOld - pqxNew;
            final double logR = annealFactor * logProbabilityDelta + hastingsCorrection;
            final double r = Math.exp(logR);

            final boolean shouldAccept = r >= random.nextDouble();


            if (shouldAccept) {
                log.trace(String.format("ACCEPT %.4f", logR));
                log.trace(String.format("New log prob = %.4f", logProbabilityAfterStep));
                return new StepResult(true, logProbabilityAfterStep);
            } else {
                log.trace(String.format("REJECT %.4f", logR));
            }
        }

        proposal.reject();

        if (useCacheOnRejection) {
            preProposalSnapshot.apply();
        } else {
            VertexValuePropagation.cascadeUpdate(chosenVertices);
        }

        return new StepResult(false, logProbabilityBeforeStep);
    }

    private static NetworkSnapshot getSnapshotOfAllAffectedVertices(final Set<Vertex> chosenVertices,
                                                                    final Map<Vertex, LambdaSection> affectedVertices) {

        Set<Vertex> allAffectedVertices = new HashSet<>();
        for (Vertex vertex : chosenVertices) {
            allAffectedVertices.addAll(affectedVertices.get(vertex).getAllVertices());
        }

        return NetworkSnapshot.create(allAffectedVertices);
    }

    private static double sumLogProbabilityOfAffected(Set<Vertex> vertices,
                                                      Map<Vertex, LambdaSection> affectedVertices) {
        Set<Vertex> lambdaSectionUnion = new HashSet<>();
        for (Vertex v : vertices) {
            lambdaSectionUnion.addAll(affectedVertices.get(v).getLatentAndObservedVertices());
        }
        return ProbabilityCalculator.calculateLogProbFor(lambdaSectionUnion);
    }

    /**
     * This creates a cache of potentially all vertices downstream to an observed or probabilistic vertex
     * from each latent vertex. If useCacheOnRejection is false then only the downstream observed or probabilistic
     * is cached.
     *
     * @param latentVertices      The latent vertices to create a cache for
     * @param useCacheOnRejection Whether or not to cache the entire downstream set or just the observed/probabilistic
     * @return A vertex to Lambda Section map that represents the downstream Lambda Section for each latent vertex.
     * This Lambda Section may include all of the nonprobabilistic vertices if useCacheOnRejection is enabled.
     */
    private static Map<Vertex, LambdaSection> createVerticesAffectedByCache(List<? extends Vertex> latentVertices,
                                                                            boolean useCacheOnRejection) {
        return latentVertices.stream()
            .collect(Collectors.toMap(
                v -> v,
                v -> LambdaSection.getDownstreamLambdaSection(v, useCacheOnRejection)
            ));
    }

    @Value
    static class StepResult {
        boolean accepted;
        double logProbabilityAfterStep;
    }

}
