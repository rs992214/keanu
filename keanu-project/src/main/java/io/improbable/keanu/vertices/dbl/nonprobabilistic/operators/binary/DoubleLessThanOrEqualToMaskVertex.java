package io.improbable.keanu.vertices.dbl.nonprobabilistic.operators.binary;

import io.improbable.keanu.annotation.ExportVertexToPythonBindings;
import io.improbable.keanu.tensor.dbl.DoubleTensor;
import io.improbable.keanu.vertices.LoadVertexParam;
import io.improbable.keanu.vertices.Vertex;

public class DoubleLessThanOrEqualToMaskVertex extends DoubleBinaryOpVertex {

    @ExportVertexToPythonBindings
    public DoubleLessThanOrEqualToMaskVertex(@LoadVertexParam(LEFT_NAME) Vertex<DoubleTensor, ?> left,
                                             @LoadVertexParam(RIGHT_NAME) Vertex<DoubleTensor, ?> right) {
        super(left, right);
    }

    @Override
    protected DoubleTensor op(DoubleTensor l, DoubleTensor r) {
        return l.lessThanOrEqualToMask(r);
    }

}
