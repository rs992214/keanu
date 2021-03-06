package io.improbable.keanu.vertices.tensor.number.fixed.intgr.nonprobabilistic;

import io.improbable.keanu.annotation.ExportVertexToPythonBindings;
import io.improbable.keanu.tensor.NumberTensor;
import io.improbable.keanu.tensor.intgr.IntegerTensor;
import io.improbable.keanu.vertices.LoadVertexParam;
import io.improbable.keanu.vertices.NonProbabilistic;
import io.improbable.keanu.vertices.SaveVertexParam;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.VertexImpl;
import io.improbable.keanu.vertices.VertexUnaryOp;
import io.improbable.keanu.vertices.tensor.number.fixed.intgr.IntegerVertex;

public class CastNumberToIntegerVertex<T extends NumberTensor> extends VertexImpl<IntegerTensor, IntegerVertex> implements IntegerVertex, NonProbabilistic<IntegerTensor>, VertexUnaryOp<Vertex<T, ?>> {

    private final Vertex<T, ?> inputVertex;
    private static final String INPUT_NAME = "inputVertex";

    @ExportVertexToPythonBindings
    public CastNumberToIntegerVertex(@LoadVertexParam(INPUT_NAME) Vertex<T, ?> inputVertex) {
        super(inputVertex.getShape());
        this.inputVertex = inputVertex;
        setParents(inputVertex);
    }

    @Override
    public IntegerTensor calculate() {
        return inputVertex.getValue().toInteger();
    }

    @SaveVertexParam(INPUT_NAME)
    public Vertex<T, ?> getInputVertex() {
        return inputVertex;
    }
}
