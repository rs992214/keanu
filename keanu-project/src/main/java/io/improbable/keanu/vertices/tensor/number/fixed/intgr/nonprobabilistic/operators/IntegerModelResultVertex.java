package io.improbable.keanu.vertices.tensor.number.fixed.intgr.nonprobabilistic.operators;

import io.improbable.keanu.tensor.Tensor;
import io.improbable.keanu.tensor.intgr.IntegerTensor;
import io.improbable.keanu.vertices.NonProbabilistic;
import io.improbable.keanu.vertices.NonSaveableVertex;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.VertexImpl;
import io.improbable.keanu.vertices.VertexLabel;
import io.improbable.keanu.vertices.model.ModelResult;
import io.improbable.keanu.vertices.model.ModelResultProvider;
import io.improbable.keanu.vertices.model.ModelVertex;
import io.improbable.keanu.vertices.tensor.number.fixed.intgr.IntegerVertex;

/**
 * A non-probabilistic integer vertex whose value is extracted from an upstream model vertex.
 */
public class IntegerModelResultVertex extends VertexImpl<IntegerTensor, IntegerVertex> implements IntegerVertex,   ModelResultProvider<IntegerTensor>, NonProbabilistic<IntegerTensor>, NonSaveableVertex {

    private final ModelResult<IntegerTensor> delegate;

    public IntegerModelResultVertex(ModelVertex model, VertexLabel label) {
        super(Tensor.SCALAR_SHAPE);
        this.delegate = new ModelResult<>(model, label);
        setParents((Vertex) model);
    }

    @Override
    public ModelVertex<IntegerTensor> getModel() {
        return delegate.getModel();
    }

    @Override
    public IntegerTensor calculate() {
        return delegate.calculate();
    }
}
