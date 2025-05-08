package dk.zealandcs.gilbert.application.post;


import dk.zealandcs.gilbert.domain.post.Post;
import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.infrastruture.post.IPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final IPostRepository postRepository;

    PostService(IPostRepository repo) { this.postRepository = repo; }


    @Override
    public List<Post> allPosts() {
        logger.info("Get all posts");
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPost(int id) {
        logger.info("Get post with id {}", id);
        return postRepository.findById(id);
    }

    @Override
    public List<Post> getPostsByOwner(int ownerId) {
        logger.info("Get posts by owner {}", ownerId);
        return postRepository.findByOwnerId(ownerId);
    }

    @Override
    public Optional<Post> createPost(Post post) {
        logger.info("Create post {}", post);
        Post result = postRepository.write(post);
        if (result != null) {
            logger.info("Saved post {}", result);
        } else {
            logger.info("Saved post null");
        }
        return Optional.ofNullable(result);
    }

    @Override
    public boolean editPost(User executingUser, Post post) {
        return false;
    }

    @Override
    public boolean deletePost(User executingUser, Post post) {
        return false;
    }
}
