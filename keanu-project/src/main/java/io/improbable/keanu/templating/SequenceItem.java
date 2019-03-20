package io.improbable.keanu.templating;

import com.google.common.collect.ImmutableList;
import io.improbable.keanu.vertices.ProxyVertex;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.VertexDictionary;
import io.improbable.keanu.vertices.VertexLabel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableMap.copyOf;

public class SequenceItem implements VertexDictionary {

    private static final String NAME_PREFIX = "Sequence_Item_";
    private static Pattern NAME_REGEX = Pattern.compile(NAME_PREFIX + "-?[\\d]+$");

    private Map<VertexLabel, Vertex<?>> contents;

    public SequenceItem() {
        this.contents = new HashMap<>();
    }

    public <T extends Vertex<?>> void addAll(T... vertices) {
        addAll(ImmutableList.copyOf(vertices));
    }

    public <T extends Vertex<?>> void addAll(Collection<T> vertices) {
        vertices.forEach(v -> add(v));
    }

    public <T extends Vertex<?>> void addAll(Map<VertexLabel, T> vertices) {
        vertices.entrySet().forEach(v -> add(v.getKey(), v.getValue()));
    }

    public <T extends Vertex<?>> T add(T v) {
        return add(v.getLabel(), v);
    }

    public <T extends Vertex<?>> T add(VertexLabel label, T v) {
        if (label == null) {
            throw new SequenceConstructionException("Vertex " + v + " must contain a label in order to be added to a sequence item");
        }
        String outerNamespace = label.getOuterNamespace().orElse("");
        if (NAME_REGEX.matcher(outerNamespace).matches()) {
            throw new SequenceConstructionException("Vertex " + v + " has already been added to " + outerNamespace);
        }
        label = scoped(label);
        if (contents.containsKey(label)) {
            throw new IllegalArgumentException("Key " + label + " already exists");
        }
        contents.put(label, v);
        v.setLabel(label);
        return v;
    }

    /**
     * @return Returns a map of vertex labels to vertices, which covers all of the vertices that have been explicitly
     * added to this sequence item.
     */
    public Map<VertexLabel, Vertex<?>> getContents() {
        return copyOf(this.contents);
    }

    private String getUniqueName() {
        return NAME_PREFIX + this.hashCode();
    }

    private VertexLabel scoped(VertexLabel label) {
        return label.withExtraNamespace(getUniqueName());
    }

    @Override
    public <V extends Vertex<?>> V get(VertexLabel label) {
        Vertex<?> vertex = contents.getOrDefault(label, contents.get(scoped(label)));

        if (vertex == null) {
            throw new IllegalArgumentException("Cannot find VertexLabel " + label);
        }
        return (V) vertex;
    }

    @Override
    public SequenceItem withExtraEntries(Map<VertexLabel, Vertex<?>> extraEntries) {
        SequenceItem item = new SequenceItem();
        item.addAll(contents);
        item.addAll(extraEntries);
        return item;
    }

    public Collection<Vertex<?>> getProxyVertices() {
        return contents.values().stream()
            .filter(v -> v instanceof ProxyVertex)
            .collect(Collectors.toList());
    }
}
