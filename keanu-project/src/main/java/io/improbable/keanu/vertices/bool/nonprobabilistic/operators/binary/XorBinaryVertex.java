package io.improbable.keanu.vertices.bool.nonprobabilistic.operators.binary;

import io.improbable.keanu.annotation.DisplayInformationForOutput;
import io.improbable.keanu.annotation.ExportVertexToPythonBindings;
import io.improbable.keanu.tensor.bool.BooleanTensor;
import io.improbable.keanu.vertices.IVertex;
import io.improbable.keanu.vertices.LoadVertexParam;

@DisplayInformationForOutput(displayName = "XOR")
public class XorBinaryVertex extends BooleanBinaryOpVertex<BooleanTensor, BooleanTensor> {

    @ExportVertexToPythonBindings
    public XorBinaryVertex(@LoadVertexParam(A_NAME) IVertex<BooleanTensor> a,
                           @LoadVertexParam(B_NAME) IVertex<BooleanTensor> b) {
        super(a, b);
    }

    @Override
    protected BooleanTensor op(BooleanTensor l, BooleanTensor r) {
        return l.xor(r);
    }
}