package io.improbable.keanu;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import io.improbable.keanu.tensor.TensorShape;
import io.improbable.keanu.tensor.jvm.Slicer;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.improbable.keanu.tensor.TensorShape.getAbsoluteDimension;

public interface BaseTensor<
    BOOLEAN extends BaseTensor<BOOLEAN, Boolean, BOOLEAN>,
    N,
    T extends BaseTensor<BOOLEAN, N, T>
    > {

    long[] SCALAR_SHAPE = new long[]{};

    int getRank();

    long[] getShape();

    /**
     * Returns the stride for each dimension of the tensor (based on C ordering).
     * <p>
     * The stride is the distance you'd move in a flat representation of the tensor for each index within that dimension
     * EG) For a 2x2 Tensor the Tensor would be laid out (in C order):
     * [{0, 0}, {0, 1}, {1, 0}, {1, 1}]
     * Thus the stride array would be provided as:
     * [2, 1]
     *
     * @return The stride array for this tensor
     */
    long[] getStride();

    long getLength();

    /**
     * @param booleanIndex a boolean tensor the same shape as this tensor where true is specified if the element
     *                     should be kept and false if not.
     * @return a vector with the values that align with true from the boolean index.
     */
    T get(BOOLEAN booleanIndex);

    T slice(int dimension, long index);

    default T slice(String sliceArg) {
        return slice(Slicer.fromString(sliceArg));
    }

    T slice(Slicer slicer);

    T take(long... index);

    /**
     * @param dimension      the dimension to split on
     * @param splitAtIndices the indices that the dimension to split on should be split on
     * @return pieces of the tensor split in the order specified by splitAtIndices. To get
     * pieces that encompasses the entire tensor, the last index in the splitAtIndices must
     * be the length of the dimension being split on.
     * <p>
     * e.g A =
     * [
     * 1, 2, 3, 4, 5, 6
     * 7, 8, 9, 1, 2, 3
     * ]
     * <p>
     * A.split(0, [1]) gives List([1, 2, 3, 4, 5, 6])
     * A.split(0, [1, 2]) gives List([1, 2, 3, 4, 5, 6], [7, 8, 9, 1, 2, 3]
     * <p>
     * A.split(1, [1, 3, 6]) gives
     * List(
     * [1, [2, 3  , [4, 5, 6,
     * 7]  8, 9]    1, 2, 3]
     * )
     */
    List<T> split(int dimension, long... splitAtIndices);

    default List<T> sliceAlongDimension(int dimension, long indexStart, long indexEnd) {
        List<T> slicedTensors = new ArrayList<>();

        for (long i = indexStart; i < indexEnd; i++) {
            slicedTensors.add(slice(dimension, i));
        }

        return slicedTensors;
    }

    T diag();

    default T transpose() {
        Preconditions.checkArgument(
            getRank() == 2,
            "Can only transpose rank 2. Use permute(...) for higher rank transpose."
        );
        return permute(1, 0);
    }

    T reshape(long... newShape);

    default T squeeze() {
        final long[] shape = getShape();
        List<Long> squeezedShape = new ArrayList<>();
        for (long length : shape) {
            if (length > 1) {
                squeezedShape.add(length);
            }
        }
        return reshape(Longs.toArray(squeezedShape));
    }

    default T expandDims(int axis) {
        final long[] shape = getShape();
        return reshape(ArrayUtils.insert(axis, shape, 1L));
    }

    default T moveAxis(int source, int destination) {

        int[] dimensionRange = TensorShape.dimensionRange(0, getRank());
        source = getAbsoluteDimension(source, dimensionRange.length);
        destination = getAbsoluteDimension(destination, dimensionRange.length);

        int[] rearrange = ArrayUtils.insert(destination, ArrayUtils.remove(dimensionRange, source), source);

        return permute(rearrange);
    }

    default T swapAxis(int axis1, int axis2) {

        int[] rearrange = TensorShape.dimensionRange(0, getRank());
        axis1 = getAbsoluteDimension(axis1, rearrange.length);
        axis2 = getAbsoluteDimension(axis2, rearrange.length);

        final int temp = rearrange[axis1];
        rearrange[axis1] = axis2;
        rearrange[axis2] = temp;

        return permute(rearrange);
    }

    T permute(int... rearrange);

    T broadcast(long... toShape);

    default boolean isLengthOne() {
        return getLength() == 1;
    }

    default boolean isScalar() {
        return getRank() == 0;
    }

    /**
     * Returns true if the tensor is a vector. A vector being a 1xn or a nx1 tensor.
     * <p>
     * (1, 2, 3) is a 1x3 vector.
     * <p>
     * (1)
     * (2)
     * (3) is a 3x1 vector.
     *
     * @return true if the tensor is a vector
     */
    default boolean isVector() {
        return getRank() == 1;
    }

    default boolean isMatrix() {
        return getRank() == 2;
    }

    default boolean hasSameShapeAs(T that) {
        return hasSameShapeAs(that.getShape());
    }

    default boolean hasSameShapeAs(long[] shape) {
        return Arrays.equals(this.getShape(), shape);
    }

    BOOLEAN elementwiseEquals(T that);

    BOOLEAN elementwiseEquals(N value);

    BOOLEAN notEqualTo(T that);

    BOOLEAN notEqualTo(N value);
}