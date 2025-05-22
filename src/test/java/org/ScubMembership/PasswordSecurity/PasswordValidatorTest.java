package org.ScubMembership.PasswordSecurity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordValidatorTest {

    @Test
    public void testValidStrongPasswords() {
        assertTrue(PasswordValidator.isStrongPassword("Abcdef1@"));
        assertTrue(PasswordValidator.isStrongPassword("StrongPass1$"));
        assertTrue(PasswordValidator.isStrongPassword("Valid123!A"));
        assertTrue(PasswordValidator.isStrongPassword("A1b2c3d4@"));
    }

    @Test
    public void testTooShortPassword() {
        assertFalse(PasswordValidator.isStrongPassword("Ab1@")); // Only 4 characters
    }

    @Test
    public void testMissingUppercase() {
        assertFalse(PasswordValidator.isStrongPassword("abcdef1@")); // no uppercase
    }

    @Test
    public void testMissingLowercase() {
        assertFalse(PasswordValidator.isStrongPassword("ABCDEF1@")); // no lowercase
    }

    @Test
    public void testMissingDigit() {
        assertFalse(PasswordValidator.isStrongPassword("Abcdefg@")); // no digit
    }

    @Test
    public void testMissingSpecialChar() {
        assertFalse(PasswordValidator.isStrongPassword("Abcdefg1")); // no special character
    }

    @Test
    public void testOnlySpecialCharacters() {
        assertFalse(PasswordValidator.isStrongPassword("@#$%&*!@")); // only special characters
    }

    @Test
    public void testEmptyPassword() {
        assertFalse(PasswordValidator.isStrongPassword("")); // empty
    }

    @Test
    public void testWhitespaceInPassword() {
        assertFalse(PasswordValidator.isStrongPassword("Abc def1@")); // contains space
    }

    @Test
    public void testNullPassword() {
        assertFalse(PasswordValidator.isStrongPassword(null)); // should return false gracefully
    }
}
