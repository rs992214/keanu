package io.improbable.keanu.backend;

import io.improbable.keanu.algorithms.variational.optimizer.Variable;
import io.improbable.keanu.algorithms.variational.optimizer.VariableReference;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ProbabilisticGraph extends AutoCloseable {

    default double logProb() {
        return logProb(Collections.emptyMap());
    }

    double logProb(Map<VariableReference, ?> inputs);

    default double logLikelihood() {
        return logLikelihood(Collections.emptyMap());
    }

    double logLikelihood(Map<VariableReference, ?> inputs);

    LogProbWithSample logProbWithSample(Map<VariableReference, ?> inputs, List<VariableReference> outputs);

    List<? extends Variable> getLatentVariables();

    Map<VariableReference, ?> getLatentVariablesValues();

    @Override
    default void close() {
    }
}