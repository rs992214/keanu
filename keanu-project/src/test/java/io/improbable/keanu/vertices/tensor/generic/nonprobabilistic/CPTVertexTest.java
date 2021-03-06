package io.improbable.keanu.vertices.tensor.generic.nonprobabilistic;

import io.improbable.keanu.vertices.tensor.bool.BooleanVertex;
import io.improbable.keanu.vertices.tensor.bool.probabilistic.BernoulliVertex;
import org.junit.Test;

import static io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.ConstantBooleanVertex.FALSE;
import static io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.ConstantBooleanVertex.TRUE;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class CPTVertexTest {

    @Test
    public void canSetAndCascade() {

        BooleanVertex A = new BernoulliVertex(0.2);

        BooleanVertex cpt = ConditionalProbabilityTable.of(A)
            .when(true).then(TRUE)
            .orDefault(FALSE);

        A.setAndCascade(false);
        assertFalse(cpt.getValue().scalar());

        A.setAndCascade(true);
        assertTrue(cpt.getValue().scalar());
    }

    @Test
    public void canBeXOR() {

        BooleanVertex A = new BernoulliVertex(0.5);
        BooleanVertex B = new BernoulliVertex(0.5);

        BooleanVertex cpt = ConditionalProbabilityTable.of(A, B)
            .when(true, true).then(FALSE)
            .when(false, true).then(TRUE)
            .when(true, false).then(TRUE)
            .orDefault(FALSE);

        A.setAndCascade(true);
        B.setAndCascade(true);
        assertFalse(cpt.getValue().scalar());

        A.setAndCascade(false);
        B.setAndCascade(true);
        assertTrue(cpt.getValue().scalar());

        A.setAndCascade(true);
        B.setAndCascade(false);
        assertTrue(cpt.getValue().scalar());

        A.setAndCascade(false);
        B.setAndCascade(false);
        assertFalse(cpt.getValue().scalar());
    }
}
