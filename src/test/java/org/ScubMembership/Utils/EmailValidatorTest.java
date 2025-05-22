package org.ScubMembership.Utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {

    @Test
    public void testValidEmails() {
        assertTrue(EmailValidator.isValidEmail("test@example.com"));
        assertTrue(EmailValidator.isValidEmail("john.doe@example.co.uk"));
        assertTrue(EmailValidator.isValidEmail("user123+filter@domain.org"));
        assertTrue(EmailValidator.isValidEmail("a@b.it"));
        assertTrue(EmailValidator.isValidEmail("simple_email123@domain.com"));
    }

    @Test
    public void testInvalidEmails() {
        assertFalse(EmailValidator.isValidEmail("plainaddress"));
        assertFalse(EmailValidator.isValidEmail("@no-local-part.com"));
        assertFalse(EmailValidator.isValidEmail("user@.missing-domain"));
        assertFalse(EmailValidator.isValidEmail("user@domain"));
        assertFalse(EmailValidator.isValidEmail("user@domain.toolongextension"));
        assertFalse(EmailValidator.isValidEmail("user@domain..com"));
        assertFalse(EmailValidator.isValidEmail("user@@domain.com"));
        assertFalse(EmailValidator.isValidEmail("user@domain.c")); // too short TLD
        assertFalse(EmailValidator.isValidEmail("user@-domain.com")); // domain starts with invalid character
    }

    @Test
    public void testNullOrEmpty() {
        assertFalse(EmailValidator.isValidEmail(null));
        assertFalse(EmailValidator.isValidEmail(""));
        assertFalse(EmailValidator.isValidEmail("   "));
    }
}
