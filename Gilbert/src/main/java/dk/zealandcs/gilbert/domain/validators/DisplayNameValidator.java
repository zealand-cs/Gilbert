package dk.zealandcs.gilbert.domain.validators;

import dk.zealandcs.gilbert.exceptions.InvalidDisplayNameException;

public class DisplayNameValidator {
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 64;

    public static boolean isValid(String displayName) throws InvalidDisplayNameException {
        return displayName.length() >= MIN_NAME_LENGTH && displayName.length() <= MAX_NAME_LENGTH;
    }
}
