package io.improbable.keanu.vertices.intgr;

import io.improbable.keanu.kotlin.IntegerOperators;
import io.improbable.keanu.tensor.intgr.IntegerTensor;
import io.improbable.keanu.tensor.jvm.Slicer;
import io.improbable.keanu.vertices.NonProbabilisticVertex;
import io.improbable.keanu.vertices.bool.BooleanVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.ConstantIntegerVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerDivisionVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerGreaterThanMaskVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerGreaterThanOrEqualMaskVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerLessThanMaskVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerLessThanOrEqualMaskVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerMatrixMultiplyVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerMaxVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerMinVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerModVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerMultiplicationVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerPowerVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerSetWithMaskVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.binary.IntegerTensorMultiplyVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.multiple.IntegerConcatenationVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.unary.IntegerAbsVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.unary.IntegerApplyVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.unary.IntegerCumProdVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.unary.IntegerCumSumVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.unary.IntegerMaxUnaryVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.unary.IntegerMinUnaryVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.unary.IntegerProductVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.unary.IntegerSumVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.operators.unary.IntegerUnaryOpLambda;
import io.improbable.keanu.vertices.number.FixedPointTensorVertex;

import java.util.List;
import java.util.function.Function;

public interface IntegerVertex extends IntegerOperators<IntegerVertex>, FixedPointTensorVertex<Integer, IntegerTensor, IntegerVertex> {

    //////////////////////////
    ////  Vertex Helpers
    //////////////////////////

    default void setValue(int value) {
        setValue(IntegerTensor.scalar(value));
    }

    default void setValue(int[] values) {
        setValue(IntegerTensor.create(values));
    }

    default void setAndCascade(int value) {
        setAndCascade(IntegerTensor.scalar(value));
    }

    default void setAndCascade(int[] values) {
        setAndCascade(IntegerTensor.create(values));
    }

    default void observe(int value) {
        observe(IntegerTensor.scalar(value));
    }

    default void observe(int[] values) {
        observe(IntegerTensor.create(values));
    }

    default int getValue(long... index) {
        return getValue().getValue(index);
    }

    //////////////////////////
    ////  Tensor Operations
    //////////////////////////

    default IntegerVertex asTyped(NonProbabilisticVertex<IntegerTensor, IntegerVertex> vertex) {
        return new IntegerVertexWrapper(vertex);
    }

    static IntegerVertex concat(int dimension, IntegerVertex... toConcat) {
        return new IntegerConcatenationVertex(dimension, toConcat);
    }

    @Override
    default List<IntegerVertex> split(int dimension, long... splitAtIndices) {
        return null;
    }

    @Override
    default IntegerVertex slice(Slicer slicer) {
        return null;
    }

    @Override
    default BooleanVertex elementwiseEquals(Integer value) {
        return elementwiseEquals(new ConstantIntegerVertex(value));
    }

    @Override
    default BooleanVertex notEqualTo(Integer value) {
        return notEqualTo(new ConstantIntegerVertex(value));
    }

    //////////////////////////
    ////  Number Tensor Operations
    //////////////////////////


    @Override
    default IntegerVertex minus(int value) {
        return minus(new ConstantIntegerVertex(value));
    }

    @Override
    default IntegerVertex minus(Integer value) {
        return minus(new ConstantIntegerVertex(value));
    }

    @Override
    default IntegerVertex reverseMinus(IntegerVertex value) {
        return value.minus(this);
    }

    @Override
    default IntegerVertex reverseMinus(Integer value) {
        return new ConstantIntegerVertex(value).minus(this);
    }

    @Override
    default IntegerVertex reverseMinus(int that) {
        return new ConstantIntegerVertex(that).minus(this);
    }

    @Override
    default IntegerVertex unaryMinus() {
        return multiply(-1);
    }

    @Override
    default IntegerVertex plus(int value) {
        return plus(new ConstantIntegerVertex(value));
    }

    @Override
    default IntegerVertex plus(Integer value) {
        return plus(new ConstantIntegerVertex(value));
    }

    default IntegerVertex multiply(IntegerVertex that) {
        return new IntegerMultiplicationVertex(this, that);
    }

    default IntegerVertex multiply(int factor) {
        return new IntegerMultiplicationVertex(this, new ConstantIntegerVertex(factor));
    }

    @Override
    default IntegerVertex times(IntegerVertex that) {
        return multiply(that);
    }

    @Override
    default IntegerVertex times(Integer value) {
        return multiply(value);
    }

    @Override
    default IntegerVertex times(int that) {
        return multiply(that);
    }

    default IntegerVertex divideBy(int divisor) {
        return new IntegerDivisionVertex(this, new ConstantIntegerVertex(divisor));
    }

    default IntegerVertex divideBy(IntegerVertex that) {
        return new IntegerDivisionVertex(this, that);
    }

    @Override
    default IntegerVertex div(IntegerVertex that) {
        return divideBy(that);
    }

    @Override
    default IntegerVertex div(Integer value) {
        return divideBy(value);
    }

    @Override
    default IntegerVertex div(int that) {
        return divideBy(that);
    }

    @Override
    default IntegerVertex reverseDiv(Integer value) {
        return new ConstantIntegerVertex(value).div(this);
    }

    @Override
    default IntegerVertex reverseDiv(IntegerVertex value) {
        return value.div(this);
    }

    @Override
    default IntegerVertex reverseDiv(int that) {
        return (new ConstantIntegerVertex(that)).div(this);
    }

    @Override
    default IntegerVertex pow(IntegerVertex exponent) {
        return new IntegerPowerVertex(this, exponent);
    }

    @Override
    default IntegerVertex pow(Integer exponent) {
        return pow(new ConstantIntegerVertex(exponent));
    }

    @Override
    default IntegerVertex pow(int exponent) {
        return pow(new ConstantIntegerVertex(exponent));
    }

    @Override
    default IntegerVertex average() {
        return null;
    }

    @Override
    default IntegerVertex standardDeviation() {
        return null;
    }

    @Override
    default IntegerVertex argMax(int axis) {
        return null;
    }

    @Override
    default IntegerVertex argMax() {
        return null;
    }

    @Override
    default IntegerVertex argMin(int axis) {
        return null;
    }

    @Override
    default IntegerVertex argMin() {
        return null;
    }

    @Override
    default IntegerVertex setWithMask(IntegerVertex mask, Integer value) {
        return new IntegerSetWithMaskVertex(this, mask, value);
    }

    @Override
    default IntegerVertex apply(Function<Integer, Integer> function) {
        return new IntegerApplyVertex(this, function);
    }

    @Override
    default IntegerVertex safeLogTimes(IntegerVertex y) {
        return null;
    }

    @Override
    default IntegerVertex abs() {
        return new IntegerAbsVertex(this);
    }

    @Override
    default IntegerVertex sum() {
        return new IntegerSumVertex(this);
    }

    @Override
    default IntegerVertex sum(int... sumOverDimensions) {
        return new IntegerSumVertex(this, sumOverDimensions);
    }

    @Override
    default IntegerVertex cumSum(int requestedDimension) {
        return new IntegerCumSumVertex(this, requestedDimension);
    }

    @Override
    default IntegerVertex product() {
        return new IntegerProductVertex(this);
    }

    @Override
    default IntegerVertex product(int... overDimensions) {
        return new IntegerProductVertex(this, overDimensions);
    }

    @Override
    default IntegerVertex cumProd(int requestedDimension) {
        return new IntegerCumProdVertex(this, requestedDimension);
    }

    static IntegerVertex min(IntegerVertex a, IntegerVertex b) {
        return new IntegerMinVertex(a, b);
    }

    static IntegerVertex max(IntegerVertex a, IntegerVertex b) {
        return new IntegerMaxVertex(a, b);
    }

    @Override
    default IntegerVertex max() {
        return new IntegerMaxUnaryVertex(this);
    }

    @Override
    default IntegerVertex max(IntegerVertex that) {
        return max(this, that);
    }

    @Override
    default IntegerVertex min() {
        return new IntegerMinUnaryVertex(this);
    }

    @Override
    default IntegerVertex min(IntegerVertex that) {
        return min(this, that);
    }

    @Override
    default IntegerVertex clamp(IntegerVertex min, IntegerVertex max) {
        return null;
    }

    @Override
    default IntegerVertex matrixMultiply(IntegerVertex that) {
        return new IntegerMatrixMultiplyVertex(this, that);
    }

    @Override
    default IntegerVertex tensorMultiply(IntegerVertex value, int[] dimLeft, int[] dimsRight) {
        return new IntegerTensorMultiplyVertex(this, value, dimLeft, dimsRight);
    }

    default IntegerVertex lambda(long[] shape, Function<IntegerTensor, IntegerTensor> op) {
        return new IntegerUnaryOpLambda(shape, this, op);
    }

    default IntegerVertex lambda(Function<IntegerTensor, IntegerTensor> op) {
        return new IntegerUnaryOpLambda(this, op);
    }

    @Override
    default BooleanVertex lessThan(Integer value) {
        return lessThan(new ConstantIntegerVertex(value));
    }

    @Override
    default BooleanVertex lessThanOrEqual(Integer value) {
        return lessThanOrEqual((new ConstantIntegerVertex(value)));
    }

    @Override
    default BooleanVertex greaterThan(Integer value) {
        return greaterThan((new ConstantIntegerVertex(value)));
    }

    @Override
    default BooleanVertex greaterThanOrEqual(Integer value) {
        return greaterThanOrEqual((new ConstantIntegerVertex(value)));
    }

    @Override
    default IntegerVertex greaterThanMask(IntegerVertex greaterThanThis) {
        return new IntegerGreaterThanMaskVertex(this, greaterThanThis);
    }

    @Override
    default IntegerVertex greaterThanOrEqualToMask(IntegerVertex greaterThanOrEqualThis) {
        return new IntegerGreaterThanOrEqualMaskVertex(this, greaterThanOrEqualThis);
    }

    @Override
    default IntegerVertex lessThanMask(IntegerVertex lessThanThis) {
        return new IntegerLessThanMaskVertex(this, lessThanThis);
    }

    @Override
    default IntegerVertex lessThanOrEqualToMask(IntegerVertex lessThanOrEqualThis) {
        return new IntegerLessThanOrEqualMaskVertex(this, lessThanOrEqualThis);
    }

    //////////////////////////
    ////  Fixed Point Tensor Operations
    //////////////////////////

    @Override
    default IntegerVertex mod(Integer that) {
        return mod(new ConstantIntegerVertex(that));
    }

    @Override
    default IntegerVertex mod(IntegerVertex that) {
        return new IntegerModVertex(this, that);
    }

    @Override
    default Class<?> ofType() {
        return IntegerTensor.class;
    }
}
