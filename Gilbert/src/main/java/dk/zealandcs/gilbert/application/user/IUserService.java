package dk.zealandcs.gilbert.application.user;

import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.UserRegister;
import dk.zealandcs.gilbert.domain.user.UserRole;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> allUsers();
    Optional<User> getUser(int id);
    Optional<User> getUserByEmail(String email);
    Optional<User> login(String email, String password);
    Optional<User> register(UserRegister user);
    boolean deleteUser(User executingUser, User targetUser);

    boolean updateUser(User executingUser, User targetUser);
    boolean updatePassword(User executingUser, User targetUser, String password);
    boolean updateRole(User executingUser, User targetUser, UserRole role);
}
