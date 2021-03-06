package io.improbable.keanu.vertices.tensor.number.fixed.intgr.nonprobabilistic;

import io.improbable.keanu.annotation.ExportVertexToPythonBindings;
import io.improbable.keanu.tensor.intgr.IntegerTensor;
import io.improbable.keanu.vertices.ConstantVertex;
import io.improbable.keanu.vertices.LoadVertexParam;
import io.improbable.keanu.vertices.NonProbabilistic;
import io.improbable.keanu.vertices.SaveVertexParam;
import io.improbable.keanu.vertices.VertexImpl;
import io.improbable.keanu.vertices.tensor.number.fixed.intgr.IntegerVertex;

public class ConstantIntegerVertex extends VertexImpl<IntegerTensor, IntegerVertex> implements IntegerVertex, ConstantVertex, NonProbabilistic<IntegerTensor> {

    private final static String CONSTANT_NAME = "constant";

    @ExportVertexToPythonBindings
    public ConstantIntegerVertex(@LoadVertexParam(CONSTANT_NAME) IntegerTensor constant) {
        super(constant.getShape());
        setValue(constant);
    }

    public ConstantIntegerVertex(int... vector) {
        this(IntegerTensor.create(vector));
    }

    public ConstantIntegerVertex(int constant) {
        this(IntegerTensor.scalar(constant));
    }

    public ConstantIntegerVertex(int[] data, long[] shape) {
        this(IntegerTensor.create(data, shape));
    }

    @Override
    public IntegerTensor calculate() {
        return getValue();
    }

    @SaveVertexParam(CONSTANT_NAME)
    public IntegerTensor getConstantValue() {
        return getValue();
    }
}
