package dk.zealandcs.gilbert.infrastruture.user;

import dk.zealandcs.gilbert.config.DatabaseConfig;
import dk.zealandcs.gilbert.domain.user.User;
import dk.zealandcs.gilbert.domain.user.UserRole;
import dk.zealandcs.gilbert.exceptions.UserWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final DatabaseConfig databaseConfig;

    public UserRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    //Inserts new user into the database, hashes the password before storing
    @Override
    public User write(User user) throws UserWriteException {
        String sql = "INSERT INTO users (display_name, username, email, password, terms_agreement_date, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            logger.info("Attempting to register new user with email {} ", user.getEmail());

            stmt.setString(1, user.getDisplayName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPasswordHash());
            stmt.setDate(5, user.getTermsAcceptedDate());
            stmt.setString(6, user.getRole().name());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                    logger.info("Successfully registered new user with email {} ", user.getEmail());
                    return user;
                }
            }
            logger.error("Failed to register new user with email {} ", user.getEmail());
            throw new UserWriteException("Something went wrong when registering");
        } catch (SQLException e) {
            logger.error("Error while trying to register new user: {}", e.getMessage());
            throw new UserWriteException("Error writing new user");
        }
    }

    public Optional<User> findByField(String field, String value) {
        String sql = "SELECT id, display_name, username, email, password, profile_picture_id, terms_agreement_date, role FROM users WHERE " + field + " = ?";
        try (Connection conn = databaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            logger.info("Attempting to find user by {} with {} ", field, value);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return userFromResultSet(rs);
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.warn("Error while trying to find user by {} with {} ", field, value);
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT id, display_name, username, email, password, profile_picture_id, terms_agreement_date, role FROM users";
        List<User> users = new ArrayList<>();

        try (Connection conn = databaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("display_name"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("profile_picture_id"),
                        rs.getDate("terms_agreement_date")
                );
                user.setId(rs.getInt("id"));
                user.setRole(UserRole.valueOf(rs.getString("role").toUpperCase()));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error("Error while fetching all users: {}", e.getMessage());
            return List.of();
        }

    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET display_name = ?, username = ?, email = ?, password = ?, profile_picture_id = ?, terms_agreement_date = ?, role = ? WHERE id = ?";

        logger.info("Attempting to update user {}", user.getId());

        try (Connection conn = databaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getDisplayName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPasswordHash());
            stmt.setString(5, user.getProfilePictureId().orElse(null));
            stmt.setDate(6, user.getTermsAcceptedDate());
            stmt.setString(7, user.getRole().toString());
            stmt.setInt(8, user.getId());
            stmt.executeUpdate();
            logger.info("Updated users {}", user.getId());
        } catch (SQLException e) {
            logger.warn("Error while updating user {}: {}", user.getId(), e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        logger.info("Attempting to delete user with id {} ", id);

        try (Connection conn = databaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Deleted user with id {}", id);
        } catch (SQLException e) {
            logger.warn("Error while trying to delete user with id {} ", id);
        }
    }

    //Converts a result set into a user object
    Optional<User> userFromResultSet(ResultSet rs) {
        try {
            var user = new User(
                    rs.getString("display_name"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("profile_picture_id"),
                    rs.getDate("terms_agreement_date")
            );
            user.setId(rs.getInt("id"));
            user.setRole(UserRole.valueOf(rs.getString("role").toUpperCase()));
            return Optional.of(user);
        } catch (SQLException e) {
            logger.error("Error mapping user from result {}", e.getMessage());
            return Optional.empty();
        }
    }
}
