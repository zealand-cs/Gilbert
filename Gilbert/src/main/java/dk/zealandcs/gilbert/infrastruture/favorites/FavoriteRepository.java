package dk.zealandcs.gilbert.infrastruture.favorites;

import dk.zealandcs.gilbert.config.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class FavoriteRepository implements IFavoriteRepository {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteRepository.class);

    private final DatabaseConfig databaseConfig;

    public FavoriteRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public void insert(int userId, int postId) {
        String sql = "INSERT INTO user_favorites (user_id, post_id) VALUES (?, ?)";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            logger.info("Adding favorite {} for user {}", postId, userId);

            stmt.setInt(1, userId);
            stmt.setInt(2, postId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while trying to add favorite (post {}) for user {}: {}", postId, userId, e.getMessage());
            // TODO: Throw correct error
        }
    }

    @Override
    public void remove(int userId, int postId) {
        String sql = "DELETE FROM user_favorites WHERE user_id = ? & post_id = ?";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            logger.info("Adding favorite {} for user {}", postId, userId);

            stmt.setInt(1, userId);
            stmt.setInt(2, postId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while trying to remove favorite (post {}) for user {}: {}", userId, postId, e.getMessage());
            // TODO: Throw correct error
        }
    }

    @Override
    public int getNumberOfFavoritesByPost(int postId) {
        String sql = "SELECT COUNT(*) FROM user_favorites WHERE post_id = ?";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            logger.info("Counting favorites on post {}", postId);

            stmt.setInt(1, postId);

            var res = stmt.executeQuery();
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            logger.error("Error while counting post {} favorites: {}", postId, e.getMessage());
            // TODO: Throw correct error
            return 0;
        }
    }
}