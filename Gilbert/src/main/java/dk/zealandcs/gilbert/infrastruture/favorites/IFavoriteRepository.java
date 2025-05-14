package dk.zealandcs.gilbert.infrastruture.favorites;

import dk.zealandcs.gilbert.domain.post.Post;
import dk.zealandcs.gilbert.domain.user.User;

import java.util.List;

public interface IFavoriteRepository {
    void insert(int userId, int postId);
    default void insert(User user, Post post) {
        insert(user.getId(), post.getId());
    }
    default void insert(User user, int postId) {
        insert(user.getId(), postId);
    }
    default void insert(int userId, Post post) {
        insert(userId, post.getId());
    }

    void remove(int userId, int postId);
    default void remove(User user, Post post) {
        remove(user.getId(), post.getId());
    }
    default void remove(User user, int postId) {
        remove(user.getId(), postId);
    }
    default void remove(int userId, Post post) {
        remove(userId, post.getId());
    }

    List<Post> getUserFavorites(int userId);
    default List<Post> getUserFavorites(User user) {
        return getUserFavorites(user.getId());
    }

    int getNumberOfFavoritesByPost(int postId);
    default int getNumberOfFavoritesByPost(Post post) {
        return getNumberOfFavoritesByPost(post.getId());
    }
}
