package io.improbable.keanu.vertices.tensor.generic;

import io.improbable.keanu.tensor.generic.GenericTensor;
import io.improbable.keanu.vertices.NonProbabilisticVertex;
import io.improbable.keanu.vertices.tensor.TensorVertex;
import io.improbable.keanu.vertices.tensor.bool.BooleanVertex;
import io.improbable.keanu.vertices.tensor.generic.nonprobabilistic.ConstantGenericTensorVertex;

public interface GenericTensorVertex<T> extends TensorVertex<T, GenericTensor<T>, GenericTensorVertex<T>> {

    default GenericTensorVertex<T> wrap(NonProbabilisticVertex<GenericTensor<T>, GenericTensorVertex<T>> vertex) {
        return new GenericVertexWrapper<>(vertex);
    }

    default BooleanVertex elementwiseEquals(T that) {
        return elementwiseEquals(new ConstantGenericTensorVertex<>(that));
    }

    default BooleanVertex notEqualTo(T that) {
        return notEqualTo(new ConstantGenericTensorVertex<>(that));
    }

    @Override
    default Class<?> ofType() {
        return GenericTensor.class;
    }

}
