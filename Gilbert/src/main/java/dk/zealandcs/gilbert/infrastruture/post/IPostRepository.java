package dk.zealandcs.gilbert.infrastruture.post;

import dk.zealandcs.gilbert.domain.post.Post;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {

    Post write(Post post);
    Optional<Post> findById(int id);
    Optional<Post> findByOwnerId(int ownerId);
    List<Post> findAll();
    void update(Post post);
    void delete(int id);

}
