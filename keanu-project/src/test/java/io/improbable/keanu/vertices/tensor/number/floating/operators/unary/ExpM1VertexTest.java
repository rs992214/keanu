package io.improbable.keanu.vertices.tensor.number.floating.operators.unary;

import io.improbable.keanu.tensor.FloatingPointTensor;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.tensor.number.UnaryOperationTestHelpers;
import io.improbable.keanu.vertices.tensor.number.floating.FloatingPointTensorVertex;
import org.junit.Test;

import static io.improbable.keanu.vertices.tensor.number.UnaryOperationTestHelpers.finiteDifferenceMatchesElementwise;

public class ExpM1VertexTest {

    @Test
    public void doesOperateOnMatrix() {
        UnaryOperationTestHelpers.operatesOnInput(FloatingPointTensor::expM1, FloatingPointTensorVertex::expM1);
    }

    @Test
    public void changesMatchGradient() {
        finiteDifferenceMatchesElementwise(DoubleVertex::expM1);
    }
}