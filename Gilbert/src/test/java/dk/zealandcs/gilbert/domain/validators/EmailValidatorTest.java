package dk.zealandcs.gilbert.domain.validators;

import dk.zealandcs.gilbert.exceptions.InvalidEmailFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {
    @Test
    public void isValid_validEmail() {
        var email = "john@example.com";
        assertTrue(EmailValidator.isValid(email));
    }

    @Test
    public void isValid_multipleAtSeperators() {
        var email = "john@test@example.com";
        assertTrue(EmailValidator.isValid(email));
    }

    @Test
    public void isValid_edgeCaseMail() {
        var email = "joh.akjh.+df/879832y08hlj.n@examplasdf/e++-.asd-=?";
        assertTrue(EmailValidator.isValid(email));
    }

    @Test
    public void isValid_zeroDotThrows() {
        var email = "john@example";
        assertThrows(InvalidEmailFormatException.class, () -> EmailValidator.isValid(email));
    }

    @Test
    public void isValid_missingAt() {
        var email = "johnexample.dk";
        assertThrows(InvalidEmailFormatException.class, () -> EmailValidator.isValid(email));
    }
}