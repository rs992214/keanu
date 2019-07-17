package io.improbable.keanu.vertices.tensor.number.floating.operators.unary;

import io.improbable.keanu.vertices.dbl.DoubleVertex;
import org.apache.commons.math3.util.FastMath;
import org.junit.Ignore;
import org.junit.Test;

import static io.improbable.keanu.vertices.tensor.number.BinaryOperationTestHelpers.toDiagonalArray;
import static io.improbable.keanu.vertices.tensor.number.UnaryOperationTestHelpers.calculatesDerivativeOfMatrixElementWiseOperator;
import static io.improbable.keanu.vertices.tensor.number.UnaryOperationTestHelpers.calculatesDerivativeOfScalar;
import static io.improbable.keanu.vertices.tensor.number.UnaryOperationTestHelpers.finiteDifferenceMatchesElementwise;
import static io.improbable.keanu.vertices.tensor.number.UnaryOperationTestHelpers.operatesOn2x2MatrixVertexValues;
import static io.improbable.keanu.vertices.tensor.number.UnaryOperationTestHelpers.operatesOnScalarVertexValue;

public class CoshVertexTest {

    @Test
    public void coshScalarVertexValue() {
        operatesOnScalarVertexValue(
            Math.PI,
            FastMath.cosh(Math.PI),
            DoubleVertex::cosh
        );
    }

    @Test
    @Ignore
    public void calculatesDerivativeOScalarCosh() {
        calculatesDerivativeOfScalar(
            0.5,
            -Math.sin(0.5),
            DoubleVertex::cosh
        );
    }

    @Test
    public void coshMatrixVertexValues() {
        operatesOn2x2MatrixVertexValues(
            new double[]{0.0, 0.1, 0.2, 0.3},
            new double[]{FastMath.cosh(0.0), FastMath.cosh(0.1), FastMath.cosh(0.2), FastMath.cosh(0.3)},
            DoubleVertex::cosh
        );
    }

    @Test
    @Ignore
    public void calculatesDerivativeOfMatrixElementWiseCosh() {
        calculatesDerivativeOfMatrixElementWiseOperator(
            new double[]{0.1, 0.2, 0.3, 0.4},
            toDiagonalArray(new double[]{-Math.sin(0.1), -Math.sin(0.2), -Math.sin(0.3), -Math.sin(0.4)}),
            DoubleVertex::cosh
        );
    }

    @Test
    @Ignore
    public void changesMatchGradient() {
        finiteDifferenceMatchesElementwise(DoubleVertex::cosh);
    }

}
