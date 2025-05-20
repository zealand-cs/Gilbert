package dk.zealandcs.gilbert.infrastruture.post;

import dk.zealandcs.gilbert.domain.post.Brand;
import dk.zealandcs.gilbert.domain.post.Post;
import dk.zealandcs.gilbert.domain.post.ProductType;
import dk.zealandcs.gilbert.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {

    Post write(Post post);
    Optional<Post> findById(int id);
    List<Post> findByOwnerId(int ownerId);
    List<Post> findAll();
    void update(Post post);
    void delete(int id);
    List<Brand> getAllBrands();
    List<ProductType> getAllProductTypes();

    List<Post> search(String query, String[] users, String[] category);

    List<Post> getUserFavorites(int userId);
    default List<Post> getUserFavorites(User user) {
        return getUserFavorites(user.getId());
    }
}
