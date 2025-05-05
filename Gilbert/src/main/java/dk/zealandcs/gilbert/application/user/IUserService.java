package dk.zealandcs.gilbert.application.user;

import dk.zealandcs.gilbert.domain.User;
import dk.zealandcs.gilbert.domain.UserRole;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> allUsers();
    Optional<User> getUser(int id);
    Optional<User> login(String email, String password);
    Optional<User> register(User user);
    boolean deleteUser(User executingUser, User targetUser);

    boolean updateUser(User executingUser, User targetUser);
    boolean updatePassword(User executingUser, User targetUser, String password);
    boolean updateRole(User executingUser, User targetUser, UserRole role);
}
