package dk.zealandcs.gilbert.domain.user;

import java.util.ArrayList;
import java.util.List;

public class UserRegister {
    private String displayName;
    private String email;
    private String password;

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    private boolean verifyEmail() {
        // Could use regex, but its really no necessary
        var split = email.split("@", 2);
        return split.length >= 2 && split[1] != null && split[1].contains(".");
    }

    private boolean verifyPassword() {
        List<PasswordProblem> problems = new ArrayList<>();
        if (password.length() < 8) {
            problems.add(PasswordProblem.TooFewCharacters);
        }

        boolean containsNumber = false;
        boolean containsCapitalLetter = false;

        for (var cha : password.toCharArray()) {
            if (Character.isDigit(cha)) {
                containsNumber = true;
            }
            if (Character.isUpperCase(cha)) {
                containsCapitalLetter = true;
            }

            if (containsNumber && containsCapitalLetter) {
                break;
            }
        }

        if (!containsNumber) {
            problems.add(PasswordProblem.MissingNumber);
        }

        if (!containsCapitalLetter) {
            problems.add(PasswordProblem.MissingCapitalLetter);
        }

        return problems.isEmpty();
    }

    private boolean verifyName() {
        for (var cha : displayName.toCharArray()) {
            if (Character.isDigit(cha) || Character.isAlphabetic(cha) || Character.isSpaceChar(cha)) {
                continue;
            }
            return false;
        }
        return true;
    }

    public boolean verify() {
        return verifyName() && verifyEmail() && verifyPassword();
    }
}
