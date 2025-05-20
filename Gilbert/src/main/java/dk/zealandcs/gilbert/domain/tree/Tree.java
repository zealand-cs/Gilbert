package dk.zealandcs.gilbert.domain.tree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<T extends TreeNode> {
    private final SequencedMap<Integer, T> nodeMap;
    private final SequencedMap<Integer, SequencedSet<Integer>> children;
    private final SequencedSet<Integer> roots;

    Tree() {
        this(new LinkedHashMap<>(), new LinkedHashMap<>(), new LinkedHashSet<>());
    }

    public<V> Tree(List<V> values, IdAccessorFunction<V> idAccessor, ParentIdAccessorFunction<V> parentIdAccessor) {
        this(values.stream().map((v) -> (T) new GeneralNode(v, idAccessor, parentIdAccessor)).toList());
    }

    public Tree(List<T> nodes) {
        this();

        for (T node : nodes) {
            nodeMap.put(node.getId(), node);

            var parent = node.getParentId();
            if (parent.isEmpty()) {
                roots.add(node.getId());
                continue;
            }

            if (!children.containsKey(parent.get())) {
                children.put(parent.get(), new LinkedHashSet<>());
            }

            children.get(parent.get()).add(node.getId());
        }
    }

    private Tree(SequencedMap<Integer, T> nodeMap, SequencedMap<Integer, SequencedSet<Integer>> children, SequencedSet<Integer> roots) {
        this.nodeMap = nodeMap;
        this.children = children;
        this.roots = roots;
    }

    private Set<Integer> getChildren(Integer id) {
        return children.get(id);
    }

    private Set<Integer> getChildren(T node) {
        return getChildren(node.getId());
    }

    public Optional<T> getNode(Integer id) {
        return Optional.ofNullable(nodeMap.get(id));
    }

    public List<T> getRoots() {
        // Instead of get() we use orElse(null), since when mapping, we know the nodes should exist
        // This is to prevent a warning
        return roots.stream().map((v) -> getNode(v).orElse(null)).toList();
    }

    public List<T> getChildrenNodes(Integer id) {
        // Instead of get() we use orElse(null), since when mapping, we know the nodes should exist
        // This is to prevent a warning
        var node = Optional.ofNullable(children.get(id));
        return node.map(integers -> integers.stream().map((v) -> getNode(v).orElse(null)).toList()).orElseGet(ArrayList::new);
    }

    public List<T> getChildrenNodes(T node) {
        return getChildrenNodes(node.getId());
    }
}
