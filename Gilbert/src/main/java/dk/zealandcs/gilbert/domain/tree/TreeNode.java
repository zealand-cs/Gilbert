package dk.zealandcs.gilbert.domain.tree;

import java.util.Optional;

public interface TreeNode {
    Integer getId();
    Optional<Integer> getParentId();
}
