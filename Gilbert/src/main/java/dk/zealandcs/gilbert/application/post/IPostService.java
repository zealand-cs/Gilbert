package dk.zealandcs.gilbert.application.post;

import com.stripe.model.Product;
import dk.zealandcs.gilbert.domain.post.Brand;
import dk.zealandcs.gilbert.domain.post.ProductType;
import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.post.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService{

    List<Post> allPosts();
    Optional<Post> getPost(int id);
    List<Post> getPostsByOwner(int ownerId);
    Optional<Post> createPost(Post post);
    boolean editPost(User executingUser, Post post);
    boolean deletePost(User executingUser, Post post);
    List<Brand> getAllBrands();
    List<ProductType> getAllProductTypes();
    Optional<Post> findById(int id);

    List<Post> search(String query);
    List<Post> getPendingPosts();
    boolean approvePost(int postId, User executingUser);
    boolean declinePost(int postId, User executingUser);
}
