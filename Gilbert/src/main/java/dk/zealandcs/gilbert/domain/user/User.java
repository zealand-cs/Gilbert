package dk.zealandcs.gilbert.domain.user;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

public class User {
    private static final int SUFFIX_LENGTH = 5;

    private int id;

    private String displayName;
    private String username;
    private String email;
    private String passwordHash;
    private String profilePictureId;
    private UserRole role;
    private Date termsAcceptedDate;

    public User(String displayName, String username, String email, String password, Date termsAcceptedDate) {
        this.displayName = displayName;
        this.username = username;
        this.email = email;
        this.passwordHash = password;
        this.profilePictureId = null;
        this.role = UserRole.USER;
        this.termsAcceptedDate = termsAcceptedDate;
    }

    public User(String displayName, String username, String email, String password, String profilePictureId, Date termsAcceptedDate) {
        this.displayName = displayName;
        this.username = username;
        this.email = email;
        this.passwordHash = password;
        this.profilePictureId = profilePictureId;
        this.role = UserRole.USER;
        this.termsAcceptedDate = termsAcceptedDate;
    }

    public User(RegisterUser user) {
        this(user.getDisplayName(), null, user.getEmail(), user.getPassword(), null);
        this.username = generateUsername(user.getDisplayName()) + generateUsernameSuffix(SUFFIX_LENGTH);
        this.termsAcceptedDate = new Date(Date.from(Instant.now()).getTime());
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets a password hash directly, with no modifications
     * @param passwordHash the hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Hashes the password and sets it.
     * @param password the password
     */
    public void setPassword(String password) {
        var passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        setPasswordHash(passwordHash);
    }

    public void hashPassword() {
        this.passwordHash = BCrypt.hashpw(passwordHash, BCrypt.gensalt());
    }

    public Date getTermsAcceptedDate() {
        return termsAcceptedDate;
    }

    public void setTermsAcceptedDate(Date termsAcceptedDate) {
        this.termsAcceptedDate = termsAcceptedDate;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.passwordHash);
    }

    public static String generateUsername(String displayName) {
        StringBuilder username = new StringBuilder();
        for (var cha : displayName.toCharArray()) {
            if (Character.isLetterOrDigit(cha)) {
                username.append(Character.toLowerCase(cha));
            }
        }
        return username.toString();
    }
    public static String generateUsernameSuffix(int length) {
        return RandomStringUtils.random(length, 0, 10, false, true, new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', }, new Random());
    }

    public Optional<String> getProfilePictureId() {
        return Optional.ofNullable(profilePictureId);
    }

    public void setProfilePictureId(String profilePictureId) {
        this.profilePictureId = profilePictureId;
    }
}
