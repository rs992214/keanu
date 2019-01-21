package io.improbable.keanu.algorithms.mcmc;

import io.improbable.keanu.algorithms.variational.optimizer.LambdaSectionSnapshot;
import io.improbable.keanu.algorithms.variational.optimizer.Variable;
import io.improbable.keanu.network.NetworkSnapshot;
import io.improbable.keanu.vertices.Vertex;

import java.util.List;
import java.util.Set;

public class RollBackOnRejection implements ProposalRejectionStrategy {
    private final LambdaSectionSnapshot lambdaSectionSnapshot;
    private NetworkSnapshot networkSnapshot;

    public RollBackOnRejection(List<Vertex> latentVariables) {
        lambdaSectionSnapshot = new LambdaSectionSnapshot(latentVariables);
    }

    @Override
    public void prepare(Set<Variable> chosenVariables) {
        Set<? extends Variable> affectedVariables = lambdaSectionSnapshot.getAllVariablesAffectedBy(chosenVariables);
        networkSnapshot = NetworkSnapshot.create((Set<Vertex>) affectedVariables);
    }

    @Override
    public void handle() {
        networkSnapshot.apply();
    }
}
