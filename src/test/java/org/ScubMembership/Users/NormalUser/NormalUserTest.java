package org.ScubMembership.Users.NormalUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.internal.matchers.Null;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

class NormalUserTest {

    private NormalUser normalUser;
    private final String TEST_EMAIL = "user@example.com";
    private final String TEST_USERNAME = "normaluser";
    private final String TEST_PASSWORD = "userpass123";
    private final int TEST_ID = 1;
    private final String TEST_PREFERRED_SPORT = "Basketball";
    private final String TEST_PREFERRED_TIME_SLOT = "Morning (08:00 - 12:00)";

    @BeforeEach
    void setUp() {
        normalUser = new NormalUser(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD, TEST_ID, TEST_PREFERRED_SPORT, TEST_PREFERRED_TIME_SLOT);
    }

    @Test
    @DisplayName("Constructor should initialize NormalUser with correct values")
    void testConstructor() {
        assertEquals(TEST_EMAIL, normalUser.getEmail());
        assertEquals(TEST_USERNAME, normalUser.getUsername());
        assertEquals(TEST_PASSWORD, normalUser.getPassword());
        assertEquals(TEST_ID, normalUser.getID());
        assertEquals(TEST_PREFERRED_SPORT, normalUser.getPreferredSport());
    }

    @Test
    @DisplayName("Constructor should handle different parameter types correctly")
    void testConstructorWithDifferentValues() {
        String email = "different@test.com";
        String username = "differentuser";
        String password = "differentpass";
        int id = 999;
        String sport = "Football";
        String time = "Afternoon (12:00 - 17:00)";

        NormalUser newUser = new NormalUser(email, username, password, id, sport, time);

        assertEquals(email, newUser.getEmail());
        assertEquals(username, newUser.getUsername());
        assertEquals(password, newUser.getPassword());
        assertEquals(id, newUser.getID());
        assertEquals(sport, newUser.getPreferredSport());
        assertEquals(time, newUser.getPreferredTimeSlot());
    }

    @Test
    @DisplayName("Constructor should handle null email")
    void testConstructorWithNullEmail() {
        NormalUser nullEmailUser = new NormalUser(null, TEST_USERNAME, TEST_PASSWORD, TEST_ID, TEST_PREFERRED_SPORT, TEST_PREFERRED_TIME_SLOT);

        assertNull(nullEmailUser.getEmail());
        assertEquals(TEST_USERNAME, nullEmailUser.getUsername());
        assertEquals(TEST_PASSWORD, nullEmailUser.getPassword());
        assertEquals(TEST_ID, nullEmailUser.getID());
        assertEquals(TEST_PREFERRED_SPORT, nullEmailUser.getPreferredSport());
        assertEquals(TEST_PREFERRED_TIME_SLOT, nullEmailUser.getPreferredTimeSlot());
    }

    @Test
    @DisplayName("Constructor should handle null username")
    void testConstructorWithNullUsername() {
        NormalUser nullUsernameUser = new NormalUser(TEST_EMAIL, null, TEST_PASSWORD, TEST_ID, TEST_PREFERRED_SPORT, TEST_PREFERRED_TIME_SLOT);

        assertEquals(TEST_EMAIL, nullUsernameUser.getEmail());
        assertNull(nullUsernameUser.getUsername());
        assertEquals(TEST_PASSWORD, nullUsernameUser.getPassword());
        assertEquals(TEST_ID, nullUsernameUser.getID());
        assertEquals(TEST_PREFERRED_SPORT, nullUsernameUser.getPreferredSport());
        assertEquals(TEST_PREFERRED_TIME_SLOT, nullUsernameUser.getPreferredTimeSlot());
    }

    @Test
    @DisplayName("Constructor should handle null password")
    void testConstructorWithNullPassword() {
        NormalUser nullPasswordUser = new NormalUser(TEST_EMAIL, TEST_USERNAME, null,  TEST_ID, TEST_PREFERRED_SPORT, TEST_PREFERRED_SPORT);

        assertEquals(TEST_EMAIL, nullPasswordUser.getEmail());
        assertEquals(TEST_USERNAME, nullPasswordUser.getUsername());
        assertNull(nullPasswordUser.getPassword());
        assertEquals(TEST_ID, nullPasswordUser.getID());
        assertEquals(TEST_PREFERRED_SPORT, nullPasswordUser.getPreferredSport());
        assertEquals(TEST_PREFERRED_SPORT, nullPasswordUser.getPreferredTimeSlot());
    }

    @Test
    @DisplayName("Constructor should handle null preferred sport")
    void testConstructorWithNullPreferredSport() {
        NormalUser nullSportUser = new NormalUser(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD, TEST_ID, null, TEST_PREFERRED_TIME_SLOT);

        assertEquals(TEST_EMAIL, nullSportUser.getEmail());
        assertEquals(TEST_USERNAME, nullSportUser.getUsername());
        assertEquals(TEST_PASSWORD, nullSportUser.getPassword());
        assertEquals(TEST_ID, nullSportUser.getID());
        assertNull(nullSportUser.getPreferredSport());
        assertEquals(TEST_PREFERRED_TIME_SLOT, nullSportUser.getPreferredTimeSlot());
    }

    @Test
    @DisplayName("Constructor should handle null preferred sport")
    void testConstructorWithNullPreferredTimeSlot() {
        NormalUser nullSportUser = new NormalUser(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD, TEST_ID, TEST_PREFERRED_SPORT, null);

        assertEquals(TEST_EMAIL, nullSportUser.getEmail());
        assertEquals(TEST_USERNAME, nullSportUser.getUsername());
        assertEquals(TEST_PASSWORD, nullSportUser.getPassword());
        assertEquals(TEST_ID, nullSportUser.getID());
        assertEquals(TEST_PREFERRED_SPORT, nullSportUser.getPreferredSport());
        assertNull(nullSportUser.getPreferredTimeSlot());
    }

    @Test
    @DisplayName("Constructor should handle negative ID")
    void testConstructorWithNegativeID() {
        int negativeId = -1;
        NormalUser negativeIdUser = new NormalUser(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD, negativeId, TEST_PREFERRED_SPORT, TEST_PREFERRED_TIME_SLOT);

        assertEquals(TEST_EMAIL, negativeIdUser.getEmail());
        assertEquals(TEST_USERNAME, negativeIdUser.getUsername());
        assertEquals(TEST_PASSWORD, negativeIdUser.getPassword());
        assertEquals(negativeId, negativeIdUser.getID());
        assertEquals(TEST_PREFERRED_SPORT, negativeIdUser.getPreferredSport());
        assertEquals(TEST_PREFERRED_TIME_SLOT, negativeIdUser.getPreferredTimeSlot());
    }

    @Test
    @DisplayName("Constructor should handle zero ID")
    void testConstructorWithZeroID() {
        int zeroId = 0;
        NormalUser zeroIdUser = new NormalUser(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD, zeroId, TEST_PREFERRED_SPORT, TEST_PREFERRED_TIME_SLOT);

        assertEquals(zeroId, zeroIdUser.getID());
    }

    // email test

    @Test
    @DisplayName("getEmail should return the correct email")
    void testGetEmail() {
        assertEquals(TEST_EMAIL, normalUser.getEmail());
    }

    @Test
    @DisplayName("setEmail should correctly update the email")
    void testSetEmail() {
        String newEmail = "newemail@example.com";
        normalUser.setEmail(newEmail);

        assertEquals(newEmail, normalUser.getEmail());
        assertNotEquals(TEST_EMAIL, normalUser.getEmail());
    }

    @Test
    @DisplayName("setEmail should handle null values")
    void testSetEmailNull() {
        normalUser.setEmail(null);
        assertNull(normalUser.getEmail());
    }

    @Test
    @DisplayName("setEmail should handle empty string")
    void testSetEmailEmpty() {
        String emptyEmail = "";
        normalUser.setEmail(emptyEmail);
        assertEquals(emptyEmail, normalUser.getEmail());
    }

    @Test
    @DisplayName("setEmail should handle complex email formats")
    void testSetEmailComplex() {
        String complexEmail = "test.user+tag@sub.domain.co.uk";
        normalUser.setEmail(complexEmail);
        assertEquals(complexEmail, normalUser.getEmail());
    }

    // username test

    @Test
    @DisplayName("getUsername should return the correct username")
    void testGetUsername() {
        assertEquals(TEST_USERNAME, normalUser.getUsername());
    }

    @Test
    @DisplayName("setUsername should correctly update the username")
    void testSetUsername() {
        String newUsername = "newusername";
        normalUser.setUsername(newUsername);

        assertEquals(newUsername, normalUser.getUsername());
        assertNotEquals(TEST_USERNAME, normalUser.getUsername());
    }

    @Test
    @DisplayName("setUsername should handle null values")
    void testSetUsernameNull() {
        normalUser.setUsername(null);
        assertNull(normalUser.getUsername());
    }

    @Test
    @DisplayName("setUsername should handle empty string")
    void testSetUsernameEmpty() {
        String emptyUsername = "";
        normalUser.setUsername(emptyUsername);
        assertEquals(emptyUsername, normalUser.getUsername());
    }

    @Test
    @DisplayName("setUsername should handle special characters")
    void testSetUsernameSpecialChars() {
        String specialUsername = "user@123_test-2024";
        normalUser.setUsername(specialUsername);
        assertEquals(specialUsername, normalUser.getUsername());
    }

    // password test

    @Test
    @DisplayName("getPassword should return the correct password")
    void testGetPassword() {
        assertEquals(TEST_PASSWORD, normalUser.getPassword());
    }

    @Test
    @DisplayName("setPassword should correctly update the password")
    void testSetPassword() {
        String newPassword = "newpassword456";
        normalUser.setPassword(newPassword);

        assertEquals(newPassword, normalUser.getPassword());
        assertNotEquals(TEST_PASSWORD, normalUser.getPassword());
    }

    @Test
    @DisplayName("setPassword should handle null values")
    void testSetPasswordNull() {
        normalUser.setPassword(null);
        assertNull(normalUser.getPassword());
    }

    @Test
    @DisplayName("setPassword should handle empty string")
    void testSetPasswordEmpty() {
        String emptyPassword = "";
        normalUser.setPassword(emptyPassword);
        assertEquals(emptyPassword, normalUser.getPassword());
    }

    @Test
    @DisplayName("setPassword should handle complex passwords")
    void testSetPasswordComplex() {
        String complexPassword = "MyStr0ng!P@ssw0rd#2024$%";
        normalUser.setPassword(complexPassword);
        assertEquals(complexPassword, normalUser.getPassword());
    }

    // ID test

    @Test
    @DisplayName("getID should return the correct ID")
    void testGetID() {
        assertEquals(TEST_ID, normalUser.getID());
    }

    @Test
    @DisplayName("setID should correctly update the ID")
    void testSetID() {
        int newID = 42;
        normalUser.setID(newID);

        assertEquals(newID, normalUser.getID());
        assertNotEquals(TEST_ID, normalUser.getID());
    }

    @Test
    @DisplayName("setID should handle zero")
    void testSetIDZero() {
        normalUser.setID(0);
        assertEquals(0, normalUser.getID());
    }

    @Test
    @DisplayName("setID should handle negative values")
    void testSetIDNegative() {
        int negativeID = -999;
        normalUser.setID(negativeID);
        assertEquals(negativeID, normalUser.getID());
    }

    @Test
    @DisplayName("setID should handle large values")
    void testSetIDLarge() {
        int largeID = Integer.MAX_VALUE;
        normalUser.setID(largeID);
        assertEquals(largeID, normalUser.getID());
    }

    // preferred sport test

    @Test
    @DisplayName("getPreferredSport should return the correct preferred sport")
    void testGetPreferredSport() {
        assertEquals(TEST_PREFERRED_SPORT, normalUser.getPreferredSport());
    }

    @Test
    @DisplayName("setPreferredSport should correctly update the preferred sport")
    void testSetPreferredSport() {
        String newSport = "Football";
        normalUser.setPreferredSport(newSport);

        assertEquals(newSport, normalUser.getPreferredSport());
        assertNotEquals(TEST_PREFERRED_SPORT, normalUser.getPreferredSport());
    }

    @Test
    @DisplayName("setPreferredSport should handle null values")
    void testSetPreferredSportNull() {
        normalUser.setPreferredSport(null);
        assertNull(normalUser.getPreferredSport());
    }

    @Test
    @DisplayName("setPreferredSport should handle empty string")
    void testSetPreferredSportEmpty() {
        String emptySport = "";
        normalUser.setPreferredSport(emptySport);
        assertEquals(emptySport, normalUser.getPreferredSport());
    }

    @Test
    @DisplayName("setPreferredSport should handle various sports")
    void testSetPreferredSportVariousSports() {
        String[] sports = {"Tennis", "Swimming", "Running", "Cycling", "Golf", "Soccer"};

        for (String sport : sports) {
            normalUser.setPreferredSport(sport);
            assertEquals(sport, normalUser.getPreferredSport());
        }
    }

    @Test
    @DisplayName("setPreferredSport should handle sports with special characters")
    void testSetPreferredSportSpecialChars() {
        String specialSport = "Mixed Martial Arts (MMA)";
        normalUser.setPreferredSport(specialSport);
        assertEquals(specialSport, normalUser.getPreferredSport());
    }

    @Test
    @DisplayName("setPreferredSport should handle sports with numbers")
    void testSetPreferredSportWithNumbers() {
        String sportWithNumbers = "Formula 1 Racing";
        normalUser.setPreferredSport(sportWithNumbers);
        assertEquals(sportWithNumbers, normalUser.getPreferredSport());
    }

    @Test
    @DisplayName("setPreferredSport should handle long sport names")
    void testSetPreferredSportLongName() {
        String longSportName = "International Association Football (Soccer) Championship League";
        normalUser.setPreferredSport(longSportName);
        assertEquals(longSportName, normalUser.getPreferredSport());
    }

    // multiple fields test

    @Test
    @DisplayName("Multiple setters should work independently")
    void testMultipleSettersIndependently() {
        String newEmail = "updated@example.com";
        String newUsername = "updateduser";
        String newPassword = "updatedpass";
        int newID = 999;
        String newSport = "Tennis";

        normalUser.setEmail(newEmail);
        assertEquals(newEmail, normalUser.getEmail());
        assertEquals(TEST_USERNAME, normalUser.getUsername());
        assertEquals(TEST_PASSWORD, normalUser.getPassword());
        assertEquals(TEST_ID, normalUser.getID());
        assertEquals(TEST_PREFERRED_SPORT, normalUser.getPreferredSport());

        normalUser.setUsername(newUsername);
        assertEquals(newEmail, normalUser.getEmail());
        assertEquals(newUsername, normalUser.getUsername());
        assertEquals(TEST_PASSWORD, normalUser.getPassword());
        assertEquals(TEST_ID, normalUser.getID());
        assertEquals(TEST_PREFERRED_SPORT, normalUser.getPreferredSport());

        normalUser.setPassword(newPassword);
        assertEquals(newEmail, normalUser.getEmail());
        assertEquals(newUsername, normalUser.getUsername());
        assertEquals(newPassword, normalUser.getPassword());
        assertEquals(TEST_ID, normalUser.getID());
        assertEquals(TEST_PREFERRED_SPORT, normalUser.getPreferredSport());

        normalUser.setID(newID);
        assertEquals(newEmail, normalUser.getEmail());
        assertEquals(newUsername, normalUser.getUsername());
        assertEquals(newPassword, normalUser.getPassword());
        assertEquals(newID, normalUser.getID());
        assertEquals(TEST_PREFERRED_SPORT, normalUser.getPreferredSport());

        normalUser.setPreferredSport(newSport);
        assertEquals(newEmail, normalUser.getEmail());
        assertEquals(newUsername, normalUser.getUsername());
        assertEquals(newPassword, normalUser.getPassword());
        assertEquals(newID, normalUser.getID());
        assertEquals(newSport, normalUser.getPreferredSport());
    }

    @Test
    @DisplayName("All setters should handle null simultaneously")
    void testAllSettersWithNull() {
        normalUser.setEmail(null);
        normalUser.setUsername(null);
        normalUser.setPassword(null);
        normalUser.setPreferredSport(null);

        assertNull(normalUser.getEmail());
        assertNull(normalUser.getUsername());
        assertNull(normalUser.getPassword());
        assertNull(normalUser.getPreferredSport());
        assertEquals(TEST_ID, normalUser.getID());
    }

}