package io.improbable.keanu.vertices.tensor.number.floating.operators.unary;

import io.improbable.keanu.tensor.FloatingPointTensor;
import io.improbable.keanu.vertices.tensor.number.UnaryOperationTestHelpers;
import io.improbable.keanu.vertices.tensor.number.floating.FloatingPointTensorVertex;
import io.improbable.keanu.vertices.tensor.number.floating.dbl.DoubleVertex;
import org.junit.Test;

import static io.improbable.keanu.vertices.tensor.number.UnaryOperationTestHelpers.finiteDifferenceMatchesElementwise;

public class Log10VertexTest {

    @Test
    public void doesOperateOnMatrix() {
        UnaryOperationTestHelpers.operatesOnInput(FloatingPointTensor::log10, FloatingPointTensorVertex::log10);
    }

    @Test
    public void changesMatchGradient() {
        finiteDifferenceMatchesElementwise(DoubleVertex::log10);
    }
}
