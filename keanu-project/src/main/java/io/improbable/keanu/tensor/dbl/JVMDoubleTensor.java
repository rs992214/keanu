package io.improbable.keanu.tensor.dbl;

import com.google.common.primitives.Ints;
import io.improbable.keanu.tensor.FloatingPointScalarOperations;
import io.improbable.keanu.tensor.TensorShape;
import io.improbable.keanu.tensor.TensorShapeValidation;
import io.improbable.keanu.tensor.bool.BooleanTensor;
import io.improbable.keanu.tensor.bool.JVMBooleanTensor;
import io.improbable.keanu.tensor.intgr.IntegerTensor;
import io.improbable.keanu.tensor.jvm.JVMFloatingPointTensor;
import io.improbable.keanu.tensor.jvm.JVMNumberTensor;
import io.improbable.keanu.tensor.jvm.JVMTensor;
import io.improbable.keanu.tensor.jvm.ResultWrapper;
import io.improbable.keanu.tensor.lng.JVMLongTensorFactory;
import io.improbable.keanu.tensor.lng.LongTensor;
import org.apache.commons.math3.linear.SingularMatrixException;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import static io.improbable.keanu.tensor.TensorShape.getRowFirstStride;
import static io.improbable.keanu.tensor.dbl.KeanuLapack.dgetrf;
import static io.improbable.keanu.tensor.dbl.KeanuLapack.dgetri;
import static io.improbable.keanu.tensor.dbl.KeanuLapack.dpotrf;
import static org.bytedeco.openblas.global.openblas.CblasNoTrans;
import static org.bytedeco.openblas.global.openblas.CblasRowMajor;
import static org.bytedeco.openblas.global.openblas.cblas_dgemm;

public class JVMDoubleTensor extends JVMFloatingPointTensor<Double, DoubleTensor, DoubleBuffer.PrimitiveDoubleWrapper> implements DoubleTensor {

    static final DoubleBuffer.DoubleArrayWrapperFactory factory = new DoubleBuffer.DoubleArrayWrapperFactory();

    JVMDoubleTensor(DoubleBuffer.PrimitiveDoubleWrapper buffer, long[] shape, long[] stride) {
        super(buffer, shape, stride);
    }

    JVMDoubleTensor(DoubleBuffer.PrimitiveDoubleWrapper buffer, long[] shape) {
        super(buffer, shape, getRowFirstStride(shape));
    }

    JVMDoubleTensor(ResultWrapper<Double, DoubleBuffer.PrimitiveDoubleWrapper> resultWrapper) {
        this(resultWrapper.outputBuffer, resultWrapper.outputShape, resultWrapper.outputStride);
    }

    JVMDoubleTensor(double[] data, long[] shape, long[] stride) {
        this(factory.create(data), shape, stride);
    }

    JVMDoubleTensor(double[] data, long[] shape) {
        this(factory.create(data), shape);
    }

    JVMDoubleTensor(double value) {
        super(new DoubleBuffer.DoubleWrapper(value), new long[0], new long[0]);
    }

    @Override
    protected DoubleTensor create(DoubleBuffer.PrimitiveDoubleWrapper buffer, long[] shape, long[] stride) {
        return new JVMDoubleTensor(buffer, shape, stride);
    }

    @Override
    protected DoubleTensor set(DoubleBuffer.PrimitiveDoubleWrapper buffer, long[] shape, long[] stride) {
        this.buffer = buffer;
        this.shape = shape;
        this.stride = stride;
        return this;
    }

    @Override
    protected DoubleBuffer.DoubleArrayWrapperFactory getFactory() {
        return factory;
    }

    @Override
    protected FloatingPointScalarOperations<Double> getOperations() {
        return DoubleScalarOperations.INSTANCE;
    }

    @Override
    protected JVMTensor<Double, DoubleTensor, DoubleBuffer.PrimitiveDoubleWrapper> getAsJVMTensor(DoubleTensor that) {
        return asJVM(that);
    }

    static JVMNumberTensor<Double, DoubleTensor, DoubleBuffer.PrimitiveDoubleWrapper> asJVM(DoubleTensor that) {
        if (that instanceof JVMDoubleTensor) {
            return ((JVMDoubleTensor) that);
        } else {
            return new JVMDoubleTensor(factory.create(that.asFlatDoubleArray()), that.getShape(), that.getStride());
        }
    }

    @Override
    public BooleanTensor toBoolean() {
        return new JVMBooleanTensor(buffer.equal(1.0), getShape(), getStride());
    }

    @Override
    public DoubleTensor toDouble() {
        return this;
    }

    @Override
    public IntegerTensor toInteger() {
        return IntegerTensor.create(buffer.asIntegerArray(), getShape());
    }

    @Override
    public LongTensor toLong() {
        return JVMLongTensorFactory.INSTANCE.create(buffer.asLongArray(), getShape());
    }

    @Override
    public double[] asFlatDoubleArray() {
        return buffer.copy().asDoubleArray();
    }

    @Override
    public int[] asFlatIntegerArray() {
        return buffer.asIntegerArray();
    }

    @Override
    public long[] asFlatLongArray() {
        return buffer.asLongArray();
    }

    @Override
    public Double[] asFlatArray() {
        return buffer.asArray();
    }

    @Override
    public DoubleTensor choleskyDecomposition() {

        if (shape.length != 2 || shape[0] != shape[1]) {
            throw new IllegalArgumentException("Cholesky decomposition must be performed on square matrix.");
        }

        int N = Ints.checkedCast(shape[0]);
        double[] newBuffer = buffer.copy().asDoubleArray();

        int result = dpotrf(KeanuLapack.Triangular.LOWER, N, newBuffer);

        if (result != 0) {
            throw new IllegalStateException("Cholesky decomposition failed");
        }

        zeroOutUpperTriangle(N, newBuffer);

        return new JVMDoubleTensor(newBuffer, getShape(), getStride());
    }

    private void zeroOutUpperTriangle(int N, double[] buffer) {
        if (N > 1) {
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    buffer[i * N + j] = 0;
                }
            }
        }
    }

    @Override
    public DoubleTensor matrixDeterminant() {

        final int m = Ints.checkedCast(shape[0]);
        final int n = Ints.checkedCast(shape[1]);
        final double[] newBuffer = buffer.copy().asDoubleArray();
        final int[] ipiv = new int[newBuffer.length];

        final int factorizationResult = dgetrf(m, n, newBuffer, ipiv);

        if (factorizationResult < 0) {
            throw new IllegalStateException("Matrix factorization failed");
        } else if (factorizationResult > 0) {
            return new JVMDoubleTensor(0.0);
        }

        //credit: https://stackoverflow.com/questions/47315471/compute-determinant-from-lu-decomposition-in-lapack
        int j;
        double detp = 1.;
        for (j = 0; j < n; j++) {
            if (j + 1 != ipiv[j]) {
                detp = -detp;
            }
        }

        double detU = 1.0;
        for (int i = 0; i < m; i++) {
            detU *= newBuffer[i * m + i];
        }

        return new JVMDoubleTensor(detU * detp);
    }

    @Override
    public DoubleTensor matrixInverse() {

        final int m = Ints.checkedCast(shape[0]);
        final int n = Ints.checkedCast(shape[1]);
        final double[] newBuffer = buffer.copy().asDoubleArray();
        final int[] ipiv = new int[newBuffer.length];

        final int factorizationResult = dgetrf(m, n, newBuffer, ipiv);

        if (factorizationResult < 0) {
            throw new IllegalStateException("Matrix factorization failed");
        } else if (factorizationResult > 0) {
            throw new SingularMatrixException();
        }

        int inverseResult = dgetri(m, newBuffer, ipiv);

        if (inverseResult != 0) {
            throw new IllegalStateException("Matrix inverse failed");
        }

        return new JVMDoubleTensor(newBuffer, getShape(), getStride());
    }

    @Override
    public DoubleTensor matrixMultiply(DoubleTensor that) {

        final long[] thatShape = that.getShape();
        TensorShapeValidation.getMatrixMultiplicationResultingShape(shape, thatShape);

        //C = alpha*A*B + beta*C
        //(M,N) = (M,k)(k,N) + (M,N)
        final double[] A = buffer.asDoubleArray();
        final double[] B = getAsJVMTensor(that).getBuffer().asDoubleArray();
        final double[] C = new double[Ints.checkedCast(this.shape[0] * thatShape[1])];

        final int N = Ints.checkedCast(thatShape[1]);
        final int M = Ints.checkedCast(this.shape[0]);
        final int K = Ints.checkedCast(this.shape[1]);

        cblas_dgemm(CblasRowMajor, CblasNoTrans, CblasNoTrans, M, N, K, 1, A, K, B, N, 0, C, N);

        return new JVMDoubleTensor(C, new long[]{this.shape[0], thatShape[1]});
    }

    @Override
    public DoubleTensor setWithMaskInPlace(DoubleTensor mask, Double value) {
        return broadcastableBinaryOpWithAutoBroadcastInPlace((l, r) -> r == 1.0 ? value : l, getAsJVMTensor(mask));
    }

    @Override
    public DoubleTensor mean() {
        return new JVMDoubleTensor(buffer.sum() / buffer.getLength());
    }

    @Override
    public DoubleTensor mean(int... overDimensions) {
        final long length = TensorShape.getLength(shape, overDimensions);
        return sum(overDimensions).divInPlace((double) length);
    }

    @Override
    public DoubleTensor standardDeviation() {

        SummaryStatistics stats = new SummaryStatistics();
        for (int i = 0; i < buffer.getLength(); i++) {
            stats.addValue(buffer.get(i));
        }

        return new JVMDoubleTensor(stats.getStandardDeviation());
    }

    @Override
    public DoubleTensor replaceNaNInPlace(Double value) {
        buffer.apply(v -> Double.isNaN(v) ? value : v);
        return this;
    }

}
