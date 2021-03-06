package io.improbable.keanu.vertices.tensor.number.operators.binary;

import io.improbable.keanu.vertices.tensor.number.floating.dbl.DoubleVertex;
import org.junit.Test;

import static io.improbable.keanu.vertices.tensor.number.BinaryOperationTestHelpers.operatesOnTwo2x2MatrixVertexValues;
import static io.improbable.keanu.vertices.tensor.number.BinaryOperationTestHelpers.operatesOnTwoScalarVertexValues;

public class LessThanOrEqualToMaskVertexTest {

    @Test
    public void lessThanOrEqualToScalarVertexValues() {
        operatesOnTwoScalarVertexValues(
            2.0,
            3.0,
            1.0,
            DoubleVertex::lessThanOrEqualToMask
        );
    }

    @Test
    public void lessThanOrEqualToMatrixVertexValues() {
        operatesOnTwo2x2MatrixVertexValues(
            new double[]{1.0, 4.0, 3.0, -3.0},
            new double[]{2.0, 2.0, 3.0, 3.0},
            new double[]{1.0, 0.0, 1.0, 1.0},
            DoubleVertex::lessThanOrEqualToMask
        );
    }
}
