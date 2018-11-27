package io.improbable.keanu.network;

import com.google.common.primitives.Booleans;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import io.improbable.keanu.KeanuSavedBayesNet;
import io.improbable.keanu.tensor.bool.BooleanTensor;
import io.improbable.keanu.tensor.dbl.DoubleTensor;
import io.improbable.keanu.tensor.intgr.IntegerTensor;
import io.improbable.keanu.vertices.LoadVertexParam;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.VertexLabel;
import io.improbable.keanu.vertices.bool.BoolVertex;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.intgr.IntegerVertex;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class ProtobufLoader implements NetworkLoader {

    private final Map<Vertex, KeanuSavedBayesNet.VertexValue> savedValues;

    public ProtobufLoader() {
        savedValues = new HashMap<>();
    }

    @Override
    public void loadValue(Vertex vertex) {
        throw new IllegalArgumentException("Cannot Load value for Untyped Vertex");
    }

    public BayesianNetwork loadNetwork(InputStream input) throws IOException {
        Map<KeanuSavedBayesNet.VertexID, Vertex> instantiatedVertices = new HashMap<>();
        KeanuSavedBayesNet.BayesianNetwork parsedNet = KeanuSavedBayesNet.BayesianNetwork.parseFrom(input);

        for (KeanuSavedBayesNet.Vertex vertex : parsedNet.getVerticesList()) {
            Vertex newVertex = createVertexFromProtoBuf(vertex, instantiatedVertices);
            instantiatedVertices.put(vertex.getId(), newVertex);
        }

        BayesianNetwork bayesNet = new BayesianNetwork(instantiatedVertices.values());

        loadDefaultValues(parsedNet, instantiatedVertices, bayesNet);

        return bayesNet;
    }

    @Override
    public void loadValue(DoubleVertex vertex) {
        KeanuSavedBayesNet.VertexValue value = savedValues.get(vertex);
        DoubleTensor tensor = extractDoubleValue(value);
        vertex.setValue(tensor);
    }

    private DoubleTensor extractDoubleValue(KeanuSavedBayesNet.VertexValue value) {
        if (value.getValueTypeCase() != KeanuSavedBayesNet.VertexValue.ValueTypeCase.DOUBLEVAL) {
            throw new IllegalArgumentException("Non Double Value specified for Double Vertex");
        } else {
            return extractDoubleTensor(value.getDoubleVal());
        }
    }

    private void loadDefaultValues(KeanuSavedBayesNet.BayesianNetwork parsedNet,
                                   Map<KeanuSavedBayesNet.VertexID, Vertex> instantiatedVertices,
                                   BayesianNetwork bayesNet) {
        for (KeanuSavedBayesNet.StoredValue value : parsedNet.getDefaultStateList()) {
            Vertex targetVertex = getTargetVertex(value, instantiatedVertices, bayesNet);

            savedValues.put(targetVertex, value.getValue());
            targetVertex.loadValue(this);
        }
    }

    private Vertex getTargetVertex(KeanuSavedBayesNet.StoredValue storedValue,
                                   Map<KeanuSavedBayesNet.VertexID, Vertex> instantiatedVertices,
                                   BayesianNetwork bayesNet) {
        Vertex idTarget = getTargetByID(storedValue, instantiatedVertices);
        Vertex labelTarget = getTargetByLabel(storedValue, instantiatedVertices, bayesNet);

        return checkTargetsAreValid(idTarget, labelTarget, storedValue);
    }

    private Vertex getTargetByID(KeanuSavedBayesNet.StoredValue storedValue,
                                 Map<KeanuSavedBayesNet.VertexID, Vertex> instantiatedVertices) {
        if (storedValue.hasId()) {
            return instantiatedVertices.get(storedValue.getId());
        } else {
            return null;
        }
    }

    private Vertex getTargetByLabel(KeanuSavedBayesNet.StoredValue storedValue,
                                    Map<KeanuSavedBayesNet.VertexID, Vertex> instantiatedVertices,
                                    BayesianNetwork bayesNet) {
        if (!storedValue.getVertexLabel().isEmpty()) {
            return bayesNet.getVertexByLabel(new VertexLabel(storedValue.getVertexLabel()));
        } else {
            return null;
        }
    }

    private Vertex checkTargetsAreValid(Vertex idTarget, Vertex labelTarget, KeanuSavedBayesNet.StoredValue storedValue) {
        Vertex targetVertex;

        if (idTarget != null && labelTarget != null) {
            if (idTarget != labelTarget) {
                throw new IllegalArgumentException("Label and VertexID don't refer to same Vertex: ("
                        + storedValue.getVertexLabel() + ") ("
                        + storedValue.getId().toString() + ")");
            } else {
                targetVertex = idTarget;
            }
        } else if (idTarget == null && labelTarget == null) {
            throw new IllegalArgumentException("Value specified for unknown Vertex: ("
                + storedValue.getVertexLabel() + ") ("
                + storedValue.getId() + ")");
        } else {
            targetVertex = (idTarget != null) ? idTarget : labelTarget;
        }

        return targetVertex;
    }

    @Override
    public void loadValue(BoolVertex vertex) {
        KeanuSavedBayesNet.VertexValue value = savedValues.get(vertex);
        BooleanTensor tensor = extractBoolValue(value);
        vertex.setValue(tensor);
    }

    private BooleanTensor extractBoolValue(KeanuSavedBayesNet.VertexValue value) {
        if (value.getValueTypeCase() != KeanuSavedBayesNet.VertexValue.ValueTypeCase.BOOLVAL) {
            throw new IllegalArgumentException("Non Boolean Value specified for Boolean Vertex");
        } else {
            return extractBoolTensor(value.getBoolVal());
        }
    }

    @Override
    public void loadValue(IntegerVertex vertex) {
        KeanuSavedBayesNet.VertexValue value = savedValues.get(vertex);
        IntegerTensor tensor = extractIntValue(value);
        vertex.setValue(tensor);
    }

    private IntegerTensor extractIntValue(KeanuSavedBayesNet.VertexValue value) {
        if (value.getValueTypeCase() != KeanuSavedBayesNet.VertexValue.ValueTypeCase.INTVAL) {
            throw new IllegalArgumentException("Non Int Value specified for Int Vertex");
        } else {
            return extractIntTensor(value.getIntVal());
        }
    }

    private <T> Vertex<T> createVertexFromProtoBuf(KeanuSavedBayesNet.Vertex vertex,
                                                   Map<KeanuSavedBayesNet.VertexID, Vertex> existingVertices) {
        Class vertexClass;
        try {
            vertexClass = Class.forName(vertex.getVertexType());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unknown Vertex Type Specified: " + vertex.getVertexType(), e);
        }

        Map<String, Object> parameterMap = getParamMap(vertex, existingVertices);
        Vertex newVertex = instantiateVertex(vertexClass, parameterMap);

        if (!vertex.getLabel().isEmpty()) {
            newVertex.setLabel(vertex.getLabel());
        }

        return newVertex;
    }

    private Vertex instantiateVertex(Class vertexClass,
                                     Map<String, Object> paramMap) {
        Constructor<Vertex> loadConstructor = getAnnotatedConstructor(vertexClass);
        Parameter[] constructorParameters = loadConstructor.getParameters();
        Object[] arguments = new Object[constructorParameters.length];

        for (int i = 0; i < constructorParameters.length; i++) {
            LoadVertexParam paramAnnotation = constructorParameters[i].getAnnotation(LoadVertexParam.class);

            if (paramAnnotation != null) {
                Object parameter = paramMap.get(paramAnnotation.value());
                if (parameter == null) {
                    throw new IllegalArgumentException("Failed to create vertex due to missing parent: "
                        + paramAnnotation.value());
                }
                arguments[i] = parameter;
            } else {
                throw new IllegalArgumentException("Cannot create Vertex due to unannotated parameter in constructor");
            }

            Class argumentClass = arguments[i].getClass();
            Class parameterClass = constructorParameters[i].getType();

            if (!parameterClass.isAssignableFrom(argumentClass)) {
                throw new IllegalArgumentException("Incorrect Parameter Type specified.  Got: "
                    + arguments[i].getClass() + ", Expected: " + constructorParameters[i].getType());
            }
        }

        try {
            return loadConstructor.newInstance(arguments);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create new Vertex", e);
        }
    }

    private Constructor getAnnotatedConstructor(Class vertexClass) {
        Constructor[] constructors = vertexClass.getConstructors();

        for (Constructor constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();

            if (parameters.length > 0 && parameters[0].isAnnotationPresent(LoadVertexParam.class)) {
                return constructor;
            }
        }

        throw new IllegalArgumentException("No Annotated Load Constructor for Vertex of type: " + vertexClass);
    }

    private Map<String, Object> getParamMap(KeanuSavedBayesNet.Vertex vertex,
                                            Map<KeanuSavedBayesNet.VertexID, Vertex> existingVertices) {
        Map<String, Object> parentsMap = new HashMap<>();

        for (KeanuSavedBayesNet.NamedParam parameter : vertex.getParametersList()) {
            parentsMap.put(parameter.getName(), getDecodedParam(parameter, existingVertices));
        }

        return parentsMap;
    }

    private Object getDecodedParam(KeanuSavedBayesNet.NamedParam parameter,
                                   Map<KeanuSavedBayesNet.VertexID, Vertex> existingVertices) {
        switch (parameter.getParamCase()) {
            case PARENTVERTEX:
                return existingVertices.get(parameter.getParentVertex());

            case DOUBLETENSORPARAM:
                return extractDoubleTensor(parameter.getDoubleTensorParam());

            case INTTENSORPARAM:
                return extractIntTensor(parameter.getIntTensorParam());

            case BOOLTENSORPARAM:
                return extractBoolTensor(parameter.getBoolTensorParam());

            case DOUBLEPARAM:
                return parameter.getDoubleParam();

            case INTPARAM:
                return parameter.getIntParam();

            case LONGPARAM:
                return parameter.getLongParam();

            case STRINGPARAM:
                return parameter.getStringParam();

            case LONGARRAYPARAM:
                return Longs.toArray(parameter.getLongArrayParam().getValuesList());

            case VERTEXARRAYPARAM:
                return extractVertexArray(parameter, existingVertices);

            default:
                throw new IllegalArgumentException("Unknown Param Type Received: "
                    + parameter.getParamCase().toString());
        }
    }

    private Vertex[] extractVertexArray(KeanuSavedBayesNet.NamedParam param,
                                        Map<KeanuSavedBayesNet.VertexID, Vertex> existingVertices) {
        Vertex[] newVertexArray = new Vertex[param.getVertexArrayParam().getValuesCount()];

        for (int i = 0; i < newVertexArray.length; i++) {
            KeanuSavedBayesNet.VertexID parentId = param.getVertexArrayParam().getValues(i);
            Vertex parentVertex = existingVertices.get(parentId);

            if (parentVertex == null) {
                throw new IllegalArgumentException("Saved Structure references unknown Parent: "
                    + parentId.toString());
            }

            newVertexArray[i] = parentVertex;
        }

        return newVertexArray;
    }

    private DoubleTensor extractDoubleTensor(KeanuSavedBayesNet.DoubleTensor tensor) {
        return DoubleTensor.create(
            Doubles.toArray(tensor.getValuesList()),
            Longs.toArray(tensor.getShapeList()));
    }

    private IntegerTensor extractIntTensor(KeanuSavedBayesNet.IntegerTensor tensor) {
        return IntegerTensor.create(
            Ints.toArray(tensor.getValuesList()),
            Longs.toArray(tensor.getShapeList()));
    }

    private BooleanTensor extractBoolTensor(KeanuSavedBayesNet.BooleanTensor tensor) {
        return BooleanTensor.create(
            Booleans.toArray(tensor.getValuesList()),
            Longs.toArray(tensor.getShapeList()));
    }
}
