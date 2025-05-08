package dk.zealandcs.gilbert.application.post;

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
}
