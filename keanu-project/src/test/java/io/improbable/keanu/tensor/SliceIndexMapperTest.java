package io.improbable.keanu.tensor;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SliceIndexMapperTest {

    @Test
    public void canCalculateResultShape() {

        Slicer slicer = Slicer.builder()
            .slice(2, 7, 2)
            .build();

        SliceIndexMapper sliceIndexMapper = new SliceIndexMapper(slicer, new long[]{10}, new long[]{1});

        assertThat(sliceIndexMapper.getResultShape(), equalTo(new long[]{3}));
    }

    @Test
    public void canCalculateResultShapeOf2Dimensions() {

        Slicer slicer = Slicer.builder()
            .slice(2, 7, 2)
            .slice(2)
            .build();

        long[] shape = new long[]{10, 3};
        long[] stride = TensorShape.getRowFirstStride(shape);

        SliceIndexMapper sliceIndexMapper = new SliceIndexMapper(slicer, shape, stride);

        assertThat(sliceIndexMapper.getResultShape(), equalTo(new long[]{3}));
    }

    @Test
    public void canCalculateIndexMapToSourceBuffer() {

        Slicer slicer = Slicer.builder()
            .slice(2, 7, 2)
            .build();

        SliceIndexMapper sliceIndexMapper = new SliceIndexMapper(slicer, new long[]{10}, new long[]{1});

        assertThat(sliceIndexMapper.getSourceIndexFromResultIndex(0), equalTo(2L));
        assertThat(sliceIndexMapper.getSourceIndexFromResultIndex(1), equalTo(4L));
        assertThat(sliceIndexMapper.getSourceIndexFromResultIndex(2), equalTo(6L));
    }

    @Test
    public void canCalculateIndexMapToSourceBufferFor2Dims() {

        Slicer slicer = Slicer.builder()
            .slice(1, 4, 2)
            .slice(0, 2)
            .build();

        long[] shape = new long[]{10, 3};
        long[] stride = TensorShape.getRowFirstStride(shape);

        SliceIndexMapper sliceIndexMapper = new SliceIndexMapper(slicer, shape, stride);

        long[] resultShape = sliceIndexMapper.getResultShape();

        assertThat(resultShape, equalTo(new long[]{2, 2}));

        assertThat(sliceIndexMapper.getSourceIndexFromResultIndex(0), equalTo(TensorShape.getFlatIndex(shape, stride, 1, 0)));
        assertThat(sliceIndexMapper.getSourceIndexFromResultIndex(1), equalTo(TensorShape.getFlatIndex(shape, stride, 1, 1)));
        assertThat(sliceIndexMapper.getSourceIndexFromResultIndex(2), equalTo(TensorShape.getFlatIndex(shape, stride, 3, 0)));
        assertThat(sliceIndexMapper.getSourceIndexFromResultIndex(3), equalTo(TensorShape.getFlatIndex(shape, stride, 3, 1)));
    }

    @Test
    public void canCalculateIndexMapToSourceBufferFor2DimsWhere2ndDimIsDropped() {

        Slicer slicer = Slicer.builder()
            .slice(1, 4, 2)
            .slice(2)
            .build();

        long[] shape = new long[]{10, 3};
        long[] stride = TensorShape.getRowFirstStride(shape);

        SliceIndexMapper sliceIndexMapper = new SliceIndexMapper(slicer, shape, stride);

        long[] resultShape = sliceIndexMapper.getResultShape();

        assertThat(resultShape, equalTo(new long[]{2}));

        assertThat(sliceIndexMapper.getSourceIndexFromResultIndex(0), equalTo(TensorShape.getFlatIndex(shape, stride, 1, 2)));
        assertThat(sliceIndexMapper.getSourceIndexFromResultIndex(1), equalTo(TensorShape.getFlatIndex(shape, stride, 3, 2)));
    }
}
