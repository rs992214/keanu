package io.improbable.keanu.vertices.tensor.number.fixed.intgr.nonprobabilistic;

import io.improbable.keanu.tensor.Tensor;
import io.improbable.keanu.tensor.intgr.IntegerTensor;
import io.improbable.keanu.vertices.NonProbabilistic;
import io.improbable.keanu.vertices.NonSaveableVertex;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.VertexImpl;
import io.improbable.keanu.vertices.tensor.generic.nonprobabilistic.CPTCondition;
import io.improbable.keanu.vertices.tensor.number.fixed.intgr.IntegerVertex;

import java.util.List;
import java.util.Map;

public class IntegerCPTVertex extends VertexImpl<IntegerTensor, IntegerVertex> implements IntegerVertex, NonProbabilistic<IntegerTensor>, NonSaveableVertex {

    private final List<Vertex<? extends Tensor<?, ?>, ?>> inputs;
    private final Map<CPTCondition, IntegerVertex> conditions;
    private final IntegerVertex defaultResult;

    public IntegerCPTVertex(List<Vertex<? extends Tensor<?, ?>, ?>> inputs,
                            Map<CPTCondition, IntegerVertex> conditions,
                            IntegerVertex defaultResult) {
        super(defaultResult.getShape());
        this.inputs = inputs;
        this.conditions = conditions;
        this.defaultResult = defaultResult;
        addParents(inputs);
        addParents(conditions.values());
        addParent(defaultResult);
    }

    @Override
    public IntegerTensor calculate() {
        final CPTCondition condition = CPTCondition.from(inputs, v -> v.getValue().scalar());
        IntegerVertex vertex = conditions.get(condition);
        return vertex == null ? defaultResult.getValue() : vertex.getValue();
    }
}
