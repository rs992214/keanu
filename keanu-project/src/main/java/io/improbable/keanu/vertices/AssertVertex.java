package io.improbable.keanu.vertices;

import io.improbable.keanu.tensor.bool.BooleanTensor;
import io.improbable.keanu.vertices.bool.BoolVertex;
import io.improbable.keanu.vertices.dbl.KeanuRandom;

import java.util.Optional;

public class AssertVertex extends BoolVertex implements NonProbabilistic<BooleanTensor>, NonSaveableVertex {

    private final Vertex<? extends BooleanTensor> predicate;
    private Optional<String> errorMessage = Optional.empty();

    public AssertVertex(Vertex<? extends BooleanTensor> predicate, String errorMessage) {
        this(predicate);
        this.errorMessage = Optional.of(errorMessage);
    }

    public AssertVertex(Vertex<? extends BooleanTensor> predicate) {
        super(predicate.getShape());
        this.predicate = predicate;
        setParents(predicate);
    }


    @Override
    public BooleanTensor calculate() {
        assertion();
        return predicate.getValue();
    }

    private void assertion() {
        if (!predicate.getValue().allTrue()) {
            throw new AssertionError(buildAssertMessage());
        }
    }

    private String buildAssertMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AssertVertex");
        if (getLabel() != null) {
            stringBuilder.append(" (" + getLabel().getQualifiedName() + ")");
        }
        errorMessage.ifPresent((s -> stringBuilder.append(": " + s)));
        return stringBuilder.toString();
    }

    @Override
    public BooleanTensor sample(KeanuRandom random) {
        return predicate.sample();
    }

}
