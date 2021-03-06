package io.improbable.keanu.vertices.tensor.generic.nonprobabilistic;

import io.improbable.keanu.tensor.TensorShape;
import io.improbable.keanu.tensor.intgr.IntegerTensor;
import io.improbable.keanu.vertices.LoadVertexParam;
import io.improbable.keanu.vertices.NonProbabilistic;
import io.improbable.keanu.vertices.SaveVertexParam;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.VertexImpl;
import io.improbable.keanu.vertices.tensor.generic.GenericVertex;
import io.improbable.keanu.vertices.tensor.number.fixed.intgr.IntegerVertex;

public class MultiplexerVertex<T> extends VertexImpl<T, GenericVertex<T>> implements GenericVertex<T>, NonProbabilistic<T> {

    private final static String SELECTOR_CONTROL_NAME = "selectorControlVertex";
    private final static String SELECT_VERTICES_NAME = "selectVertices";
    private final IntegerVertex selectorControlVertex;
    private final Vertex<T, ?>[] selectVertices;

    public MultiplexerVertex(@LoadVertexParam(SELECTOR_CONTROL_NAME) IntegerVertex selectorControlVertex,
                             @LoadVertexParam(SELECT_VERTICES_NAME) Vertex<T, ?>... select) {

        if (!TensorShape.isScalar(selectorControlVertex.getShape())) {
            throw new IllegalArgumentException("Select control must be scalar integer");
        }

        this.selectVertices = select;
        this.selectorControlVertex = selectorControlVertex;
        setParents(select);
        addParent(selectorControlVertex);
    }

    private Vertex<T, ?> getSelector() {
        int optionGroupIdx = selectorControlVertex.getValue().scalar();
        return selectVertices[optionGroupIdx];
    }

    @Override
    public T calculate() {
        Vertex<T, ?> selector = getSelector();
        return selector.getValue();
    }

    public static <T> T mux(IntegerTensor select, T... outputs) {
        return outputs[select.scalar()];
    }

    @SaveVertexParam(SELECTOR_CONTROL_NAME)
    public IntegerVertex getSelectorControlVertex() {
        return selectorControlVertex;
    }

    @SaveVertexParam(SELECT_VERTICES_NAME)
    public Vertex<T, ?>[] getSelectVertices() {
        return selectVertices;
    }
}