package dk.zealandcs.gilbert.application.post;

import dk.zealandcs.gilbert.domain.post.Brand;
import dk.zealandcs.gilbert.domain.post.Post;
import dk.zealandcs.gilbert.domain.post.ProductType;
import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.UserRole;
import dk.zealandcs.gilbert.infrastruture.post.IPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private IPostRepository postRepository;

    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = new PostService(postRepository);
    }


    @Test
    void allPosts() {
        List<Post> expectedPosts = Arrays.asList(
                new Post(),
                new Post()
        );
        when(postRepository.findAll()).thenReturn(expectedPosts);

        List<Post> actualPosts = postService.allPosts();

        assertEquals(expectedPosts, actualPosts);
        verify(postRepository).findAll();


    }

    @Test
    void getPost() {
        int postId = 1;
        Post expectedPost = new Post();
        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(expectedPost));

        Optional<Post> actualPost = postService.getPost(postId);

        assertTrue(actualPost.isPresent());
        assertEquals(expectedPost, actualPost.get());
        verify(postRepository).findById(postId);
    }

    @Test
    void getPostsByOwner() {
        int ownerId = 1;
        List<Post> expectedPosts = Arrays.asList(new Post(), new Post());
        when(postRepository.findByOwnerId(ownerId)).thenReturn(expectedPosts);

        List<Post> actualPosts = postService.getPostsByOwner(ownerId);

        assertEquals(expectedPosts, actualPosts);
        verify(postRepository).findByOwnerId(ownerId);
    }

    @Test
    void createPost() {
        Post post = new Post();
        Post expectedPost = new Post();
        when(postRepository.write(post)).thenReturn(expectedPost);

        Optional<Post> actualPost = postService.createPost(post);

        assertTrue(actualPost.isPresent());
        assertEquals(expectedPost, actualPost.get());
        verify(postRepository).write(post);
    }

    @Test
    void editPost_asOwner_Success() {
        User owner = new User();
        owner.setId(1);
        Post post = new Post();
        post.setOwnerId(owner.getId());

        boolean result = postService.editPost(owner, post);

        assertTrue(result);
        verify(postRepository).update(post);
    }

    @Test
    void editPost_AsEmployee_Success() {
        User employee = new User();
        employee.setId(2);
        employee.setRole(UserRole.EMPLOYEE);
        Post post = new Post();
        post.setOwnerId(employee.getId());

        boolean result = postService.editPost(employee, post);

        assertTrue(result);
        verify(postRepository).update(post);
    }

    @Test
    void editPost_AsUnauthorizedUser_Failure() {
        User unauthorizedUser = new User();
        unauthorizedUser.setId(2);
        unauthorizedUser.setRole(UserRole.USER);
        Post post = new Post();
        post.setOwnerId(1);

        boolean result = postService.editPost(unauthorizedUser, post);

        assertFalse(result);
        verify(postRepository, never()).update(post);
    }

    @Test
    void deletePost_AsOwner_Success() {
        User owner = new User();
        owner.setId(1);
        Post post = new Post();
        post.setId(1);
        post.setOwnerId(1);

        boolean result = postService.deletePost(owner, post);

        assertTrue(result);
        verify(postRepository).delete(post.getId());

    }


    @Test
    void deletePost_asUnauthorizedUser_Failure() {
        User unauthorizedUser = new User();
        unauthorizedUser.setId(2);
        unauthorizedUser.setRole(UserRole.USER);
        Post post = new Post();
        post.setId(1);
        post.setOwnerId(1);

        boolean result = postService.deletePost(unauthorizedUser, post);

        assertFalse(result);
        verify(postRepository, never()).delete(post.getId());

    }

    @Test
    void getAllBrands() {
        List<Brand> expectedBrands = Arrays.asList(new Brand(), new Brand());
        when(postRepository.getAllBrands()).thenReturn(expectedBrands);

        List<Brand> actualBrands = postService.getAllBrands();

        assertEquals(expectedBrands, actualBrands);
        verify(postRepository).getAllBrands();
    }

    @Test
    void getAllProductTypes() {
        List<ProductType> expectedProductTypes = Arrays.asList(new ProductType(), new ProductType());
        when(postRepository.getAllProductTypes()).thenReturn(expectedProductTypes);

        List<ProductType> actualProductTypes = postService.getAllProductTypes();

        assertEquals(expectedProductTypes, actualProductTypes);
        verify(postRepository).getAllProductTypes();
    }

    @Test
    void findById_ExistingPost() {
        int postId = 1;
        Post expectedPost = new Post();
        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(expectedPost));

        Optional<Post> actualPost = postService.findById(postId);

        assertTrue(actualPost.isPresent());
        assertEquals(expectedPost, actualPost.get());
        verify(postRepository).findById(postId);
    }

    @Test
    void findById_NonExistingPost() {
        int postId = 1;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Optional<Post> result = postService.findById(postId);

        assertTrue(result.isEmpty());
        verify(postRepository).findById(postId);

    }
    @Test
    void search() {

        String query = "@John $Brand $ProductType";
        List<Post> expectedPosts = Arrays.asList(new Post(), new Post());
        when(postRepository.search("", new String[]{"John"}, new String[]{"Brand", "ProductType"}))
                .thenReturn(expectedPosts);

        List<Post> actualPosts = postService.search(query);

        assertEquals(expectedPosts, actualPosts);
        verify(postRepository).search("", new String[]{"John"}, new String[]{"Brand", "ProductType"});
    }

}