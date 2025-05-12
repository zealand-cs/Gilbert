package dk.zealandcs.gilbert.application.user;

import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.RegisterUser;
import dk.zealandcs.gilbert.domain.user.UserRole;
import dk.zealandcs.gilbert.exceptions.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> allUsers();
    Optional<User> getUser(int id);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);
    User login(String email, String password) throws EmailNotFoundException, InvalidPasswordException;
    User register(RegisterUser user) throws InvalidDisplayNameException, InvalidPasswordFormatException, EmailInUseException, UserWriteException;
    boolean deleteUser(User targetUser);

    boolean updateProfilePicture(User targetUser, MultipartFile image);
    String getProfilePictureUrl(String pfpId);

    boolean updateUser(User user);
    boolean updatePassword(User targetUser, String currentPassword, String newPassword);
    boolean updateRole(User executingUser, User targetUser, UserRole role);
}
