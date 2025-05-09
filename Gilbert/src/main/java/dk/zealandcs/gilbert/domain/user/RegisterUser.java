package dk.zealandcs.gilbert.domain.user;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class RegisterUser {
    private final String displayName;
    private final String email;
    private final String password;
    private final boolean termsAccepted;

    public RegisterUser(String displayName, String email, String password, boolean termsAccepted) {
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.termsAccepted = termsAccepted;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAcceptedTerms() {
        return termsAccepted;
    }
}
