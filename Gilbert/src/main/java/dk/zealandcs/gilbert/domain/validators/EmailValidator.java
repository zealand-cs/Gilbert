package dk.zealandcs.gilbert.domain.validators;

import dk.zealandcs.gilbert.exceptions.InvalidEmailFormatException;

public class EmailValidator {
    public static boolean isValid(String email) throws InvalidEmailFormatException {
        // Could use regex, but its really no necessary
        var split = email.split("@", 2);
        if (split.length >= 2 && split[1] != null && split[1].contains(".")) {
            return true;
        }
        throw new InvalidEmailFormatException("Invalid email format");
    }
}
