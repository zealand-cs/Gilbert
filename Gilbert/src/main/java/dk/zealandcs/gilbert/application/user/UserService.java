package dk.zealandcs.gilbert.application.user;

import dk.zealandcs.gilbert.domain.post.Post;
import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.RegisterUser;
import dk.zealandcs.gilbert.domain.user.UserRole;
import dk.zealandcs.gilbert.domain.validators.DisplayNameValidator;
import dk.zealandcs.gilbert.domain.validators.EmailValidator;
import dk.zealandcs.gilbert.domain.validators.PasswordValidator;
import dk.zealandcs.gilbert.exceptions.*;
import dk.zealandcs.gilbert.infrastruture.favorites.IFavoriteRepository;
import dk.zealandcs.gilbert.infrastruture.post.IPostRepository;
import dk.zealandcs.gilbert.infrastruture.storage.IStorageRepository;
import dk.zealandcs.gilbert.infrastruture.user.IUserRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private final String PROFILE_PICTURE_PATH = "users/pfp";
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final IUserRepository userRepository;
    private final IStorageRepository storageRepository;
    private final IPostRepository postRepository;
    private final IFavoriteRepository favoriteRepository;

    UserService(IUserRepository userRepository, IStorageRepository storageRepository, IPostRepository postRepository, IFavoriteRepository favoriteRepository) {
        this.userRepository = userRepository;
        this.storageRepository = storageRepository;
        this.postRepository = postRepository;
        this.favoriteRepository = favoriteRepository;
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
        insertUser.setPassword(user.getPassword());

        return userRepository.write(insertUser);
    }

    @Override
    public boolean deleteUser(User targetUser) {
        userRepository.delete(targetUser.getId());
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        try {
            DisplayNameValidator.isValid(user.getDisplayName());
        } catch (InvalidDisplayNameException e) {
            throw new NotImplementedException();
        }

        userRepository.update(user);
        return true;
    }

    @Override
    public boolean updatePassword(User targetUser, String currentPassword, String newPassword) {
        PasswordValidator.isValid(newPassword);

        if (!targetUser.checkPassword(currentPassword)) {
            return false;
        }

        targetUser.setPassword(newPassword);
        userRepository.update(targetUser);

        return true;
    }

    @Override
    public boolean updateProfilePicture(User targetUser, MultipartFile image) {
        String objectId = UUID.randomUUID().toString();
        var storageIdentifier = PROFILE_PICTURE_PATH + "/" + objectId;

        try {
            var stream = image.getInputStream();
            storageRepository.store(storageIdentifier, stream);

            var oldObjectId = targetUser.getProfilePictureId();

            targetUser.setProfilePictureId(objectId);
            userRepository.update(targetUser);
            oldObjectId.ifPresent(storageRepository::delete);

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String getProfilePictureUrl(String pfpId) {
        var storageIdentifier = PROFILE_PICTURE_PATH + "/" + pfpId;
        return storageRepository.objectUrl(storageIdentifier);
    }

    @Override
    public boolean updateRole(User executingUser, User targetUser, UserRole role) {
        return false;
    }

    @Override
    public void addFavorite(User user, int post) {
        favoriteRepository.insert(user, post);
    }

    @Override
    public void removeFavorite(User user, int post) {
        favoriteRepository.remove(user, post);
    }

    @Override
    public List<Post> getFavorites(User user) {
        return postRepository.getUserFavorites(user);
    }
}
