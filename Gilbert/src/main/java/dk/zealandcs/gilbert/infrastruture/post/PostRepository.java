package dk.zealandcs.gilbert.infrastruture.post;

import dk.zealandcs.gilbert.config.DatabaseConfig;
import dk.zealandcs.gilbert.domain.post.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository implements IPostRepository {
    private static final Logger logger = LoggerFactory.getLogger(PostRepository.class);

    private final DatabaseConfig databaseConfig;

    public PostRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    /**
     * Writes a new post to the database from given paramaters.
     */
    @Override
    public Post write(Post post) {
        String sql = "INSERT INTO posts(owner_id, name, description, price, item_condition, size, location," +
                " status, image_id, brands_id, product_type_id, date_posted_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            stmt.setString(9, post.getImageId().isPresent() ? post.getImageId().get() : null);
            stmt.setInt(10, post.getBrand().getId());
            stmt.setInt(11, post.getTypeOfClothing().getId());
            stmt.setTimestamp(12, new Timestamp(post.getDatePostedAt().getTime()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new PostWriteException("Failed to create post, no rows affected");
            }
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
        String sql = """
                SELECT p.*, b.name as brand_name, pt.name as type_name, u.display_name as owner_display_name
                FROM posts p 
                LEFT JOIN brands b ON p.brands_id = b.id 
                LEFT JOIN product_types pt ON p.product_type_id = pt.id 
                LEFT JOIN users u ON p.owner_id = u.id
                WHERE p.id = ?
                """;

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            logger.info("Getting post by id {}: " + id);

            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return postFromResultSet(rs);
            }
            logger.warn("No post found with id {}", id);
            return Optional.empty();

        } catch (SQLException e) {
            logger.error("SQL Exception error getting post {}", id, e);
            return Optional.empty();
        }
    }

    /**
     * Finds all posts by a given ownerId
     */
    public List<Post> findByOwnerId(int ownerId) {
        String sql = """
                SELECT p.*, b.name as brand_name, pt.name as type_name, u.display_name as owner_display_name
                FROM posts p
                LEFT JOIN brands b ON p.brands_id = b.id 
                LEFT JOIN product_types pt ON p.product_type_id = pt.id 
                LEFT JOIN users u ON p.owner_id = u.id
                WHERE p.owner_id = ?
                """;
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
        String sql = """
                     SELECT p.*, b.name as brand_name, pt.name as type_name, u.display_name as owner_display_name 
                     FROM posts p 
                     LEFT JOIN brands b ON p.brands_id = b.id 
                     LEFT JOIN product_types pt ON p.product_type_id = pt.id
                     LEFT JOIN users u ON p.owner_id = u.id
                """;


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
        String sql = "UPDATE posts SET owner_id = ?, name = ?, description = ?, price = ?, item_condition = ?, " +
                "size = ?, location = ?, status = ?, image_id = ?, brands_id = ?, product_type_id = ?, date_posted_at = ? " +
                "WHERE id = ?";

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            logger.info("Updating post {}", post);

            stmt.setInt(1, post.getOwnerId());
            stmt.setString(2, post.getName());
            stmt.setString(3, post.getDescription());
            stmt.setDouble(4, post.getPrice());
            stmt.setString(5, post.getCondition().name());
            stmt.setString(6, post.getSize());
            stmt.setString(7, post.getLocation());
            stmt.setString(8, post.getStatus().name());
            stmt.setString(9, post.getImageId().isPresent() ? post.getImageId().get() : null);
            stmt.setInt(10, post.getBrand().getId());
            stmt.setInt(11, post.getTypeOfClothing().getId());
            stmt.setTimestamp(12, new Timestamp(post.getDatePostedAt().getTime()));
            stmt.setInt(13, post.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL Exception error updating post {}", post, e);
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        try (Connection conn = databaseConfig.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Deleted post with id {}", id);
        } catch (SQLException e) {
            logger.error("SQL Exception error deleting post {}", id, e);
        }
    }

    public List<Brand> getAllBrands() {
        String sql = "SELECT id, name from brands";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            logger.info("Getting all brands");


            var rs = stmt.executeQuery();
            var brands = new ArrayList<Brand>();
            while (rs.next()) {
                var brand = brandFromResultSet(rs);
                brand.ifPresent(brands::add);
            }
            logger.info("Found {} brands", brands.size());
            return brands;
        } catch (SQLException e) {
            logger.error("SQL Exception error getting brands", e);
            throw new RuntimeException(e);
        }
    }

    public List<ProductType> getAllProductTypes() {
        String sql = "SELECT id, name, parent_id FROM product_types ORDER BY order_index, name DESC";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            logger.info("Getting all product types");

            var rs = stmt.executeQuery();
            var productTypes = new ArrayList<ProductType>();
            while (rs.next()) {
                var productType = productTypeFromResultSet(rs);
                productType.ifPresent(productTypes::add);
            }
            logger.info("Found {} product types", productTypes.size());
            return productTypes;

        } catch (SQLException e) {
            logger.error("SQL Exception error getting product types", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> search(String query, String[] users, String[] categories) {
        String newSql = """
                SELECT * FROM
                (
                    SELECT
                        p.*,
                        b.name AS brand_name,
                        pt.name AS type_name,
                        u.display_name AS owner_display_name,
                        SUM(
                          MATCH(b.name) AGAINST(?) +
                          MATCH(p.name, p.description) AGAINST(?)
                        ) AS relevance
                    FROM posts p
                        LEFT JOIN brands b ON p.brands_id = b.id
                        LEFT JOIN product_types pt ON p.product_type_id = pt.id
                        LEFT JOIN users u ON p.owner_id = u.id
                """;
        String partTwo = """
                    GROUP BY (p.id)
                ) AS filtered
                """;
        String partThree = """
                ORDER BY relevance DESC
                """;

        String sql = newSql;
        if (categories.length > 0) {
            String inSql = String.join(",", Collections.nCopies(categories.length, "?"));
            sql += "WHERE pt.id IN (" +  inSql + ")";

            if (users.length > 0) {
                sql += " AND ";
            }
        }
        if (users.length > 0) {
            if (categories.length == 0) {
                sql += " WHERE ";
            }

            String inSql = String.join(",", Collections.nCopies(users.length, "?"));
            sql += "u.username IN (" + inSql + ")";
        }
        sql += partTwo;
        if (!query.isEmpty()) {
            sql += "WHERE relevance > 0 ";
        }
        sql += partThree;

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            logger.info("Getting posts by query: {}", query);

            int i = 1;
            stmt.setString(i, query);
            i++;
            stmt.setString(i, query);
            i++;
            for (var category : categories) {
                stmt.setString(i, category);
                i++;
            }
            for (var user : users) {
                stmt.setString(i, user);
                i++;
            }

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

    @Override
    public List<Post> getUserFavorites(int userId) {
        String sql = """
                SELECT p.*, b.name as brand_name, pt.name as type_name, u.display_name as owner_display_name
                FROM posts p
                LEFT JOIN brands b ON p.brands_id = b.id
                LEFT JOIN product_types pt ON p.product_type_id = pt.id
                LEFT JOIN users u ON p.owner_id = u.id
                LEFT JOIN user_favorites uf ON p.id = uf.post_id
                WHERE uf.user_id = ?
                """;
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            logger.info("Getting favorite posts by user id {}: ", userId);

            stmt.setInt(1, userId);
            var rs = stmt.executeQuery();
            var posts = new ArrayList<Post>();
            while (rs.next()) {
                var post = postFromResultSet(rs);
                post.ifPresent(posts::add);
            }
            logger.info("Found {} favorited posts for user", posts.size(), userId);
            return posts;

        } catch (SQLException e) {
            logger.error("SQL Exception error getting posts", e);
            throw new RuntimeException(e);
        }
    }

    //method to findall posts with a given status (for example pendingapproval)
    @Override
    public List<Post> findAllByStatus(PostStatus status) {
        String sql = """
                SELECT p.*, b.name as brand_name, pt.name as type_name, u.display_name as owner_display_name
                FROM posts p
                LEFT JOIN brands b ON p.brands_id = b.id 
                LEFT JOIN product_types pt ON p.product_type_id = pt.id 
                LEFT JOIN users u ON p.owner_id = u.id
                WHERE p.status = ?
                """;

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();
            List<Post> posts = new ArrayList<>();

            while (rs.next()) {
                postFromResultSet(rs).ifPresent(posts::add);
            }

            logger.info("Found {} posts with status {}", posts.size(), status);
            return posts;
        } catch (SQLException e) {
            logger.error("Error fetching posts by status: {}", e.getMessage());
            return new ArrayList<>();
        }
    }


    // method to update a single poststatus
    @Override
    public void updateStatus(int postId, PostStatus status) {
        String sql = "UPDATE posts SET status = ? WHERE id = ?";

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            stmt.setInt(2, postId);
            stmt.executeUpdate();

            logger.info("Updated status of post {} to {}", postId, status);
        } catch (SQLException e) {
            logger.error("Error updating post status: {}", e.getMessage());
            throw new RuntimeException("Failed to update post status", e);
        }
    }


    /**
     * Creates a Postobject from the given resultset
     */
    Optional<Post> postFromResultSet(ResultSet rs) {
        try {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setOwnerDisplayName(rs.getString("owner_display_name"));
            post.setOwnerId(rs.getInt("owner_id"));
            post.setName(rs.getString("name"));
            post.setDescription(rs.getString("description"));
            post.setPrice(rs.getDouble("price"));
            post.setCondition(Condition.valueOf(rs.getString("item_condition")));
            post.setSize(rs.getString("size"));
            post.setLocation(rs.getString("location"));
            post.setImageId(rs.getString("image_id"));
            post.setDatePostedAt(rs.getDate("date_posted_at"));

            String statusStr = rs.getString("status");
            if (statusStr != null) {
                post.setStatus(PostStatus.valueOf(statusStr));
            }

            Brand brand = new Brand();
            brand.setId(rs.getInt("brands_id"));
            brand.setName(rs.getString("brand_name"));
            post.setBrand(brand);

            ProductType type = new ProductType();
            type.setId(rs.getInt("product_type_id"));
            post.setTypeOfClothing(type);

            return Optional.of(post);

        } catch (SQLException e) {
            logger.error("Failed to map ResultSet", e);
            return Optional.empty();
        }
    }

    private Optional<Brand> brandFromResultSet(ResultSet rs) throws SQLException {
        return Optional.of(new Brand(
                rs.getString("name"),
                rs.getInt("id")
        ));
    }

    private Optional<ProductType> productTypeFromResultSet(ResultSet rs) throws SQLException {
        var parentId = rs.getInt("parent_id");
        return Optional.of(new ProductType(
                rs.getString("name"),
                rs.getInt("id"),
                parentId == 0 ? null : parentId
        ));
    }
}
