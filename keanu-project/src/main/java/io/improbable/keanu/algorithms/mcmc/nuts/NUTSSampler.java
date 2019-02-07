package io.improbable.keanu.algorithms.mcmc.nuts;

import io.improbable.keanu.KeanuRandom;
import io.improbable.keanu.algorithms.NetworkSample;
import io.improbable.keanu.algorithms.ProbabilisticModelWithGradient;
import io.improbable.keanu.algorithms.Statistics;
import io.improbable.keanu.algorithms.Variable;
import io.improbable.keanu.algorithms.VariableReference;
import io.improbable.keanu.algorithms.mcmc.SamplingAlgorithm;
import io.improbable.keanu.tensor.dbl.DoubleTensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Algorithm 6: "No-U-Turn Sampler with Dual Averaging".
 * The No-U-Turn Sampler: Adaptively Setting Path Lengths in Hamiltonian Monte Carlo
 * https://arxiv.org/pdf/1111.4246.pdf
 */
class NUTSSampler implements SamplingAlgorithm {

    private final KeanuRandom random;
    private final List<? extends Variable<DoubleTensor, ?>> latentVariables;
    private final List<? extends Variable> sampleFromVariables;
    private final int maxTreeHeight;
    private final boolean adaptEnabled;
    private final Stepsize stepsize;
    private Tree tree;
    private final ProbabilisticModelWithGradient logProbGradientCalculator;
    private final Statistics statistics;
    private final boolean saveStatistics;
    private int sampleNum;

    /**
     * @param sampleFromVariables       variables to sample from
     * @param latentVariables           variables that represent latent variables
     * @param logProbGradientCalculator gradient calculator for diff of log prob with respect to latents
     * @param adaptEnabled              enable the NUTS step size adaptation
     * @param stepsize                  configuration for tuning the stepsize, if adaptEnabled
     * @param tree                      initial tree that will contain the state of the tree build
     * @param maxTreeHeight             The largest tree height before stopping the hamilitonian process
     * @param random                    the source of randomness
     * @param statistics                the sampler statistics
     * @param saveStatistics            whether to record statistics
     */
    public NUTSSampler(List<? extends Variable> sampleFromVariables,
                       List<? extends Variable<DoubleTensor, ?>> latentVariables,
                       ProbabilisticModelWithGradient logProbGradientCalculator,
                       boolean adaptEnabled,
                       Stepsize stepsize,
                       Tree tree,
                       int maxTreeHeight,
                       KeanuRandom random,
                       Statistics statistics,
                       boolean saveStatistics) {

        this.sampleFromVariables = sampleFromVariables;
        this.latentVariables = latentVariables;
        this.logProbGradientCalculator = logProbGradientCalculator;

        this.tree = tree;
        this.stepsize = stepsize;
        this.maxTreeHeight = maxTreeHeight;
        this.adaptEnabled = adaptEnabled;

        this.random = random;
        this.statistics = statistics;
        this.saveStatistics = saveStatistics;

        this.sampleNum = 1;
    }

    @Override
    public void sample(Map<VariableReference, List<?>> samples, List<Double> logOfMasterPForEachSample) {
        step();
        addSampleFromCache(samples, tree.getProposal().getSample());
        logOfMasterPForEachSample.add(tree.getProposal().getLogProb());
    }

    @Override
    public NetworkSample sample() {
        step();
        return new NetworkSample(tree.getProposal().getSample(), tree.getProposal().getLogProb());
    }

    @Override
    public void step() {

        Map<VariableReference, DoubleTensor> initialMomentum = new HashMap<>();
        initializeMomentumForEachVariable(latentVariables, initialMomentum, random);

        Proposal previousProposal = tree.getProposal();

        Leapfrog startState = new Leapfrog(
            previousProposal.getPosition(),
            initialMomentum,
            previousProposal.getGradient(),
            previousProposal.getLogProb()
        );

        Proposal initialProposal = new Proposal(
            previousProposal.getPosition(),
            previousProposal.getGradient(),
            previousProposal.getSample(),
            previousProposal.getEnergy(),
            1.0,
            previousProposal.getLogProb()
        );

        tree = new Tree(startState, initialProposal, 0.0, true, 0.0, 1, startState.getEnergy());

        int treeHeight = 0;

        while (tree.shouldContinue() && treeHeight < maxTreeHeight) {

            //build tree direction -1 = backwards OR 1 = forwards
            int buildDirection = random.nextBoolean() ? 1 : -1;

            Tree otherHalfTree = Tree.buildOtherHalfOfTree(
                tree,
                latentVariables,
                logProbGradientCalculator,
                sampleFromVariables,
                buildDirection,
                treeHeight,
                stepsize.getStepSize(),
                random
            );

            treeHeight++;
            tree.setAcceptSum(tree.getAcceptSum() + otherHalfTree.getAcceptSum());
            tree.setTreeSize(tree.getTreeSize() + otherHalfTree.getTreeSize());

            if (!otherHalfTree.shouldContinue()) {
                break;
            }

            if (Tree.acceptOtherPositionWithProbability(otherHalfTree.getLogSize() - tree.getLogSize(), random)) {
                tree.setProposal(otherHalfTree.getProposal());
            }

            final double newLogSize = Tree.logSumExp(tree.getLogSize(), otherHalfTree.getLogSize());

            if (Tree.isNotUsableNumber(newLogSize)) {
                throw new IllegalStateException("New logsize is " + newLogSize);
            }

            tree.setLogSize(newLogSize);

            tree.setPSum(Tree.add(tree.getPSum(), otherHalfTree.getPSum()));

            tree.setShouldContinueFlag(
                Tree.isNotUTurning(
                    tree.getLeapfrogForward().getVelocity(),
                    tree.getLeapfrogBackward().getVelocity(),
                    tree.getPSum()
                )
            );

        }

        if (saveStatistics) {
            recordSamplerStatistics();
        }

        if (this.adaptEnabled) {
            stepsize.adaptStepSize(tree, sampleNum);
        }

        sampleNum++;
    }

    private void recordSamplerStatistics() {
        stepsize.save(statistics);
        tree.save(statistics);
    }

    private static void initializeMomentumForEachVariable(List<? extends Variable<DoubleTensor, ?>> variables,
                                                          Map<VariableReference, DoubleTensor> momentums,
                                                          KeanuRandom random) {
        for (Variable<DoubleTensor, ?> variable : variables) {
            momentums.put(variable.getReference(), random.nextGaussian(variable.getShape()));
        }
    }

    /**
     * This is used to save of the sample from the uniformly chosen acceptedPosition position
     *
     * @param samples      samples taken already
     * @param cachedSample a cached sample from before leapfrog
     */
    private static void addSampleFromCache(Map<VariableReference, List<?>> samples, Map<VariableReference, ?> cachedSample) {
        for (Map.Entry<VariableReference, ?> sampleEntry : cachedSample.entrySet()) {
            addSampleForVariable(sampleEntry.getKey(), sampleEntry.getValue(), samples);
        }
    }

    private static <T> void addSampleForVariable(VariableReference id, T value, Map<VariableReference, List<?>> samples) {
        List<T> samplesForVariable = (List<T>) samples.computeIfAbsent(id, v -> new ArrayList<T>());
        samplesForVariable.add(value);
    }

}
