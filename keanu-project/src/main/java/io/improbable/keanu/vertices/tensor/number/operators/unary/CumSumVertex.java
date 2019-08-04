package io.improbable.keanu.vertices.tensor.number.operators.unary;

import io.improbable.keanu.annotation.ExportVertexToPythonBindings;
import io.improbable.keanu.tensor.NumberTensor;
import io.improbable.keanu.vertices.LoadVertexParam;
import io.improbable.keanu.vertices.NonProbabilisticVertex;
import io.improbable.keanu.vertices.SaveVertexParam;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.dbl.Differentiable;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.diff.PartialDerivative;
import io.improbable.keanu.vertices.tensor.TensorVertex;
import io.improbable.keanu.vertices.tensor.UnaryTensorOpVertex;
import io.improbable.keanu.vertices.tensor.number.NumberTensorVertex;

import java.util.Map;

public class CumSumVertex<T extends Number, TENSOR extends NumberTensor<T, TENSOR>, VERTEX extends NumberTensorVertex<T, TENSOR, VERTEX>>
    extends UnaryTensorOpVertex<T, TENSOR, VERTEX> implements NonProbabilisticVertex<TENSOR, VERTEX>, Differentiable {

    private final static String REQUESTED_DIMENSION = "requestedDimension";
    private final int requestedDimension;

    @ExportVertexToPythonBindings
    public CumSumVertex(@LoadVertexParam(INPUT_NAME) TensorVertex<T, TENSOR, VERTEX> inputVertex,
                        @LoadVertexParam(REQUESTED_DIMENSION) int requestedDimension) {
        super(inputVertex, inputVertex.ofType());
        this.requestedDimension = requestedDimension;
    }

    @Override
    protected TENSOR op(TENSOR value) {
        return value.cumSum(requestedDimension);
    }

    @SaveVertexParam(REQUESTED_DIMENSION)
    public int getRequestedDimension() {
        return this.requestedDimension;
    }

    @Override
    public PartialDerivative forwardModeAutoDifferentiation(Map<Vertex, PartialDerivative> derivativeOfParentsWithRespectToInput) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<Vertex, PartialDerivative> reverseModeAutoDifferentiation(PartialDerivative derivativeOfOutputWithRespectToSelf) {
        throw new UnsupportedOperationException();
    }
}