package dk.zealandcs.gilbert.infrastruture.post;

import dk.zealandcs.gilbert.config.DatabaseConfig;
import dk.zealandcs.gilbert.domain.post.Condition;
import dk.zealandcs.gilbert.domain.post.Post;
import dk.zealandcs.gilbert.domain.post.PostStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
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
        String sql = "INSERT INTO posts(ownerId, name, description, price, condition, size, location, status, " +
                     "image_id, brands_id, product_type_id) VALUES (?. ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            logger.info("Executing SQL for post: " + sql);
            stmt.setInt(1, post.getOwnerId());
            stmt.setString(2, post.getName());
            stmt.setString(3, post.getDescription());
            stmt.setDouble(4, post.getPrice());
            stmt.setString(5, post.getCondition().name());
            stmt.setString(6, post.getSize());
            stmt.setString(7, post.getLocation());
            stmt.setString(8, post.getStatus().name());
            stmt.setString(9, post.getImageId());
            stmt.setInt(10, Integer.parseInt(post.getBrand()));
            stmt.setInt(11, Integer.parseInt(post.getTypeOfClothing()));


            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    post.setId(keys.getInt(1));
                    logger.info("Insert post Id: " + post.getId());
                    return post;
                }
            }
            throw new PostWriteException("Something went wrong when creating post");
        } catch (SQLException e) {
            logger.error("SQL Error inserting post {}", post, e);
            throw new PostWriteException("Something went wrong when inserting post");
        }
    }

    /**
     * Finds a given post by the posts id
     */
    public Optional<Post> findById(int id) {
        String sql = "SELECT id, ownerId, name, description, price, condition, size, location, status, imageId, brand, typeOfClothing FROM Posts WHERE id = ?";
        try (Connection conn = databaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);) {
            logger.info("Getting post by id {}: " + id);

            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next())  {
                return postFromResultSet(rs);
            }
            logger.warn("No post found with id {}", id);
            return Optional.empty();

        } catch (SQLException e) {
            logger.error("SQL Exception error getting cat {}", id, e);
            return Optional.empty();
        }
    }

    /**
     * Finds all posts by a given ownerId
     */
    public List<Post> findByOwnerId(int ownerId) {
        String sql = "SELECT id, ownerId, name, description, price, condition, size, location, status, imageId, brand, typeOfClothing FROM Posts WHERE ownerId = ?";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            logger.info("Getting post by ownerId {}: ", ownerId);

            stmt.setInt(1, ownerId);
            var rs = stmt.executeQuery();
            var posts = new ArrayList<Post>();
            while (rs.next()) {
                var post = postFromResultSet(rs);
                post.ifPresent(posts::add);
            }
            logger.info("Found {} posts for ownerId", posts.size(), ownerId);
            return posts;

        } catch (SQLException e) {
            logger.error("SQL Exception error getting posts", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds all posts, no matter owner
     */
    public List<Post> findAll() {
        String sql = "SELECT id, ownerId, name, description, price, condition, size, location, status, imageId, brand, typeOfClothing FROM Posts";
        try (Connection conn = databaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            logger.info("Getting all posts");

            var rs = stmt.executeQuery();
            var posts = new ArrayList<Post>();
            while (rs.next()) {
                var post = postFromResultSet(rs);
                post.ifPresent(posts::add);
            }
            logger.info("Found {} posts", posts.size());
            return posts;

        } catch (SQLException e) {
            logger.error("SQL Exception error getting posts", e);
            throw new RuntimeException(e);
        }
    }

    public void update(Post post) {

    }

    public void delete(int id) {}

    /**
     * Creates a Postobject from the given resultset
     */
    Optional<Post> postFromResultSet(ResultSet rs) {
        try {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setOwnerId(rs.getInt("owner_id"));
            post.setName(rs.getString("name"));
            post.setDescription(rs.getString("description"));
            post.setPrice(rs.getDouble("price"));
            post.setCondition(Condition.valueOf(rs.getString("condition")));
            post.setSize(rs.getString("size"));
            post.setLocation(rs.getString("location"));
            post.setStatus(PostStatus.valueOf(rs.getString("status")));
            post.setImageId(rs.getString("image_id"));
            post.setBrand(String.valueOf(rs.getInt("brands_id")));
            post.setTypeOfClothing(String.valueOf(rs.getInt("product_type_id")));
            post.setDatePostedAt(rs.getDate("date_posted_at").toLocalDate());


            return Optional.of(post);

        } catch (SQLException e) {
            logger.error("Failed to map ResultSet", e);
            return Optional.empty();
        }
    }

}
