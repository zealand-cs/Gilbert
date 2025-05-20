package dk.zealandcs.gilbert.domain.user;

public enum UserRole {
    USER(0),
    SELLER(10),
    EMPLOYEE(50),
    ADMIN(100);

    private final int rank;

    UserRole(int rank) { this.rank = rank; }

    public boolean isAtLeast(UserRole role) { return this.rank >= role.rank; }
}
