package dk.zealandcs.gilbert.application.user;

import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.RegisterUser;
import dk.zealandcs.gilbert.domain.user.UserRole;
import dk.zealandcs.gilbert.domain.validators.DisplayNameValidator;
import dk.zealandcs.gilbert.domain.validators.EmailValidator;
import dk.zealandcs.gilbert.domain.validators.PasswordValidator;
import dk.zealandcs.gilbert.exceptions.*;
import dk.zealandcs.gilbert.infrastruture.user.IUserRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final IUserRepository userRepository;

    UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User login(String email, String password) throws EmailNotFoundException, InvalidPasswordException {
        var user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new EmailNotFoundException();
        }

        if (!user.get().checkPassword(password)) {
            throw new InvalidPasswordException();
        }

        return user.get();
    }

    @Override
    public User register(RegisterUser user) throws InvalidDisplayNameException, InvalidPasswordFormatException, EmailInUseException {
        try {
            DisplayNameValidator.isValid(user.getDisplayName());
        } catch (InvalidDisplayNameException e) {
            throw new NotImplementedException();
        }
        try {
            EmailValidator.isValid(user.getEmail());
        } catch (InvalidEmailFormatException e) {
            throw new NotImplementedException();
        }
        try {
            PasswordValidator.isValid(user.getPassword());
        } catch (InvalidPasswordFormatException e) {
            throw new NotImplementedException();
        }

        if (!user.isAcceptedTerms()) {
            throw new NotImplementedException();
        }

        var emailCheckUser = userRepository.findByEmail(user.getEmail());
        if (emailCheckUser.isPresent()) {
            throw new EmailInUseException("Email already in use");
        }

        var insertUser = new User(user);
        insertUser.hashPassword();

        return userRepository.write(insertUser);
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
