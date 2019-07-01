package io.improbable.keanu.tensor.dbl;

import io.improbable.keanu.tensor.buffer.JVMBuffer;
import io.improbable.keanu.tensor.buffer.PrimitiveNumberWrapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.util.FastMath;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DoubleBuffer {

    public static final class DoubleArrayWrapperFactory implements JVMBuffer.ArrayWrapperFactory<Double, PrimitiveDoubleWrapper> {

        @Override
        public final PrimitiveDoubleWrapper createNew(final int size) {
            if (size == 1) {
                return new DoubleWrapper(0);
            } else {
                return new DoubleArrayWrapper(new double[size]);
            }
        }

        public final PrimitiveDoubleWrapper create(double[] data) {
            if (data.length == 1) {
                return new DoubleWrapper(data[0]);
            } else {
                return new DoubleArrayWrapper(data);
            }
        }
    }

    public interface PrimitiveDoubleWrapper extends PrimitiveNumberWrapper<Double, PrimitiveDoubleWrapper> {
    }

    public static final class DoubleArrayWrapper implements PrimitiveDoubleWrapper {

        private final double[] array;

        public DoubleArrayWrapper(final double[] array) {
            this.array = array;
        }

        @Override
        public Double get(final int index) {
            return array[index];
        }

        @Override
        public void set(final Double value, final int index) {
            array[index] = value;
        }

        @Override
        public int getLength() {
            return array.length;
        }

        @Override
        public PrimitiveDoubleWrapper copy() {
            return new DoubleArrayWrapper(Arrays.copyOf(array, array.length));
        }

        @Override
        public void copyFrom(JVMBuffer.PrimitiveArrayWrapper<Double, ?> src, int srcPos, int destPos, int length) {
            if (src instanceof DoubleArrayWrapper) {
                System.arraycopy(((DoubleArrayWrapper) src).array, srcPos, array, destPos, length);
            } else {
                for (int i = 0; i < length; i++) {
                    array[destPos + i] = src.get(srcPos + i);
                }
            }
        }

        @Override
        public Double sum() {
            double result = 0;
            for (int i = 0; i < array.length; i++) {
                result += array[i];
            }
            return result;

        }

        @Override
        public void times(Double that) {
            for (int i = 0; i < array.length; i++) {
                array[i] *= that;
            }
        }

        @Override
        public void div(Double that) {
            for (int i = 0; i < array.length; i++) {
                array[i] /= that;
            }
        }

        @Override
        public void plus(Double that) {
            for (int i = 0; i < array.length; i++) {
                array[i] += that;
            }
        }

        @Override
        public void plus(int index, Double that) {
            array[index] += that;
        }

        @Override
        public void minus(Double that) {
            for (int i = 0; i < array.length; i++) {
                array[i] -= that;
            }
        }

        @Override
        public void pow(Double that) {
            for (int i = 0; i < array.length; i++) {
                array[i] = FastMath.pow(array[i], that);
            }
        }

        @Override
        public void reverseDiv(Double that) {
            for (int i = 0; i < array.length; i++) {
                array[i] = that / array[i];
            }
        }

        @Override
        public void reverseMinus(Double that) {
            for (int i = 0; i < array.length; i++) {
                array[i] = that - array[i];
            }
        }

        @Override
        public void applyRight(BiFunction<Double, Double, Double> mapper, Double rightArg) {
            for (int i = 0; i < array.length; i++) {
                array[i] = mapper.apply(array[i], rightArg);
            }
        }

        @Override
        public void applyLeft(BiFunction<Double, Double, Double> mapper, Double leftArg) {
            for (int i = 0; i < array.length; i++) {
                array[i] = mapper.apply(leftArg, array[i]);
            }
        }

        @Override
        public void apply(Function<Double, Double> mapper) {
            for (int i = 0; i < array.length; i++) {
                array[i] = mapper.apply(array[i]);
            }
        }

        @Override
        public int[] asIntegerArray() {

            int[] intBuffer = new int[array.length];
            for (int i = 0; i < array.length; i++) {
                intBuffer[i] = (int) array[i];
            }
            return intBuffer;
        }

        @Override
        public double[] asDoubleArray() {
            return array;
        }

        @Override
        public Double[] asArray() {
            return ArrayUtils.toObject(array);
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof PrimitiveDoubleWrapper)) return false;
            final PrimitiveDoubleWrapper other = (PrimitiveDoubleWrapper) o;
            if (!Arrays.equals(this.array, other.asDoubleArray())) return false;
            return true;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            result = result * PRIME + Arrays.hashCode(this.array);
            return result;
        }
    }

    public static final class DoubleWrapper extends JVMBuffer.SingleValueWrapper<Double, PrimitiveDoubleWrapper> implements PrimitiveDoubleWrapper {

        public DoubleWrapper(final double value) {
            super(value);
        }

        @Override
        public Double sum() {
            return value;
        }

        @Override
        public void times(Double that) {
            value *= that;
        }

        @Override
        public void div(Double that) {
            value /= that;
        }

        @Override
        public void plus(Double that) {
            value += that;
        }

        @Override
        public void plus(int index, Double that) {
            value += that;
        }

        @Override
        public void minus(Double that) {
            value -= that;
        }

        @Override
        public void pow(Double that) {
            value = FastMath.pow(value, that);
        }

        @Override
        public void reverseDiv(Double that) {
            value = that / value;
        }

        @Override
        public void reverseMinus(Double that) {
            value = that - value;
        }

        @Override
        public int[] asIntegerArray() {
            return new int[]{value.intValue()};
        }

        @Override
        public double[] asDoubleArray() {
            return new double[]{value};
        }

        @Override
        public Double[] asArray() {
            return new Double[]{value};
        }

        @Override
        public PrimitiveDoubleWrapper copy() {
            return new DoubleWrapper(value);
        }
    }
}