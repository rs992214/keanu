package io.improbable.keanu.tensor.intgr;

import io.improbable.keanu.tensor.INDArrayShim;
import io.improbable.keanu.tensor.Nd4jFixedPointTensor;
import io.improbable.keanu.tensor.Nd4jTensor;
import io.improbable.keanu.tensor.NumberTensor;
import io.improbable.keanu.tensor.Tensor;
import io.improbable.keanu.tensor.TypedINDArrayFactory;
import io.improbable.keanu.tensor.dbl.DoubleTensor;
import io.improbable.keanu.tensor.dbl.Nd4jDoubleTensor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 * Class for representing n-dimensional arrays of integers. This is
 * backed by Nd4j.
 */
public class Nd4jIntegerTensor extends Nd4jFixedPointTensor<Integer, IntegerTensor> implements IntegerTensor {

    static {
        INDArrayShim.startNewThreadForNd4j();
    }

    private static final DataType BUFFER_TYPE = DataType.INT;

    public Nd4jIntegerTensor(int[] data, long[] shape) {
        this(TypedINDArrayFactory.create(data, shape));
    }

    public Nd4jIntegerTensor(INDArray tensor) {
        super(tensor);
    }

    @Override
    protected INDArray getTensor(Tensor<Integer, ?> tensor) {
        return getAsINDArray(tensor);
    }

    public static Nd4jIntegerTensor scalar(int scalarValue) {
        return new Nd4jIntegerTensor(Nd4j.scalar(scalarValue));
    }

    public static Nd4jIntegerTensor create(int[] values, long[] shape) {
        return new Nd4jIntegerTensor(values, shape);
    }

    public static Nd4jIntegerTensor create(int value, long[] shape) {
        return new Nd4jIntegerTensor(Nd4j.valueArrayOf(shape, value));
    }

    public static Nd4jIntegerTensor ones(long[] shape) {
        return new Nd4jIntegerTensor(TypedINDArrayFactory.ones(shape, BUFFER_TYPE));
    }

    public static Nd4jIntegerTensor eye(long n) {
        return new Nd4jIntegerTensor(TypedINDArrayFactory.eye(n, BUFFER_TYPE));
    }

    public static Nd4jIntegerTensor zeros(long[] shape) {
        return new Nd4jIntegerTensor(TypedINDArrayFactory.zeros(shape, BUFFER_TYPE));
    }

    public static Nd4jIntegerTensor arange(int start, int end) {
        return new Nd4jIntegerTensor(TypedINDArrayFactory.arange(start, end));
    }

    public static Nd4jDoubleTensor arange(int start, int end, int stepSize) {
        int stepCount = (end - start) / stepSize;
        INDArray arangeWithStep = TypedINDArrayFactory.arange(0, stepCount).muli(stepSize).addi(start);
        return new Nd4jDoubleTensor(arangeWithStep);
    }

    static INDArray getAsINDArray(Tensor<Integer, ?> that) {

        if (that instanceof Nd4jTensor) {
            INDArray array = ((Nd4jTensor) that).getTensor();
            if (array.dataType() == DataType.INT) {
                return array;
            } else {
                return array.castTo(DataType.INT);
            }
        } else if (that instanceof NumberTensor) {
            return TypedINDArrayFactory.create(((NumberTensor) that).toInteger().asFlatIntegerArray(), that.getShape());
        } else {
            throw new IllegalArgumentException("Cannot convert " + that.getClass().getSimpleName() + " to double INDArray/");
        }
    }

    @Override
    public boolean equalsWithinEpsilon(IntegerTensor o, Integer epsilon) {
        if (this == o) return true;

        if (o instanceof Nd4jTensor) {
            return tensor.equalsWithEps(((Nd4jTensor) o).getTensor(), epsilon);
        } else {
            if (this.hasSameShapeAs(o)) {
                IntegerTensor difference = o.minus(this);
                return difference.abs().lessThan(epsilon).allTrue();
            }
        }
        return false;
    }

    @Override
    protected Integer getNumber(Number number) {
        return number.intValue();
    }

    @Override
    public IntegerTensor setAllInPlace(Integer value) {
        tensor = Nd4j.valueArrayOf(getShape(), value);
        return this;
    }

    @Override
    public IntegerTensor safeLogTimesInPlace(IntegerTensor y) {
        throw new NotImplementedException("");
    }

    @Override
    public IntegerTensor getGreaterThanMask(IntegerTensor greaterThanThis) {
        return greaterThan(greaterThanThis).toIntegerMask();
    }

    @Override
    public IntegerTensor getGreaterThanOrEqualToMask(IntegerTensor greaterThanOrEqualToThis) {
        return greaterThanOrEqual(greaterThanOrEqualToThis).toIntegerMask();
    }

    @Override
    public IntegerTensor getLessThanMask(IntegerTensor lessThanThis) {
        return lessThan(lessThanThis).toIntegerMask();
    }

    @Override
    public IntegerTensor getLessThanOrEqualToMask(IntegerTensor lessThanOrEqualToThis) {
        return lessThanOrEqual(lessThanOrEqualToThis).toIntegerMask();
    }

    @Override
    public DoubleTensor toDouble() {
        return new Nd4jDoubleTensor(tensor.castTo(DataType.DOUBLE));
    }

    @Override
    public IntegerTensor toInteger() {
        return duplicate();
    }

    @Override
    protected IntegerTensor create(INDArray tensor) {
        return new Nd4jIntegerTensor(tensor);
    }

    @Override
    protected IntegerTensor set(INDArray tensor) {
        this.tensor = tensor.dataType() == DataType.INT ? tensor : tensor.castTo(DataType.INT);
        return this;
    }

    @Override
    protected IntegerTensor getThis() {
        return this;
    }

    @Override
    public double[] asFlatDoubleArray() {
        return tensor.dup().data().asDouble();
    }

    @Override
    public int[] asFlatIntegerArray() {
        return tensor.dup().data().asInt();
    }

    @Override
    public Integer[] asFlatArray() {
        return ArrayUtils.toObject(asFlatIntegerArray());
    }

    @Override
    public FlattenedView<Integer> getFlattenedView() {
        return new Nd4jIntegerFlattenedView();
    }

    private class Nd4jIntegerFlattenedView implements FlattenedView<Integer> {

        @Override
        public long size() {
            return tensor.data().length();
        }

        @Override
        public Integer get(long index) {
            return tensor.data().getInt(index);
        }

        @Override
        public Integer getOrScalar(long index) {
            if (tensor.length() == 1) {
                return get(0);
            } else {
                return get(index);
            }
        }

        @Override
        public void set(long index, Integer value) {
            tensor.data().put(index, value);
        }

    }

}
