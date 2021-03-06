package io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators;

import io.improbable.keanu.tensor.Tensor;
import io.improbable.keanu.tensor.bool.BooleanTensor;
import io.improbable.keanu.vertices.NonProbabilistic;
import io.improbable.keanu.vertices.NonSaveableVertex;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.VertexImpl;
import io.improbable.keanu.vertices.VertexLabel;
import io.improbable.keanu.vertices.model.ModelResult;
import io.improbable.keanu.vertices.model.ModelResultProvider;
import io.improbable.keanu.vertices.model.ModelVertex;
import io.improbable.keanu.vertices.tensor.bool.BooleanVertex;

/**
 * A non-probabilistic boolean vertex whose value is extracted from an upstream model vertex.
 */
public class BooleanModelResultVertex extends VertexImpl<BooleanTensor, BooleanVertex> implements BooleanVertex, ModelResultProvider<BooleanTensor>, NonProbabilistic<BooleanTensor>, NonSaveableVertex {

    private final ModelResult<BooleanTensor> delegate;

    public BooleanModelResultVertex(ModelVertex model, VertexLabel label) {
        super(Tensor.SCALAR_SHAPE);
        delegate = new ModelResult<>(model, label);
        setParents((Vertex) model);
    }

    @Override
    public ModelVertex<BooleanTensor> getModel() {
        return delegate.getModel();
    }

    @Override
    public BooleanTensor calculate() {
        return delegate.calculate();
    }
}
