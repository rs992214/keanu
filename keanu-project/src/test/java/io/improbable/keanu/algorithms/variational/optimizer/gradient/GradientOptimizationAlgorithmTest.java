package io.improbable.keanu.algorithms.variational.optimizer.gradient;

import io.improbable.keanu.DeterministicRule;
import io.improbable.keanu.algorithms.variational.optimizer.OptimizedResult;
import io.improbable.keanu.algorithms.variational.optimizer.gradient.testcase.GradientOptimizationAlgorithmTestCase;
import io.improbable.keanu.algorithms.variational.optimizer.gradient.testcase.RosenbrockTestCase;
import io.improbable.keanu.algorithms.variational.optimizer.gradient.testcase.SingleGaussianTestCase;
import io.improbable.keanu.algorithms.variational.optimizer.gradient.testcase.SumGaussianTestCase;
import lombok.AllArgsConstructor;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.function.Supplier;

@RunWith(Theories.class)
public class GradientOptimizationAlgorithmTest {

    @Rule
    public DeterministicRule deterministicRule = new DeterministicRule();

    @AllArgsConstructor
    public enum OptimizerType {

        ADAM(() -> {

            return Adam.builder()
                .alpha(0.1)
                .useMaxThetaDeltaConvergenceChecker(0.00001)
                .build();
        }),

        NONLINEAR_CONJUGATE_GRADIENT(() -> {

            return ConjugateGradient.builder().build();
        })
        ;

        Supplier<GradientOptimizationAlgorithm> getOptimizer;
    }

    @AllArgsConstructor
    public enum TestCase {
        SUM_GAUSSIAN_MAP(() -> new SumGaussianTestCase(true)),
        SUM_GAUSSIAN_MLE(() -> new SumGaussianTestCase(false)),
        ROSENBROCK_1_100(() -> new RosenbrockTestCase(1, 100, 1, 1)),
        SINGLE_GAUSSIAN(SingleGaussianTestCase::new);

        private Supplier<GradientOptimizationAlgorithmTestCase> supplier;
    }

    @DataPoints
    public static OptimizerType[] getTypes() {
        return OptimizerType.values();
    }

    @DataPoints
    public static TestCase[] getTestCase() {
        return TestCase.values();
    }

    @Theory
    public void canOptimize(OptimizerType type, TestCase testCaseSupplier) {

        GradientOptimizationAlgorithmTestCase testCase = testCaseSupplier.supplier.get();

        GradientOptimizationAlgorithm algo = type.getOptimizer.get();

        OptimizedResult result = algo.optimize(
            testCase.getVariables(),
            testCase.getFitnessFunction(),
            testCase.getFitnessFunctionGradient()
        );

        testCase.assertResult(result);
    }
}
