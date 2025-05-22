package org.ScubMembership.Users.Admin;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    private Admin admin;
    private final String TEST_EMAIL = "admin@example.com";
    private final String TEST_USERNAME = "adminuser";
    private final String TEST_PASSWORD = "adminpass123";
    private final int TEST_ID = 1;

    @BeforeEach
    void setUp() {
        admin = new Admin(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD, TEST_ID);
    }

    @Test
    @DisplayName("Constructor should initialize Admin with correct values")
    void testConstructor() {
        assertEquals(TEST_EMAIL, admin.getEmail());
        assertEquals(TEST_USERNAME, admin.getUsername());
        assertEquals(TEST_PASSWORD, admin.getPassword());
        assertEquals(TEST_ID, admin.getID());
    }

    @Test
    @DisplayName("Constructor should handle different parameter types correctly")
    void testConstructorWithDifferentValues() {
        String email = "different@test.com";
        String username = "differentuser";
        String password = "differentpass";
        int id = 999;

        Admin newAdmin = new Admin(email, username, password, id);

        assertEquals(email, newAdmin.getEmail());
        assertEquals(username, newAdmin.getUsername());
        assertEquals(password, newAdmin.getPassword());
        assertEquals(id, newAdmin.getID());
    }

    @Test
    @DisplayName("Constructor should handle null email")
    void testConstructorWithNullEmail() {
        Admin nullEmailAdmin = new Admin(null, TEST_USERNAME, TEST_PASSWORD, TEST_ID);

        assertNull(nullEmailAdmin.getEmail());
        assertEquals(TEST_USERNAME, nullEmailAdmin.getUsername());
        assertEquals(TEST_PASSWORD, nullEmailAdmin.getPassword());
        assertEquals(TEST_ID, nullEmailAdmin.getID());
    }

    @Test
    @DisplayName("Constructor should handle null username")
    void testConstructorWithNullUsername() {
        Admin nullUsernameAdmin = new Admin(TEST_EMAIL, null, TEST_PASSWORD, TEST_ID);

        assertEquals(TEST_EMAIL, nullUsernameAdmin.getEmail());
        assertNull(nullUsernameAdmin.getUsername());
        assertEquals(TEST_PASSWORD, nullUsernameAdmin.getPassword());
        assertEquals(TEST_ID, nullUsernameAdmin.getID());
    }

    @Test
    @DisplayName("Constructor should handle null password")
    void testConstructorWithNullPassword() {
        Admin nullPasswordAdmin = new Admin(TEST_EMAIL, TEST_USERNAME, null, TEST_ID);

        assertEquals(TEST_EMAIL, nullPasswordAdmin.getEmail());
        assertEquals(TEST_USERNAME, nullPasswordAdmin.getUsername());
        assertNull(nullPasswordAdmin.getPassword());
        assertEquals(TEST_ID, nullPasswordAdmin.getID());
    }

    @Test
    @DisplayName("Constructor should handle negative ID")
    void testConstructorWithNegativeID() {
        int negativeId = -1;
        Admin negativeIdAdmin = new Admin(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD, negativeId);

        assertEquals(TEST_EMAIL, negativeIdAdmin.getEmail());
        assertEquals(TEST_USERNAME, negativeIdAdmin.getUsername());
        assertEquals(TEST_PASSWORD, negativeIdAdmin.getPassword());
        assertEquals(negativeId, negativeIdAdmin.getID());
    }

    @Test
    @DisplayName("Constructor should handle zero ID")
    void testConstructorWithZeroID() {
        int zeroId = 0;
        Admin zeroIdAdmin = new Admin(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD, zeroId);

        assertEquals(zeroId, zeroIdAdmin.getID());
    }

    // email test

    @Test
    @DisplayName("getEmail should return the correct email")
    void testGetEmail() {
        assertEquals(TEST_EMAIL, admin.getEmail());
    }

    @Test
    @DisplayName("setEmail should correctly update the email")
    void testSetEmail() {
        String newEmail = "newemail@example.com";
        admin.setEmail(newEmail);

        assertEquals(newEmail, admin.getEmail());
        assertNotEquals(TEST_EMAIL, admin.getEmail());
    }

    @Test
    @DisplayName("setEmail should handle null values")
    void testSetEmailNull() {
        admin.setEmail(null);
        assertNull(admin.getEmail());
    }

    @Test
    @DisplayName("setEmail should handle empty string")
    void testSetEmailEmpty() {
        String emptyEmail = "";
        admin.setEmail(emptyEmail);
        assertEquals(emptyEmail, admin.getEmail());
    }

    @Test
    @DisplayName("setEmail should handle whitespace")
    void testSetEmailWhitespace() {
        String whitespaceEmail = "   ";
        admin.setEmail(whitespaceEmail);
        assertEquals(whitespaceEmail, admin.getEmail());
    }

    // username test

    @Test
    @DisplayName("getUsername should return the correct username")
    void testGetUsername() {
        assertEquals(TEST_USERNAME, admin.getUsername());
    }

    @Test
    @DisplayName("setUsername should correctly update the username")
    void testSetUsername() {
        String newUsername = "newusername";
        admin.setUsername(newUsername);

        assertEquals(newUsername, admin.getUsername());
        assertNotEquals(TEST_USERNAME, admin.getUsername());
    }

    @Test
    @DisplayName("setUsername should handle null values")
    void testSetUsernameNull() {
        admin.setUsername(null);
        assertNull(admin.getUsername());
    }

    @Test
    @DisplayName("setUsername should handle empty string")
    void testSetUsernameEmpty() {
        String emptyUsername = "";
        admin.setUsername(emptyUsername);
        assertEquals(emptyUsername, admin.getUsername());
    }

    @Test
    @DisplayName("setUsername should handle special characters")
    void testSetUsernameSpecialChars() {
        String specialUsername = "admin@123_test";
        admin.setUsername(specialUsername);
        assertEquals(specialUsername, admin.getUsername());
    }

    // password test

    @Test
    @DisplayName("getPassword should return the correct password")
    void testGetPassword() {
        assertEquals(TEST_PASSWORD, admin.getPassword());
    }

    @Test
    @DisplayName("setPassword should correctly update the password")
    void testSetPassword() {
        String newPassword = "newpassword456";
        admin.setPassword(newPassword);

        assertEquals(newPassword, admin.getPassword());
        assertNotEquals(TEST_PASSWORD, admin.getPassword());
    }

    @Test
    @DisplayName("setPassword should handle null values")
    void testSetPasswordNull() {
        admin.setPassword(null);
        assertNull(admin.getPassword());
    }

    @Test
    @DisplayName("setPassword should handle empty string")
    void testSetPasswordEmpty() {
        String emptyPassword = "";
        admin.setPassword(emptyPassword);
        assertEquals(emptyPassword, admin.getPassword());
    }

    @Test
    @DisplayName("setPassword should handle complex passwords")
    void testSetPasswordComplex() {
        String complexPassword = "P@ssw0rd!2024#$%";
        admin.setPassword(complexPassword);
        assertEquals(complexPassword, admin.getPassword());
    }

    // ID test

    @Test
    @DisplayName("getID should return the correct ID")
    void testGetID() {
        assertEquals(TEST_ID, admin.getID());
    }

    @Test
    @DisplayName("setID should correctly update the ID")
    void testSetID() {
        int newID = 42;
        admin.setID(newID);

        assertEquals(newID, admin.getID());
        assertNotEquals(TEST_ID, admin.getID());
    }

    @Test
    @DisplayName("setID should handle zero")
    void testSetIDZero() {
        admin.setID(0);
        assertEquals(0, admin.getID());
    }

    @Test
    @DisplayName("setID should handle negative values")
    void testSetIDNegative() {
        int negativeID = -999;
        admin.setID(negativeID);
        assertEquals(negativeID, admin.getID());
    }

    @Test
    @DisplayName("setID should handle large values")
    void testSetIDLarge() {
        int largeID = Integer.MAX_VALUE;
        admin.setID(largeID);
        assertEquals(largeID, admin.getID());
    }

    // test of multiple fields

    @Test
    @DisplayName("Multiple setters should work independently")
    void testMultipleSettersIndependently() {
        String newEmail = "updated@example.com";
        String newUsername = "updateduser";
        String newPassword = "updatedpass";
        int newID = 999;

        admin.setEmail(newEmail);
        assertEquals(newEmail, admin.getEmail());
        assertEquals(TEST_USERNAME, admin.getUsername());
        assertEquals(TEST_PASSWORD, admin.getPassword());
        assertEquals(TEST_ID, admin.getID());

        admin.setUsername(newUsername);
        assertEquals(newEmail, admin.getEmail());
        assertEquals(newUsername, admin.getUsername());
        assertEquals(TEST_PASSWORD, admin.getPassword());
        assertEquals(TEST_ID, admin.getID());

        admin.setPassword(newPassword);
        assertEquals(newEmail, admin.getEmail());
        assertEquals(newUsername, admin.getUsername());
        assertEquals(newPassword, admin.getPassword());
        assertEquals(TEST_ID, admin.getID());

        admin.setID(newID);
        assertEquals(newEmail, admin.getEmail());
        assertEquals(newUsername, admin.getUsername());
        assertEquals(newPassword, admin.getPassword());
        assertEquals(newID, admin.getID());
    }

    @Test
    @DisplayName("Admin should implement Serializable")
    void testSerializableInterface() {
        assertInstanceOf(Serializable.class, admin);
    }

}

