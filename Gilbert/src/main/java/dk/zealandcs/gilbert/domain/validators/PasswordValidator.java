package dk.zealandcs.gilbert.domain.validators;

import dk.zealandcs.gilbert.domain.user.PasswordError;
import dk.zealandcs.gilbert.exceptions.InvalidPasswordFormatException;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidator {
    public static boolean isValid(String password) throws InvalidPasswordFormatException {
        List<PasswordError> problems = new ArrayList<>();
        if (password.length() < 8) {
            problems.add(PasswordError.TooFewCharacters);
        }

        boolean containsNumber = false;
        boolean containsUppercaseLetter = false;
        boolean containsLowercaseLetter = false;

        for (var cha : password.toCharArray()) {
            if (Character.isDigit(cha)) {
                containsNumber = true;
            }
            if (Character.isUpperCase(cha)) {
                containsUppercaseLetter = true;
            }
            if (Character.isLowerCase(cha)) {
                containsLowercaseLetter = true;
            }

            if (containsNumber && containsUppercaseLetter && containsLowercaseLetter) {
                break;
            }
        }

        if (!containsNumber) {
            problems.add(PasswordError.MissingNumber);
        }

        if (!containsUppercaseLetter) {
            problems.add(PasswordError.MissingUpperCaseLetter);
        }

        if (!containsLowercaseLetter) {
            problems.add(PasswordError.MissingLowerCaseLetter);
        }

        if (problems.isEmpty()) {
            return true;
        }

        throw new InvalidPasswordFormatException(problems);
    }
}
