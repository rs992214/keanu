package io.improbable.keanu.vertices.tensor.number.floating.dbl.nonprobabilistic.operators.multiple;

import io.improbable.keanu.annotation.ExportVertexToPythonBindings;
import io.improbable.keanu.tensor.TensorShape;
import io.improbable.keanu.tensor.dbl.DoubleTensor;
import io.improbable.keanu.vertices.LoadVertexParam;
import io.improbable.keanu.vertices.NonProbabilistic;
import io.improbable.keanu.vertices.SaveVertexParam;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.VertexImpl;
import io.improbable.keanu.vertices.tensor.number.floating.dbl.Differentiable;
import io.improbable.keanu.vertices.tensor.number.floating.dbl.DoubleVertex;
import io.improbable.keanu.vertices.tensor.number.floating.dbl.nonprobabilistic.diff.ForwardModePartialDerivative;
import io.improbable.keanu.vertices.tensor.number.floating.dbl.nonprobabilistic.diff.ReverseModePartialDerivative;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static io.improbable.keanu.tensor.TensorShapeValidation.checkShapesCanBeConcatenated;

public class ConcatenationVertex extends VertexImpl<DoubleTensor, DoubleVertex> implements DoubleVertex, Differentiable, NonProbabilistic<DoubleTensor> {

    private final static String DIMENSION_NAME = "dimension";
    private final static String OPERANDS_NAME = "operands";

    private final int dimension;
    private final Vertex<DoubleTensor, ?>[] operands;

    /**
     * A vertex that can concatenate any amount of vertices along a given dimension.
     *
     * @param dimension the dimension to concatenate on. This is the only dimension in which sizes may be different. Negative
     *                  dimension indexing is not supported.
     * @param operands  the operands vertices to concatenate
     */
    @ExportVertexToPythonBindings
    public ConcatenationVertex(@LoadVertexParam(DIMENSION_NAME) int dimension,
                               @LoadVertexParam(OPERANDS_NAME) Vertex<DoubleTensor, ?>... operands) {
        super(checkShapesCanBeConcatenated(dimension, extractFromInputs(long[].class, Vertex::getShape, operands)));
        this.dimension = dimension;
        this.operands = operands;
        setParents(operands);
    }

    @Override
    public ForwardModePartialDerivative forwardModeAutoDifferentiation(Map<Vertex, ForwardModePartialDerivative> derivativeOfParentsWithRespectToInput) {
        List<ForwardModePartialDerivative> partialsOfOperands = new ArrayList<>();
        List<DoubleTensor> operandValues = new ArrayList<>();

        for (Vertex<DoubleTensor, ?> operand : operands) {
            ForwardModePartialDerivative operandPartial = derivativeOfParentsWithRespectToInput.getOrDefault(operand, ForwardModePartialDerivative.EMPTY);
            partialsOfOperands.add(operandPartial);
            operandValues.add(operand.getValue());
        }

        return concat(partialsOfOperands, operandValues, dimension);
    }

    public static ForwardModePartialDerivative concat(List<ForwardModePartialDerivative> partialsOfOperands,
                                                      List<DoubleTensor> operandValues,
                                                      int dimension) {

        long[] wrtShape = null;
        for (int i = 0; i < partialsOfOperands.size(); i++) {
            ForwardModePartialDerivative partial = partialsOfOperands.get(i);

            if (partial.isPresent()) {
                wrtShape = partial.getWrtShape();
                break;
            }
        }

        List<DoubleTensor> partialsToConcat = getPartialsToConcatForInput(
            partialsOfOperands,
            operandValues,
            wrtShape
        );

        return new ForwardModePartialDerivative(wrtShape, concatPartialDerivatives(dimension + wrtShape.length, partialsToConcat));
    }

    private static List<DoubleTensor> getPartialsToConcatForInput(List<ForwardModePartialDerivative> partialsOfOperands,
                                                                  List<DoubleTensor> operandValues,
                                                                  long[] wrtShape) {

        List<DoubleTensor> partialsToConcat = new ArrayList<>();

        for (int i = 0; i < operandValues.size(); i++) {
            ForwardModePartialDerivative partialOfOperand = partialsOfOperands.get(i);
            DoubleTensor operandValue = operandValues.get(i);

            if (partialOfOperand.isPresent()) {
                partialsToConcat.add(partialOfOperand.get());
            } else {
                long[] resultShape = TensorShape.concat(wrtShape, operandValue.getShape());
                partialsToConcat.add(DoubleTensor.zeros(resultShape));
            }

        }

        return partialsToConcat;
    }

    private static DoubleTensor concatPartialDerivatives(int dimension, List<DoubleTensor> partialDerivatives) {
        if (partialDerivatives.size() == 1) {
            return partialDerivatives.get(0);
        } else {
            DoubleTensor[] derivativesToConcat = new DoubleTensor[partialDerivatives.size()];
            return DoubleTensor.concat(dimension, partialDerivatives.toArray(derivativesToConcat));
        }
    }

    @Override
    public Map<Vertex, ReverseModePartialDerivative> reverseModeAutoDifferentiation(ReverseModePartialDerivative derivativeOfOutputWithRespectToSelf) {
        Map<Vertex, ReverseModePartialDerivative> splitPartials = new HashMap<>();

        long currentSplitIndex = 0;
        long[] splitIndices = new long[operands.length];

        for (int i = 0; i < operands.length; i++) {
            splitIndices[i] = currentSplitIndex + operands[i].getShape()[dimension];
            currentSplitIndex = splitIndices[i];
        }

        int operandsRank = operands[0].getRank();
        int wrtStartsAt = -operandsRank;
        int wrtSplitOn = wrtStartsAt + dimension;

        DoubleTensor partial = derivativeOfOutputWithRespectToSelf.get();

        List<DoubleTensor> splitPartial = partial.split(wrtSplitOn, splitIndices);

        for (int i = 0; i < splitPartial.size(); i++) {
            splitPartials.put(operands[i], new ReverseModePartialDerivative(derivativeOfOutputWithRespectToSelf.getOfShape(), splitPartial.get(i)));
        }

        return splitPartials;
    }

    @Override
    public DoubleTensor calculate() {
        return op(extractFromInputs(DoubleTensor.class, Vertex::getValue, operands));
    }

    protected DoubleTensor op(DoubleTensor... inputs) {
        return DoubleTensor.concat(dimension, inputs);
    }

    private static <T> T[] extractFromInputs(Class<T> clazz, Function<Vertex<DoubleTensor, ?>, T> func, Vertex<DoubleTensor, ?>[] operands) {
        T[] extract = (T[]) Array.newInstance(clazz, operands.length);
        for (int i = 0; i < operands.length; i++) {
            extract[i] = func.apply(operands[i]);
        }
        return extract;
    }

    @SaveVertexParam(OPERANDS_NAME)
    public Vertex<DoubleTensor, ?>[] getOperands() {
        return operands;
    }

    @SaveVertexParam(DIMENSION_NAME)
    public int getDimension() {
        return dimension;
    }
}
