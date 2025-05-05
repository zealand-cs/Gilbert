package dk.zealandcs.gilbert.infrastruture.user;

import dk.zealandcs.gilbert.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    User write(User user);
    Optional<User> findById(int id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void update(User user);
    void delete(int id);
}
