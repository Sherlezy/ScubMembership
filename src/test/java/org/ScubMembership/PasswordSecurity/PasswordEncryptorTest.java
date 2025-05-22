package org.ScubMembership.PasswordSecurity;

import org.ScubMembership.PasswordSecurity.PasswordEncryptor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncryptorTest {

    @Test
    public void testHashPassword_CommonInput() {
        String password = "Password123!";
        String hash = PasswordEncryptor.hashPassword(password);

        assertNotNull(hash);
        assertEquals(64, hash.length()); // SHA-256 hashes are 64 hex characters
    }

    @Test
    public void testHashPassword_EmptyString() {
        String hash = PasswordEncryptor.hashPassword("");
        assertNotNull(hash);
        assertEquals(64, hash.length());
    }

    @Test
    public void testHashPassword_SpecialCharacters() {
        String password = "@#$%^&*()_+-=!";
        String hash = PasswordEncryptor.hashPassword(password);
        assertNotNull(hash);
        assertEquals(64, hash.length());
    }


    @Test
    public void testHashPassword_DeterministicOutput() {
        String password = "SamePassword";
        String hash1 = PasswordEncryptor.hashPassword(password);
        String hash2 = PasswordEncryptor.hashPassword(password);

        assertEquals(hash1, hash2);
    }

    @Test
    public void testHashPassword_DifferentInputDifferentHash() {
        String password1 = "password1";
        String password2 = "password2";

        String hash1 = PasswordEncryptor.hashPassword(password1);
        String hash2 = PasswordEncryptor.hashPassword(password2);

        assertNotEquals(hash1, hash2);
    }
}
