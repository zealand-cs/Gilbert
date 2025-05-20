package dk.zealandcs.gilbert.domain.tree;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GeneralNode<T> implements TreeNode {
    private final IdAccessorFunction<T> idAccessor;
    private final ParentIdAccessorFunction<T> parentIdAccessor;

    private final T value;

    public GeneralNode(T value, IdAccessorFunction<T> idAccessor, ParentIdAccessorFunction<T> parentIdAccessor) {
        this.value = value;
        this.idAccessor = idAccessor;
        this.parentIdAccessor = parentIdAccessor;
    }

    @Override
    public Integer getId() {
        return idAccessor.getId(value);
    }

    @Override
    public Optional<Integer> getParentId() {
        return parentIdAccessor.getParentId(value);
    }

    public T getValue() {
        return value;
    }
}

