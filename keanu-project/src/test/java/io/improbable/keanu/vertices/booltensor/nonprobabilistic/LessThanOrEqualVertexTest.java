package io.improbable.keanu.vertices.booltensor.nonprobabilistic;

import io.improbable.keanu.tensor.dbl.DoubleTensor;
import io.improbable.keanu.tensor.intgr.IntegerTensor;
import io.improbable.keanu.vertices.booltensor.nonprobabilistic.operators.binary.compare.LessThanOrEqualVertex;
import io.improbable.keanu.vertices.dbltensor.nonprobabilistic.ConstantDoubleTensorVertex;
import io.improbable.keanu.vertices.intgrtensor.nonprobabilistic.ConstantIntegerVertex;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LessThanOrEqualVertexTest {

    @Test
    public void comparesIntegers() {
        isLessThanOrEqual(0, 1, true);
        isLessThanOrEqual(1, 1, true);
        isLessThanOrEqual(2, 1, false);
    }

    @Test
    public void comparesDoubles() {
        isLessThanOrEqual(0.0, 0.5, true);
        isLessThanOrEqual(0.5, 0.5, true);
        isLessThanOrEqual(1.0, 0.5, false);
    }

    private void isLessThanOrEqual(int a, int b, boolean expected) {
        LessThanOrEqualVertex<IntegerTensor, IntegerTensor> vertex = new LessThanOrEqualVertex<>(new ConstantIntegerVertex(a), new ConstantIntegerVertex(b));
        assertEquals(expected, vertex.lazyEval().scalar());
    }

    private void isLessThanOrEqual(double a, double b, boolean expected) {
        LessThanOrEqualVertex<DoubleTensor, DoubleTensor> vertex = new LessThanOrEqualVertex<>(new ConstantDoubleTensorVertex(a), new ConstantDoubleTensorVertex(b));
        assertEquals(expected, vertex.lazyEval().scalar());
    }
}