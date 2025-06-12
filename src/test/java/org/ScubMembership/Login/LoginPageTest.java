package org.ScubMembership.Login;

import org.ScubMembership.Users.NormalUser.NormalUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class LoginPageTest {

    private LoginPage loginPage;
    private HashMap<String, NormalUser> testLoginInfo;
    private NormalUser testUser;

    @BeforeEach
    void setUp() {
        testLoginInfo = new HashMap<>();
        testUser = new NormalUser("test@example.com", "testuser", "testpass", 1, "Basketball", "testTime");
        testLoginInfo.put("testuser", testUser);

        loginPage = new LoginPage(testLoginInfo);
    }

    @Test
    @DisplayName("Constructor should initialize LoginPage with correct default values")
    void testConstructor() {
        assertNotNull(loginPage.getLoginInfo());
        assertEquals(testLoginInfo, loginPage.getLoginInfo());
        assertNotNull(loginPage.getScanner());
        assertFalse(loginPage.isLoggedIn());
        assertNull(loginPage.getCurrentUser());
    }

    @Test
    @DisplayName("Constructor should work with empty HashMap")
    void testConstructorWithEmptyHashMap() {
        HashMap<String, NormalUser> emptyMap = new HashMap<>();
        LoginPage emptyLoginPage = new LoginPage(emptyMap);

        assertNotNull(emptyLoginPage.getLoginInfo());
        assertTrue(emptyLoginPage.getLoginInfo().isEmpty());
        assertFalse(emptyLoginPage.isLoggedIn());
        assertNull(emptyLoginPage.getCurrentUser());
    }

    @Test
    @DisplayName("getLoginInfo should return the correct HashMap")
    void testGetLoginInfo() {
        HashMap<String, NormalUser> result = loginPage.getLoginInfo();

        assertNotNull(result);
        assertEquals(testLoginInfo, result);
        assertTrue(result.containsKey("testuser"));
        assertEquals(testUser, result.get("testuser"));
    }

    @Test
    @DisplayName("getLoginInfo should return reference to original HashMap (not copy)")
    void testGetLoginInfoReturnsReference() {
        HashMap<String, NormalUser> result = loginPage.getLoginInfo();

        NormalUser newUser = new NormalUser("new@example.com", "newuser", "newpass", 2, "Football", "testTime");
        result.put("newuser", newUser);

        assertTrue(loginPage.getLoginInfo().containsKey("newuser"));
    }

    @Test
    @DisplayName("isLoggedIn should return false by default")
    void testIsLoggedInDefault() {
        assertFalse(loginPage.isLoggedIn());
    }

    @Test
    @DisplayName("setLoggedIn should correctly set logged in status to true")
    void testSetLoggedInTrue() {
        loginPage.setLoggedIn(true);
        assertTrue(loginPage.isLoggedIn());
    }

    @Test
    @DisplayName("setLoggedIn should correctly set logged in status to false")
    void testSetLoggedInFalse() {
        loginPage.setLoggedIn(true);
        assertTrue(loginPage.isLoggedIn());

        loginPage.setLoggedIn(false);
        assertFalse(loginPage.isLoggedIn());
    }

    @Test
    @DisplayName("setLoggedIn should handle multiple state changes")
    void testSetLoggedInMultipleChanges() {
        assertFalse(loginPage.isLoggedIn());

        loginPage.setLoggedIn(true);
        assertTrue(loginPage.isLoggedIn());

        loginPage.setLoggedIn(false);
        assertFalse(loginPage.isLoggedIn());

        loginPage.setLoggedIn(true);
        assertTrue(loginPage.isLoggedIn());
    }

    @Test
    @DisplayName("getScanner should return a non-null Scanner instance")
    void testGetScanner() {
        Scanner scanner = loginPage.getScanner();
        assertNotNull(scanner);
        assertInstanceOf(Scanner.class, scanner);
    }

    @Test
    @DisplayName("getScanner should return the same Scanner instance on multiple calls")
    void testGetScannerConsistency() {
        Scanner scanner1 = loginPage.getScanner();
        Scanner scanner2 = loginPage.getScanner();

        assertSame(scanner1, scanner2);
    }

    @Test
    @DisplayName("getCurrentUser should return null by default")
    void testGetCurrentUserDefault() {
        assertNull(loginPage.getCurrentUser());
    }

    @Test
    @DisplayName("setCurrentUser should correctly set the current user")
    void testSetCurrentUser() {
        loginPage.setCurrentUser(testUser);

        assertNotNull(loginPage.getCurrentUser());
        assertEquals(testUser, loginPage.getCurrentUser());
        assertSame(testUser, loginPage.getCurrentUser());
    }

    @Test
    @DisplayName("setCurrentUser should handle null values")
    void testSetCurrentUserNull() {
        loginPage.setCurrentUser(testUser);
        assertNotNull(loginPage.getCurrentUser());

        loginPage.setCurrentUser(null);
        assertNull(loginPage.getCurrentUser());
    }

    @Test
    @DisplayName("setCurrentUser should handle user changes")
    void testSetCurrentUserChange() {
        loginPage.setCurrentUser(testUser);
        assertEquals(testUser, loginPage.getCurrentUser());

        NormalUser secondUser = new NormalUser("second@example.com", "seconduser", "secondpass", 2, "Tennis", "secondTest");
        loginPage.setCurrentUser(secondUser);

        assertEquals(secondUser, loginPage.getCurrentUser());
        assertNotEquals(testUser, loginPage.getCurrentUser());
    }

    @Test
    @DisplayName("Typical login workflow should work correctly")
    void testTypicalLoginWorkflow() {
        assertFalse(loginPage.isLoggedIn());
        assertNull(loginPage.getCurrentUser());

        loginPage.setLoggedIn(true);
        loginPage.setCurrentUser(testUser);

        assertTrue(loginPage.isLoggedIn());
        assertEquals(testUser, loginPage.getCurrentUser());

        loginPage.setLoggedIn(false);
        loginPage.setCurrentUser(null);

        assertFalse(loginPage.isLoggedIn());
        assertNull(loginPage.getCurrentUser());
    }

    @Test
    @DisplayName("State should be independent - changing one field shouldn't affect others")
    void testStateIndependence() {
        loginPage.setLoggedIn(true);
        assertTrue(loginPage.isLoggedIn());
        assertNull(loginPage.getCurrentUser());

        loginPage.setCurrentUser(testUser);
        assertEquals(testUser, loginPage.getCurrentUser());
        assertTrue(loginPage.isLoggedIn());

        loginPage.setLoggedIn(false);
        assertFalse(loginPage.isLoggedIn());
        assertEquals(testUser, loginPage.getCurrentUser());
    }
}
