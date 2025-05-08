package dk.zealandcs.gilbert.application.user;

import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.UserRegister;
import dk.zealandcs.gilbert.domain.user.UserRole;
import dk.zealandcs.gilbert.infrastruture.user.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final IUserRepository userRepository;

    UserService(IUserRepository userRepository) { this.userRepository = userRepository; }

    @Override
    public List<User> allUsers() {
        return List.of();
    }

    @Override
    public Optional<User> getUser(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> login(String email, String password) {
        return Optional.empty();
    }

    @Override
    public Optional<User> register(UserRegister user) {
        if (!user.verify()) {

        }
        return Optional.empty();
    }

    @Override
    public boolean deleteUser(User executingUser, User targetUser) {
        return false;
    }

    @Override
    public boolean updateUser(User executingUser, User targetUser) {
        return false;
    }

    @Override
    public boolean updatePassword(User executingUser, User targetUser, String password) {
        return false;
    }

    @Override
    public boolean updateRole(User executingUser, User targetUser, UserRole role) {
        return false;
    }
}
