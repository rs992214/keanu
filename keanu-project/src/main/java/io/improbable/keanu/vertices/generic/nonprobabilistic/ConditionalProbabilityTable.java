package io.improbable.keanu.vertices.generic.nonprobabilistic;

import com.google.common.collect.ImmutableList;
import io.improbable.keanu.tensor.Tensor;
import io.improbable.keanu.vertices.ConstantVertex;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.bool.BooleanVertex;
import io.improbable.keanu.vertices.bool.nonprobabilistic.BooleanCPTVertex;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.DoubleCPTVertex;
import io.improbable.keanu.vertices.intgr.IntegerVertex;
import io.improbable.keanu.vertices.intgr.nonprobabilistic.IntegerCPTVertex;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionalProbabilityTable {

    private ConditionalProbabilityTable() {
    }

    private static final String WHEN_CONDITION_SIZE_MISMATCH = "The 'when' condition size does not match input count";

    @SafeVarargs
    public static CPTRawBuilder of(Vertex<? extends Tensor<Boolean>>... inputs) {
        return new CPTRawBuilder(Arrays.asList(inputs));
    }

    public static class CPTRawBuilder {

        private final List<Vertex<? extends Tensor<Boolean>>> inputs;

        public CPTRawBuilder(List<Vertex<? extends Tensor<Boolean>>> inputs) {
            this.inputs = inputs;
        }

        public CPTWhenRawBuilder when(Boolean... condition) {
            if (condition.length != inputs.size()) {
                throw new IllegalArgumentException(WHEN_CONDITION_SIZE_MISMATCH);
            }
            return new CPTWhenRawBuilder(ImmutableList.copyOf(condition), inputs);
        }
    }

    public static class CPTWhenRawBuilder {

        private final CPTCondition condition;
        private final List<Vertex<? extends Tensor<Boolean>>> inputs;

        public CPTWhenRawBuilder(List<Boolean> condition, List<Vertex<? extends Tensor<Boolean>>> inputs) {
            this.condition = new CPTCondition(condition);
            this.inputs = inputs;
        }

        public <T> CPTBuilder<T> then(Vertex<Tensor<T>> thn) {
            Map<CPTCondition, Vertex<Tensor<T>>> conditions = new HashMap<>();
            conditions.put(condition, thn);
            return new CPTBuilder<>(inputs, conditions);
        }

        public DoubleCPTBuilder then(DoubleVertex thn) {
            Map<CPTCondition, DoubleVertex> conditions = new HashMap<>();
            conditions.put(condition, thn);
            return new DoubleCPTBuilder(inputs, conditions);
        }

        public DoubleCPTBuilder then(double thn) {
            return then(ConstantVertex.of(thn));
        }

        public IntegerCPTBuilder then(IntegerVertex thn) {
            Map<CPTCondition, IntegerVertex> conditions = new HashMap<>();
            conditions.put(condition, thn);
            return new IntegerCPTBuilder(inputs, conditions);
        }

        public IntegerCPTBuilder then(int thn) {
            return then(ConstantVertex.of(thn));
        }

        public BooleanCPTBuilder then(BooleanVertex thn) {
            Map<CPTCondition, BooleanVertex> conditions = new HashMap<>();
            conditions.put(condition, thn);
            return new BooleanCPTBuilder(inputs, conditions);
        }

        public BooleanCPTBuilder then(boolean thn) {
            return then(ConstantVertex.of(thn));
        }
    }

    public static class DoubleCPTBuilder {
        private final List<Vertex<? extends Tensor<Boolean>>> inputs;
        private final Map<CPTCondition, DoubleVertex> conditions;

        public DoubleCPTBuilder(List<Vertex<? extends Tensor<Boolean>>> inputs, Map<CPTCondition, DoubleVertex> conditions) {
            this.inputs = inputs;
            this.conditions = conditions;
        }

        public DoubleCPTWhenBuilder when(Boolean... condition) {
            if (condition.length != inputs.size()) {
                throw new IllegalArgumentException(WHEN_CONDITION_SIZE_MISMATCH);
            }
            return new DoubleCPTWhenBuilder(new CPTCondition(ImmutableList.copyOf(condition)), this);
        }

        public DoubleCPTVertex orDefault(DoubleVertex defaultResult) {
            return new DoubleCPTVertex(inputs, conditions, defaultResult);
        }

        public DoubleCPTVertex orDefault(double defaultResult) {
            return orDefault(ConstantVertex.of(defaultResult));
        }

        public static class DoubleCPTWhenBuilder {

            private final CPTCondition condition;
            private final DoubleCPTBuilder builder;

            private DoubleCPTWhenBuilder(CPTCondition condition, DoubleCPTBuilder builder) {
                this.condition = condition;
                this.builder = builder;
            }

            public DoubleCPTBuilder then(DoubleVertex thn) {
                builder.conditions.put(condition, thn);
                return builder;
            }

            public DoubleCPTBuilder then(double thn) {
                return then(ConstantVertex.of(thn));
            }
        }
    }

    public static class BooleanCPTBuilder {
        private final List<Vertex<? extends Tensor<Boolean>>> inputs;
        private final Map<CPTCondition, BooleanVertex> conditions;

        public BooleanCPTBuilder(List<Vertex<? extends Tensor<Boolean>>> inputs, Map<CPTCondition, BooleanVertex> conditions) {
            this.inputs = inputs;
            this.conditions = conditions;
        }

        public BooleanCPTWhenBuilder when(Boolean... condition) {
            if (condition.length != inputs.size()) {
                throw new IllegalArgumentException(WHEN_CONDITION_SIZE_MISMATCH);
            }
            return new BooleanCPTWhenBuilder(new CPTCondition(ImmutableList.copyOf(condition)), this);
        }

        public BooleanCPTVertex orDefault(BooleanVertex defaultResult) {
            return new BooleanCPTVertex(inputs, conditions, defaultResult);
        }

        public BooleanCPTVertex orDefault(boolean defaultResult) {
            return orDefault(ConstantVertex.of(defaultResult));
        }

        public static class BooleanCPTWhenBuilder {

            private final CPTCondition condition;
            private final BooleanCPTBuilder builder;

            private BooleanCPTWhenBuilder(CPTCondition condition, BooleanCPTBuilder builder) {
                this.condition = condition;
                this.builder = builder;
            }

            public BooleanCPTBuilder then(BooleanVertex thn) {
                builder.conditions.put(condition, thn);
                return builder;
            }

            public BooleanCPTBuilder then(boolean thn) {
                return then(ConstantVertex.of(thn));
            }
        }
    }

    public static class IntegerCPTBuilder {
        private final List<Vertex<? extends Tensor<Boolean>>> inputs;
        private final Map<CPTCondition, IntegerVertex> conditions;

        public IntegerCPTBuilder(List<Vertex<? extends Tensor<Boolean>>> inputs, Map<CPTCondition, IntegerVertex> conditions) {
            this.inputs = inputs;
            this.conditions = conditions;
        }

        public IntegerCPTWhenBuilder when(Boolean... condition) {
            if (condition.length != inputs.size()) {
                throw new IllegalArgumentException(WHEN_CONDITION_SIZE_MISMATCH);
            }
            return new IntegerCPTWhenBuilder(new CPTCondition(ImmutableList.copyOf(condition)), this);
        }

        public IntegerCPTVertex orDefault(IntegerVertex defaultResult) {
            return new IntegerCPTVertex(inputs, conditions, defaultResult);
        }

        public IntegerCPTVertex orDefault(int defaultResult) {
            return orDefault(ConstantVertex.of(defaultResult));
        }

        public static class IntegerCPTWhenBuilder {

            private final CPTCondition condition;
            private final IntegerCPTBuilder builder;

            private IntegerCPTWhenBuilder(CPTCondition condition, IntegerCPTBuilder builder) {
                this.condition = condition;
                this.builder = builder;
            }

            public IntegerCPTBuilder then(IntegerVertex thn) {
                builder.conditions.put(condition, thn);
                return builder;
            }

            public IntegerCPTBuilder then(int thn) {
                return then(ConstantVertex.of(thn));
            }
        }
    }

    public static class CPTBuilder<T> {
        private final List<Vertex<? extends Tensor<Boolean>>> inputs;
        private final Map<CPTCondition, Vertex<Tensor<T>>> conditions;

        public CPTBuilder(List<Vertex<? extends Tensor<Boolean>>> inputs, Map<CPTCondition, Vertex<Tensor<T>>> conditions) {
            this.inputs = inputs;
            this.conditions = conditions;
        }

        public CPTWhenBuilder<T> when(Boolean... condition) {
            if (condition.length != inputs.size()) {
                throw new IllegalArgumentException(WHEN_CONDITION_SIZE_MISMATCH);
            }
            return new CPTWhenBuilder<>(new CPTCondition(ImmutableList.copyOf(condition)), this);
        }

        public CPTVertex<T> orDefault(Vertex<Tensor<T>> defaultResult) {
            return new CPTVertex<>(inputs, conditions, defaultResult);
        }

        public static class CPTWhenBuilder<T> {

            private final CPTCondition condition;
            private final CPTBuilder<T> builder;

            private CPTWhenBuilder(CPTCondition condition, CPTBuilder<T> builder) {
                this.condition = condition;
                this.builder = builder;
            }

            public CPTBuilder<T> then(Vertex<Tensor<T>> thn) {
                builder.conditions.put(condition, thn);
                return builder;
            }
        }
    }
}
