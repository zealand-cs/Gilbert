package dk.zealandcs.gilbert.application.user;

import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.RegisterUser;
import dk.zealandcs.gilbert.exceptions.EmailInUseException;
import dk.zealandcs.gilbert.infrastruture.favorites.IFavoriteRepository;
import dk.zealandcs.gilbert.infrastruture.post.IPostRepository;
import dk.zealandcs.gilbert.infrastruture.storage.IStorageRepository;
import dk.zealandcs.gilbert.infrastruture.user.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class UserServiceTest {
    IUserRepository mockRepo;
    IStorageRepository mockStorRepo;
    IPostRepository mockPostRepo;
    IFavoriteRepository mockFavoriteRepo;

    UserService userService;

    @BeforeEach
    public void beforeEach() {
        mockRepo = Mockito.mock(IUserRepository.class);
        userService = new UserService(mockRepo, mockStorRepo, mockPostRepo, mockFavoriteRepo);
    }

    @Test
    public void register_validUser() {
        var registerUser = new RegisterUser("John", "john@example.com", "pAsSwOrD123", true);

        var expectedUser = new User(registerUser);
        Mockito.when(mockRepo.write(any())).thenReturn(expectedUser);

        var result = assertDoesNotThrow(() -> userService.register(registerUser));
        assertEquals(registerUser.getDisplayName(), result.getDisplayName());
        assertEquals(registerUser.getEmail(), result.getEmail());
    }


    @Test
    public void register_throwsEmailAlreadyInUser() {
        var registerUser = new RegisterUser("John", "john@example.com", "pAsSwOrD123", true);
        Mockito.when(mockRepo.findByEmail(anyString())).thenReturn(Optional.of(Mockito.mock(User.class)));

        assertThrows(EmailInUseException.class, () -> userService.register(registerUser));
    }
}