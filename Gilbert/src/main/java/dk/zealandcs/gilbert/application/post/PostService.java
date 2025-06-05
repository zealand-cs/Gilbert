package dk.zealandcs.gilbert.application.post;


import dk.zealandcs.gilbert.domain.post.Brand;
import dk.zealandcs.gilbert.domain.post.Post;
import dk.zealandcs.gilbert.domain.post.PostStatus;
import dk.zealandcs.gilbert.domain.post.ProductType;
import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.UserRole;
import dk.zealandcs.gilbert.infrastruture.post.IPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        logger.info("Edit post {}", post);
        if (executingUser.getId() == post.getOwnerId() || executingUser.getRole().isAtLeast(UserRole.EMPLOYEE)) {
            postRepository.update(post);
            logger.info("Edited post {}", post);
            return true;
        }
        logger.warn("Edit post {} is not an employee", executingUser.getUsername());
        return false;
    }

    @Override
    public boolean deletePost(User executingUser, Post post) {
        logger.info("Delete post {}", post);
        if (executingUser.getId() == post.getOwnerId() || executingUser.getRole().isAtLeast(UserRole.EMPLOYEE)) {
            postRepository.delete(post.getId());
            logger.info("Deleted post {}", post);
            return true;
        }
        logger.warn("User {} is not allowed to delete post {}", executingUser.getUsername(), post.getName());
        return false;
    }

    @Override
    public List<Brand> getAllBrands() {
        return postRepository.getAllBrands();
    }

    @Override
    public List<ProductType> getAllProductTypes() {
        return postRepository.getAllProductTypes();
    }

    @Override
    public Optional<Post> findById(int id) {
        logger.debug("Finding post with id: {}", id);
        var post = postRepository.findById(id);
        if (post.isEmpty()) {
            logger.warn("No post found with id: {}", id);
        } else {
            logger.debug("Found post: {}", post.get().getName());
        }
        return post;
    }

    @Override
    public List<Post> search(String query) {
        var searchKeywords = query.split(" ");
        List<String> users = new ArrayList<>();
        List<String> keywords = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        for (var keyword : searchKeywords) {
            if (keyword.startsWith("@")) {
                users.add(keyword.substring(1));
            } else if (keyword.startsWith("$")) {
                categories.add(keyword.substring(1));
            } else {
                keywords.add(keyword);
            }
        }

        return postRepository.search(String.join(" ", keywords), users.toArray(new String[0]), categories.toArray(new String[0]));
    }

    @Override
    public List<Post> getPendingPosts() {
        logger.info("Fetching pending approval posts");
        return postRepository.findAllByStatus(PostStatus.PendingApproval);
    }

    @Override
    public boolean approvePost(int postId, User executingUser) {
        if (!executingUser.getRole().isAtLeast(UserRole.EMPLOYEE)) {
            logger.warn("User {} attempted to approve post {} without proper permissions",
                    executingUser.getUsername(), postId);
            return false;
        }

        logger.info("User {} attempting to approve post {}", executingUser.getUsername(), postId);

        try {
            Optional<Post> postOptional = postRepository.findById(postId);
            if (postOptional.isEmpty()) {
                logger.warn("Post {} not found for approval", postId);
                return false;
            }

            Post post = postOptional.get();
            if (post.getStatus() != PostStatus.PendingApproval) {
                logger.warn("Post {} is not in pending status", postId);
                return false;
            }

            post.setStatus(PostStatus.Available);
            postRepository.update(post);

            logger.info("Post {} approved successfully by {}", postId, executingUser.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("Error approving post {}: {}", postId, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean declinePost(int postId, User executingUser) {
        if (!executingUser.getRole().isAtLeast(UserRole.EMPLOYEE)) {
            logger.warn("User {} attempted to decline post {} without proper permissions",
                    executingUser.getUsername(), postId);
            return false;
        }

        logger.info("User {} attempting to decline post {}", executingUser.getUsername(), postId);

        try {
            Optional<Post> postOptional = postRepository.findById(postId);
            if (postOptional.isEmpty()) {
                logger.warn("Post {} not found for declining", postId);
                return false;
            }

            Post post = postOptional.get();
            if (post.getStatus() != PostStatus.PendingApproval) {
                logger.warn("Post {} is not in pending status", postId);
                return false;
            }

            post.setStatus(PostStatus.Declined);
            postRepository.update(post);

            logger.info("Post {} declined successfully by {}", postId, executingUser.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("Error declining post {}: {}", postId, e.getMessage());
            return false;
        }
    }

}
