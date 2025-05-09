package dk.zealandcs.gilbert.exceptions;

import dk.zealandcs.gilbert.domain.user.PasswordError;

import java.util.List;

public class InvalidPasswordFormatException extends RuntimeException {
    public List<PasswordError> errors;

    public InvalidPasswordFormatException(List<PasswordError> passwordProblems) {
        super();
        this.errors = passwordProblems;
    }
}
