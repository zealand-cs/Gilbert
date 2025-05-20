package dk.zealandcs.gilbert.domain.tree;

import java.util.Optional;

public interface ParentIdAccessorFunction<T> {
    Optional<Integer> getParentId(T v);
}
