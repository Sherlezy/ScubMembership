package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.PasswordSecurity.PasswordEncryptor;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.ScubMembership.Utils.UserStorage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginAttemptTest {

    @Mock
    private LoginPage loginPage;

    @Mock
    private NormalUser mockUser1;

    @Mock
    private NormalUser mockUser2;

    private LoginAttempt loginAttempt;
    private HashMap<String, NormalUser> loginInfo;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        loginInfo = new HashMap<>();

        when(mockUser1.getPassword()).thenReturn("password123");
        when(mockUser2.getPassword()).thenReturn("secret456");

        loginInfo.put("testuser", mockUser1);
        loginInfo.put("admin", mockUser2);

        when(loginPage.getLoginInfo()).thenReturn(loginInfo);

        loginAttempt = new LoginAttempt(loginPage);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testSuccessfulLogin() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine())
                .thenReturn("admin")
                .thenReturn("secret456");

        when(loginPage.getScanner()).thenReturn(mockScanner);

        try (MockedStatic<PasswordEncryptor> mockedEncryptor = mockStatic(PasswordEncryptor.class);
             MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedEncryptor.when(() -> PasswordEncryptor.hashPassword("secret456"))
                    .thenReturn("secret456");
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage).setCurrentUser(mockUser2);
            verify(loginPage).setLoggedIn(true);
        }
    }

    @Test
    void testSuccessfulLoginSecondUser() {
        String username = "admin";
        String password = "secret456";
        String input = username + "\n" + password + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);
        when(mockUser2.getUsername()).thenReturn("admin");

        try (MockedStatic<PasswordEncryptor> mockedEncryptor = mockStatic(PasswordEncryptor.class);
             MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedEncryptor.when(() -> PasswordEncryptor.hashPassword(password))
                    .thenReturn(password);
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage).setLoggedIn(true);
            verify(loginPage).setCurrentUser(mockUser2);

            String output = outputStream.toString();
            assertTrue(output.contains("✅ Login successful! Welcome, " + username + "!"));
        }
    }

    @Test
    void testIncorrectPassword() {
        String username = "testuser";
        String wrongPassword = "wrongpassword";
        String input = username + "\n" + wrongPassword + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage, never()).setLoggedIn(true);
            verify(loginPage, never()).setCurrentUser(any());

            String output = outputStream.toString();
            assertTrue(output.contains("Incorrect password. Please try again."));
        }
    }

    @Test
    void testUsernameNotFound() {
        String nonExistentUsername = "nonexistent";
        String password = "anypassword";
        String input = nonExistentUsername + "\n" + password + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage, never()).setLoggedIn(true);
            verify(loginPage, never()).setCurrentUser(any());

            String output = outputStream.toString();
            assertTrue(output.contains("Username not found. Please try again."));
        }
    }

    @Test
    void testEmptyUsername() {
        String emptyUsername = "";
        String password = "password123";
        String input = emptyUsername + "\n" + password + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage, never()).setLoggedIn(true);
            verify(loginPage, never()).setCurrentUser(any());

            String output = outputStream.toString();
            assertTrue(output.contains("Username not found. Please try again."));
        }
    }

    @Test
    void testEmptyPassword() {
        String username = "testuser";
        String emptyPassword = "";
        String input = username + "\n" + emptyPassword + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage, never()).setLoggedIn(true);
            verify(loginPage, never()).setCurrentUser(any());

            String output = outputStream.toString();
            assertTrue(output.contains("Incorrect password. Please try again."));
        }
    }

    @Test
    void testWhitespaceUsername() {
        String whitespaceUsername = "   ";
        String password = "password123";
        String input = whitespaceUsername + "\n" + password + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage, never()).setLoggedIn(true);
            verify(loginPage, never()).setCurrentUser(any());

            String output = outputStream.toString();
            assertTrue(output.contains("Username not found. Please try again."));
        }
    }

    @Test
    void testWhitespacePassword() {
        String username = "testuser";
        String whitespacePassword = "   ";
        String input = username + "\n" + whitespacePassword + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage, never()).setLoggedIn(true);
            verify(loginPage, never()).setCurrentUser(any());

            String output = outputStream.toString();
            assertTrue(output.contains("Incorrect password. Please try again."));
        }
    }

    @Test
    void testCaseSensitiveUsername() {
        String uppercaseUsername = "TESTUSER";
        String password = "password123";
        String input = uppercaseUsername + "\n" + password + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage, never()).setLoggedIn(true);
            verify(loginPage, never()).setCurrentUser(any());

            String output = outputStream.toString();
            assertTrue(output.contains("Username not found. Please try again."));
        }
    }

    @Test
    void testCaseSensitivePassword() {
        String username = "testuser";
        String uppercasePassword = "PASSWORD123";
        String input = username + "\n" + uppercasePassword + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage, never()).setLoggedIn(true);
            verify(loginPage, never()).setCurrentUser(any());

            String output = outputStream.toString();
            assertTrue(output.contains("Incorrect password. Please try again."));
        }
    }

    @Test
    void testSpecialCharactersInCredentials() {
        String specialUsername = "user@#$%";
        String specialPassword = "pass!@#$%^&*()";

        NormalUser specialUser = mock(NormalUser.class);
        when(specialUser.getPassword()).thenReturn(specialPassword);
        when(specialUser.getUsername()).thenReturn(specialUsername);
        loginInfo.put(specialUsername, specialUser);

        String input = specialUsername + "\n" + specialPassword + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<PasswordEncryptor> mockedEncryptor = mockStatic(PasswordEncryptor.class);
             MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedEncryptor.when(() -> PasswordEncryptor.hashPassword(specialPassword))
                    .thenReturn(specialPassword);
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage).setLoggedIn(true);
            verify(loginPage).setCurrentUser(specialUser);

            String output = outputStream.toString();
            assertTrue(output.contains("✅ Login successful! Welcome, " + specialUsername + "!"));
        }
    }

    @Test
    void testVeryLongCredentials() {
        String longUsername = "a".repeat(1000);
        String longPassword = "b".repeat(1000);

        NormalUser longUser = mock(NormalUser.class);
        when(longUser.getPassword()).thenReturn(longPassword);
        when(longUser.getUsername()).thenReturn(longUsername);
        loginInfo.put(longUsername, longUser);

        String input = longUsername + "\n" + longPassword + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<PasswordEncryptor> mockedEncryptor = mockStatic(PasswordEncryptor.class);
             MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedEncryptor.when(() -> PasswordEncryptor.hashPassword(longPassword))
                    .thenReturn(longPassword);
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage).setLoggedIn(true);
            verify(loginPage).setCurrentUser(longUser);

            String output = outputStream.toString();
            assertTrue(output.contains("✅ Login successful! Welcome, " + longUsername + "!"));
        }
    }

    @Test
    void testNullPasswordFromUser() {
        String username = "testuser";
        String input = username + "\n"; // No password input

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            assertThrows(java.util.NoSuchElementException.class, () -> {
                loginAttempt.startLoginAttempt();
            });
        }
    }

    @Test
    void testEmptyLoginInfo() {
        HashMap<String, NormalUser> emptyLoginInfo = new HashMap<>();
        when(loginPage.getLoginInfo()).thenReturn(emptyLoginInfo);

        String username = "anyuser";
        String password = "anypassword";
        String input = username + "\n" + password + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        loginAttempt.startLoginAttempt();

        verify(loginPage, never()).setLoggedIn(true);
        verify(loginPage, never()).setCurrentUser(any());

        String output = outputStream.toString();
        assertTrue(output.contains("Username not found. Please try again."));
    }

    @Test
    void testNullLoginInfo() {
        when(loginPage.getLoginInfo()).thenReturn(null);

        String username = "anyuser";
        String password = "anypassword";
        String input = username + "\n" + password + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(null);
            assertThrows(NullPointerException.class, () -> {
                loginAttempt.startLoginAttempt();
            });
        }
    }

    @Test
    void testNullScanner() {
        when(loginPage.getScanner()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            loginAttempt.startLoginAttempt();
        });
    }

    @Test
    void testConstructorWithNullLoginPage() {
        assertDoesNotThrow(() -> {
            new LoginAttempt(null);
        });
    }

    @Test
    void testPromptMessages() {
        String username = "testuser";
        String password = "password123";
        String input = username + "\n" + password + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        loginAttempt.startLoginAttempt();

        String output = outputStream.toString();
        assertTrue(output.contains("Username: "));
        assertTrue(output.contains("Password: "));
    }

    @Test
    void testSuccessfulLoginSetsCorrectUser() {
        // Prepare a fresh loginInfo with a specific user
        HashMap<String, NormalUser> freshLoginInfo = new HashMap<>();
        NormalUser user = mock(NormalUser.class);
        when(user.getUsername()).thenReturn("Vez");
        when(user.getPassword()).thenReturn("vezpass");
        freshLoginInfo.put("Vez", user);

        String username = "Vez";
        String password = "vezpass";
        String input = username + "\n" + password + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<PasswordEncryptor> mockedEncryptor = mockStatic(PasswordEncryptor.class);
             MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedEncryptor.when(() -> PasswordEncryptor.hashPassword(password)).thenReturn(password);
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(freshLoginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage).setCurrentUser(user);
            verify(loginPage).setLoggedIn(true);
        }
    }

    @Test
    void testMultipleUsersWithSamePassword() {
        when(mockUser1.getPassword()).thenReturn("samepassword");
        when(mockUser2.getPassword()).thenReturn("samepassword");
        when(mockUser1.getUsername()).thenReturn("testuser");
        when(mockUser2.getUsername()).thenReturn("admin");

        String username = "testuser";
        String password = "samepassword";
        String input = username + "\n" + password + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);

        try (MockedStatic<PasswordEncryptor> mockedEncryptor = mockStatic(PasswordEncryptor.class);
             MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            mockedEncryptor.when(() -> PasswordEncryptor.hashPassword(password))
                    .thenReturn(password);
            mockedUserStorage.when(UserStorage::loadUsers).thenReturn(loginInfo);

            loginAttempt.startLoginAttempt();

            verify(loginPage).setLoggedIn(true);
            verify(loginPage).setCurrentUser(mockUser1);
            verify(loginPage, never()).setCurrentUser(mockUser2);

            String output = outputStream.toString();
            assertTrue(output.contains("✅ Login successful! Welcome, " + username + "!"));
        }
    }
}