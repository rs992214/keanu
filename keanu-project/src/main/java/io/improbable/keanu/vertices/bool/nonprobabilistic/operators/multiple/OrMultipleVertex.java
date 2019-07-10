package io.improbable.keanu.vertices.bool.nonprobabilistic.operators.multiple;

import io.improbable.keanu.tensor.bool.BooleanTensor;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.bool.BooleanVertex;

import java.util.Collection;
import java.util.stream.Collectors;

import static io.improbable.keanu.tensor.TensorShapeValidation.checkAllShapesMatch;

public class OrMultipleVertex extends BooleanReduceVertex {

    public OrMultipleVertex(Collection<? extends Vertex<BooleanTensor, BooleanVertex>> input) {
        super(checkAllShapesMatch(
            input.stream().map(Vertex::getShape).collect(Collectors.toList())
            ),
            input, BooleanTensor::or);
    }
}
