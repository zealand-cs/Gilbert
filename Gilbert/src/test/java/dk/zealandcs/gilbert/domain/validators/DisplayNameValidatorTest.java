package dk.zealandcs.gilbert.domain.validators;

import dk.zealandcs.gilbert.exceptions.InvalidDisplayNameException;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class DisplayNameValidatorTest {
    @Test
    public void isValid_validDisplayName() {
        var name = "John Doe";
        assertTrue(DisplayNameValidator.isValid(name));
    }

    @Test
    public void isValid_minLength() {
        var name = "Bo";
        assertTrue(DisplayNameValidator.isValid(name));
    }

    @Test
    public void isValid_maxLength() {
        var name = org.apache.commons.lang3.StringUtils.repeat('a', 64);
        assertTrue(DisplayNameValidator.isValid(name));
    }

    @Test
    public void isValid_tooShort() {
        var name = "J";
        assertFalse(DisplayNameValidator.isValid(name));
    }

    @Test
    public void isValid_tooLong() {
        var name = org.apache.commons.lang3.StringUtils.repeat('a', 65);
        assertFalse(DisplayNameValidator.isValid(name));
    }
}