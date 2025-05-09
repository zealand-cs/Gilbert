package dk.zealandcs.gilbert.domain.validators;

import dk.zealandcs.gilbert.domain.user.PasswordError;
import dk.zealandcs.gilbert.exceptions.InvalidPasswordFormatException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {
    @Test
    public void isValid_minimumViable() {
        var password = "aA123456";
        assertTrue(PasswordValidator.isValid(password));
    }

    @Test
    public void isValid_validPassword() {
        var password = "PaSsWoRd123";
        assertTrue(PasswordValidator.isValid(password));
    }

    @Test
    public void isValid_missingLowerCase() {
        var password = "PASS1234";
        var exception = assertThrows(InvalidPasswordFormatException.class, () -> PasswordValidator.isValid(password));
        assertTrue(exception.errors.contains(PasswordError.MissingLowerCaseLetter));
    }

    @Test
    public void isValid_missingUpperCase() {
        var password = "pass1234";
        var exception = assertThrows(InvalidPasswordFormatException.class, () -> PasswordValidator.isValid(password));
        assertTrue(exception.errors.contains(PasswordError.MissingUpperCaseLetter));
    }

    @Test
    public void isValid_missingNumber() {
        var password = "passWORD";
        var exception = assertThrows(InvalidPasswordFormatException.class, () -> PasswordValidator.isValid(password));
        assertTrue(exception.errors.contains(PasswordError.MissingNumber));
    }

    @Test
    public void isValid_tooShort() {
        var password = "aA1";
        var exception = assertThrows(InvalidPasswordFormatException.class, () -> PasswordValidator.isValid(password));
        assertTrue(exception.errors.contains(PasswordError.TooFewCharacters));
    }

    @Test
    public void isValid_allErrors() {
        var password = "+";
        var expectedErrors = Arrays.asList(PasswordError.MissingLowerCaseLetter, PasswordError.MissingUpperCaseLetter, PasswordError.MissingNumber);

        var exception = assertThrows(InvalidPasswordFormatException.class, () -> PasswordValidator.isValid(password));
        assertTrue(exception.errors.containsAll(expectedErrors));
    }
}