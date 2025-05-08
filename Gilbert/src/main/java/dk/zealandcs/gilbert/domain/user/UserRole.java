package dk.zealandcs.gilbert.domain.user;

public enum UserRole {
    User(0),
    Seller(10),
    Employee(50),
    Admin(100);

    private final int rank;

    UserRole(int rank) { this.rank = rank; }

    public boolean isAtLeast(UserRole role) { return this.rank >= role.rank; }
}
