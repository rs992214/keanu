package io.improbable.keanu.algorithms.mcmc.nuts;

import io.improbable.keanu.KeanuRandom;
import io.improbable.keanu.algorithms.ProbabilisticModelWithGradient;
import io.improbable.keanu.algorithms.Statistics;
import io.improbable.keanu.algorithms.Variable;
import io.improbable.keanu.algorithms.VariableReference;
import io.improbable.keanu.tensor.dbl.DoubleTensor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;

import static io.improbable.keanu.algorithms.mcmc.SamplingAlgorithm.takeSample;
import static io.improbable.keanu.algorithms.mcmc.nuts.VariableValues.add;


/**
 * Used by NUTS as a balanced binary tree to compute and store information
 * about leapfrogs that are taken forwards and backwards in space.
 * <p>
 * The tree is reset for each sample.
 */
class Tree {

    private final ProbabilisticModelWithGradient logProbGradientCalculator;

    private final LeapfrogIntegrator leapfrogIntegrator;

    private final List<? extends Variable> sampleFromVariables;

    private final KeanuRandom random;

    /**
     * The forward most state in the tree
     */
    @Getter
    private LeapfrogState forward;

    /**
     * The backward most state in the tree
     */
    @Getter
    private LeapfrogState backward;

    /**
     * The accepted position in the tree
     */
    @Getter
    private Proposal proposal;

    /**
     * Sum of momentum over all steps in tree
     */
    @Getter
    private Map<VariableReference, DoubleTensor> sumMomentum;

    /**
     * The energy at the start state
     */
    @Getter
    private final double startEnergy;

    /**
     * Maximum energy change on a step before the step is considered divergent
     */
    @Getter
    private final double maxEnergyChange;

    /**
     * log sum of negative energy change
     */
    @Getter
    private double logSumWeight;

    /**
     * A summation of the metropolis acceptance probability over each step in tree.
     */
    @Getter
    private double sumMetropolisAcceptanceProbability;

    /**
     * Total number of steps in tree
     */
    @Getter
    private int treeSize;

    /**
     * Height of binary tree as of last growth
     */
    @Getter
    private int treeHeight;

    /**
     * True if the tree stopped growth if the max energy change was exceeded on one of the steps
     */
    @Getter
    private boolean diverged;

    /**
     * True if the tree stopped growth due to u-turn
     */
    @Getter
    private boolean uTurned;

    /**
     * @param startState                The leap frog for is initial step in the tree.
     * @param proposal                  The current accepted position.
     * @param logProbGradientCalculator logProb gradient and logProb function
     * @param sampleFromVariables       Variables to sample from at proposals
     * @param random                    Source of randomness
     */
    public Tree(LeapfrogState startState,
                Proposal proposal,
                double maxEnergyChange,
                ProbabilisticModelWithGradient logProbGradientCalculator,
                LeapfrogIntegrator leapfrogIntegrator,
                List<? extends Variable> sampleFromVariables,
                KeanuRandom random) {

        this.forward = startState;
        this.backward = startState;
        this.proposal = proposal;
        this.maxEnergyChange = maxEnergyChange;
        this.logProbGradientCalculator = logProbGradientCalculator;
        this.leapfrogIntegrator = leapfrogIntegrator;
        this.sampleFromVariables = sampleFromVariables;
        this.random = random;
        this.sumMomentum = startState.getMomentum();
        this.startEnergy = startState.getEnergy();
        this.logSumWeight = 0.0;
        this.sumMetropolisAcceptanceProbability = 0.0;
        this.treeSize = 0;
        this.treeHeight = 0;
        this.diverged = false;
        this.uTurned = false;
    }

    public void grow(int buildDirection,
                     double timeStep) {

        SubTree otherHalfTree = buildTree(
            buildDirection == -1 ? backward : forward,
            buildDirection,
            treeHeight,
            timeStep
        );

        if (buildDirection == -1) {
            backward = otherHalfTree.backward;
        } else {
            forward = otherHalfTree.forward;
        }

        sumMetropolisAcceptanceProbability += otherHalfTree.sumMetropolisAcceptanceProbability;
        treeSize += otherHalfTree.treeSize;

        if (otherHalfTree.shouldContinue()) {

            if (Tree.acceptOtherProposalWithProbability(otherHalfTree.getLogSumWeight() - logSumWeight, random)) {
                proposal = otherHalfTree.proposal;
            }

            logSumWeight = logSumExp(logSumWeight, otherHalfTree.logSumWeight);

            sumMomentum = add(sumMomentum, otherHalfTree.sumMomentum);
        }

        diverged = otherHalfTree.diverged;

        if (!diverged) {

            uTurned = otherHalfTree.uTurned || Tree.isUTurning(
                forward.getVelocity(),
                backward.getVelocity(),
                sumMomentum
            );
        }

        treeHeight++;
    }

    /**
     * @param buildFrom      The leap frog to start building the tree from
     * @param buildDirection either 1 for forward or -1 for backwards in time
     * @param treeHeight     The height to build the tree to
     * @param timeStep       The time step delta for each new step
     * @return A subtree with treeHeight height
     */
    private SubTree buildTree(LeapfrogState buildFrom,
                              int buildDirection,
                              int treeHeight,
                              double timeStep) {
        if (treeHeight == 0) {

            //Base case-take one leapfrog step in the build direction

            return treeBuilderBaseCase(
                buildFrom,
                buildDirection,
                timeStep
            );

        } else {
            //Recursion-implicitly build the left and right subtrees.

            SubTree subTree = buildTree(
                buildFrom,
                buildDirection,
                treeHeight - 1,
                timeStep
            );

            //Should continue building other half if first half's shouldContinue is true
            if (subTree.shouldContinue()) {

                SubTree extendedSubTree = buildTree(
                    buildDirection == -1 ? subTree.backward : subTree.forward,
                    buildDirection,
                    treeHeight - 1,
                    timeStep
                );

                if (buildDirection == -1) {
                    subTree.backward = extendedSubTree.backward;
                } else {
                    subTree.forward = extendedSubTree.forward;
                }

                subTree.diverged = extendedSubTree.diverged;
                subTree.uTurned = extendedSubTree.uTurned;

                if (extendedSubTree.shouldContinue()) {

                    subTree.sumMomentum = add(subTree.sumMomentum, extendedSubTree.sumMomentum);

                    subTree.uTurned = isUTurning(
                        subTree.forward.getVelocity(),
                        subTree.backward.getVelocity(),
                        subTree.sumMomentum
                    );

                    final double totalLogSumWeight = logSumExp(subTree.logSumWeight, extendedSubTree.logSumWeight);

                    subTree.logSumWeight = totalLogSumWeight;

                    if (acceptOtherProposalWithProbability(extendedSubTree.logSumWeight - totalLogSumWeight, random)) {
                        subTree.proposal = extendedSubTree.proposal;
                    }

                }

                subTree.sumMetropolisAcceptanceProbability += extendedSubTree.sumMetropolisAcceptanceProbability;
                subTree.treeSize += extendedSubTree.treeSize;
            }

            return subTree;
        }

    }

    public static double logSumExp(double a, double b) {
        double max = Math.max(a, b);
        return max + Math.log(Math.exp(a - max) + Math.exp(b - max));
    }

    private SubTree treeBuilderBaseCase(final LeapfrogState leapfrogState,
                                        final int buildDirection,
                                        final double timeStep) {

        LeapfrogState leapfrogStateAfterStep = leapfrogIntegrator.step(leapfrogState, logProbGradientCalculator, timeStep * buildDirection);

        final double energyAfterStep = leapfrogStateAfterStep.getEnergy();

        final double energyChange = energyAfterStep - startEnergy;

        final boolean isDivergent = Math.abs(energyChange) >= maxEnergyChange || Double.isNaN(energyChange);

        if (isDivergent) {

            return new SubTree(
                leapfrogStateAfterStep,
                leapfrogStateAfterStep,
                leapfrogStateAfterStep.getMomentum(),
                null,
                Double.NEGATIVE_INFINITY,
                true,
                false,
                0,
                1
            );

        } else {

            final double logSumWeight = -energyChange;

            final double metropolisAcceptanceProbability = Math.min(
                1.0,
                Math.exp(logSumWeight)
            );

            final Map<VariableReference, ?> sample = takeSample(sampleFromVariables);

            Proposal proposal = new Proposal(
                leapfrogStateAfterStep.getPosition(),
                leapfrogStateAfterStep.getGradient(),
                sample,
                leapfrogStateAfterStep.getLogProb()
            );

            return new SubTree(
                leapfrogStateAfterStep,
                leapfrogStateAfterStep,
                leapfrogStateAfterStep.getMomentum(),
                proposal,
                logSumWeight,
                false,
                false,
                metropolisAcceptanceProbability,
                1
            );
        }
    }

    private static boolean acceptOtherProposalWithProbability(double probability,
                                                              KeanuRandom random) {

        return Math.log(random.nextDouble()) < probability;
    }

    private static boolean isUTurning(Map<VariableReference, DoubleTensor> velocityForward,
                                      Map<VariableReference, DoubleTensor> velocityBackward,
                                      Map<VariableReference, DoubleTensor> rho) {
        double forward = 0.0;
        double backward = 0.0;

        for (VariableReference latentId : velocityForward.keySet()) {

            final DoubleTensor vForward = velocityForward.get(latentId);
            final DoubleTensor vBackward = velocityBackward.get(latentId);
            final DoubleTensor rhoForLatent = rho.get(latentId);

            forward += vForward.times(rhoForLatent).sumNumber();
            backward += vBackward.times(rhoForLatent).sumNumber();
        }

        return (forward < 0.0) || (backward < 0.0);
    }

    public boolean shouldContinue() {
        return !diverged && !uTurned;
    }

    public void save(Statistics statistics) {
        statistics.store(NUTS.Metrics.LOG_PROB, proposal.getLogProb());
        statistics.store(NUTS.Metrics.TREE_SIZE, (double) treeSize);
    }

    @Data
    @AllArgsConstructor
    private static class SubTree {

        /**
         * The leap frog at the forward most step in the tree.
         */
        private LeapfrogState forward;

        /**
         * The leap frog at the backward most step in the tree
         */
        private LeapfrogState backward;

        /**
         * The sum of all of the momentum from each step
         */
        private Map<VariableReference, DoubleTensor> sumMomentum;

        /**
         * The current accepted proposal.
         */
        private Proposal proposal;

        /**
         * log sum of negative energy change
         */
        private double logSumWeight;

        /**
         * A flag indicating the a step diverged due to significant energy change.
         */
        private boolean diverged;

        /**
         * A flag indicating the steps are turning
         */
        private boolean uTurned;

        /**
         * A summation of the metropolis acceptance probability from each step
         */
        private double sumMetropolisAcceptanceProbability;

        /**
         * The size of the sub tree, which is the number of steps taken to build the tree.
         */
        private int treeSize;

        public boolean shouldContinue() {
            return !diverged && !uTurned;
        }
    }
}