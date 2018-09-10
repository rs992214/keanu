package io.improbable.keanu.vertices.dbl.nonprobabilistic.operators.unary;

import io.improbable.keanu.tensor.dbl.DoubleTensor;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.diff.DualNumber;

public class MatrixInverseVertex extends DoubleUnaryOpVertex {

    public MatrixInverseVertex(DoubleVertex inputVertex) {
        super(checkInputIsSquareMatrix(inputVertex.getShape()), inputVertex);
    }

    @Override
    protected DoubleTensor op(DoubleTensor value) {
        return value.inverse();
    }

    @Override
    protected DualNumber dualOp(DualNumber dualNumber) {
        return dualNumber.matrixInverse();
    }

    private static int[] checkInputIsSquareMatrix(int[] shape) {
        if (shape.length != 2) {
            throw new IllegalArgumentException("Can only invert a Matrix (received rank: " + shape.length + ")");
        }

        if (shape[0] != shape[1]) {
            throw new IllegalArgumentException("Can only invert a square Matrix (received: "
                + shape[0] + ", " + shape[1] + ")");
        }

        return shape;
    }

}
