package dk.zealandcs.gilbert.infrastruture.user;

import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    User write(User user);
    Optional<User> findByField(String field, String value) throws UserNotFoundException;
    List<User> findAll();
    void update(User user) throws UserNotFoundException;
    void delete(int id) throws UserNotFoundException;

    default Optional<User> findById(int id) {
        return findByField("id", String.valueOf(id));
    }
    default Optional<User> findByEmail(String email) {
        return findByField("email", email);
    }
    default Optional<User> findByUsername(String username) {
        return findByField("username", username);
    }
}
