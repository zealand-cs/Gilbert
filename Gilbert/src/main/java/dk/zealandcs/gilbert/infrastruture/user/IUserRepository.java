package dk.zealandcs.gilbert.infrastruture.user;

import dk.zealandcs.gilbert.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    User write(User user);
    Optional<User> findByField(String field, String value);
    List<User> findAll();
    void update(User user);
    void delete(int id);

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
