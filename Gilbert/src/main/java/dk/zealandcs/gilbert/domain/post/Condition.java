package dk.zealandcs.gilbert.domain.post;

public enum Condition {

    New     (0),
    AsNew   (1),
    Used    (2),
    Worn    (3),
    Damaged (4);

    private final int condition;

    Condition(int condition) {
        this.condition = condition;
    }

    public boolean isAtLeast(Condition condition) {return this.condition <= condition.condition;
    }
}
