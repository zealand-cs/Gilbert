package dk.zealandcs.gilbert.infrastruture.post;

import dk.zealandcs.gilbert.config.DatabaseConfig;
import dk.zealandcs.gilbert.domain.post.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository implements IPostRepository {
    private static final Logger logger = LoggerFactory.getLogger(PostRepository.class);
    @Autowired
    private DatabaseConfig databaseConfig;


    /**
     * Writes a new post to the database from given paramaters.
     *
     */
    @Override
    public Post write(Post post) {
        //String sql = "INSERT INTO posts() VALUES ()"
        return null;
    }

    public Optional<Post> findById(int id) {
        return null;
    }

    public Optional<Post> findByOwnerId(int ownerId) {
        return null;
    }

    public List<Post> findAll() {
        return null;
    }

    public void update(Post post) {

    }

    public void delete(int id) {}


}
