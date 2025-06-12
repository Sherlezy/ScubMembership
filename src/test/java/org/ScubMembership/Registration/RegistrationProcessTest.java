package org.ScubMembership.Registration;

import org.ScubMembership.Login.LoginFunctions.LoginMenu;
import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.EmailValidator;
import org.ScubMembership.Utils.UserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistrationProcessTest {



    @Mock
    private LoginPage mockLoginPage;

    @Mock
    private LoginMenu mockLoginMenu;

    private RegistrationProcess registrationProcess;
    private Map<String, NormalUser> loginInfo;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private MockedStatic<EmailValidator> emailValidatorMockedStatic;
    private MockedStatic<UserStorage> userStorageMockedStatic;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        loginInfo = new HashMap<>();
        when(mockLoginPage.getLoginInfo()).thenReturn((HashMap<String, NormalUser>) loginInfo);

        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        emailValidatorMockedStatic = mockStatic(EmailValidator.class);
        userStorageMockedStatic = mockStatic(UserStorage.class);
    }

    @AfterEach
    void tearDown(){
        System.setOut(originalOut);
        if (emailValidatorMockedStatic != null) emailValidatorMockedStatic.close();
        if (userStorageMockedStatic != null) userStorageMockedStatic.close();
    }

    @Test
    void testSuccessfulRegistration() {
        String input = "test@example.com\ntestuser\nTestPass123!\n1\n3\n2";
        Scanner mockScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(mockLoginPage.getScanner()).thenReturn(mockScanner);

        emailValidatorMockedStatic.when(() -> EmailValidator.isValidEmail("test@example.com"))
                .thenReturn(true);

        registrationProcess = new RegistrationProcess(mockLoginPage);

        registrationProcess.startRegistration();

        assertEquals(1, loginInfo.size());
        assertTrue(loginInfo.containsKey("testuser"));

        NormalUser registeredUser = loginInfo.get("testuser");
        assertEquals("test@example.com", registeredUser.getEmail());
        assertEquals("testuser", registeredUser.getUsername());
        assertEquals(1, registeredUser.getID());
        assertEquals("Tennis", registeredUser.getPreferredSport());

        assertEquals(1, registrationProcess.getRegisteredUsers().size());

        verify(mockLoginPage).setCurrentUser(registeredUser);
        verify(mockLoginPage).setLoggedIn(true);
        userStorageMockedStatic.verify(() -> UserStorage.saveUsers((HashMap<String, NormalUser>) loginInfo));

        String output = outputStream.toString();
        assertTrue(output.contains("User registered successfully!"));
    }

    @Test
    void testRegistrationWithInvalidEmailThenValid(){
        String input = "invalid-email\nvalid@example.com\ntestuser\nTestPass123!\n2\n3\n2";
        Scanner mockScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(mockLoginPage.getScanner()).thenReturn(mockScanner);

        emailValidatorMockedStatic.when(() -> EmailValidator.isValidEmail("invalid-email"))
                .thenReturn(false);
        emailValidatorMockedStatic.when(() -> EmailValidator.isValidEmail("valid@example.com"))
                .thenReturn(true);

        registrationProcess = new RegistrationProcess(mockLoginPage);

        registrationProcess.startRegistration();

        assertEquals(1, loginInfo.size());
        NormalUser registeredUser = loginInfo.get("testuser");
        assertEquals("valid@example.com", registeredUser.getEmail());

        String output = outputStream.toString();
        assertTrue(output.contains("Your E-Mail address is invalid."));
        assertTrue(output.contains("User registered successfully!"));
    }

    @Test
    void testRegistrationWithDuplicateEmail(){
        NormalUser existingUser = new NormalUser("existing@example.com", "existinguser", "pass", 1, "Basketball", "Morning (08:00 - 12:00)");
        loginInfo.put("existinguser", existingUser);

        String input = "existing@example.com\nnew@example.com\nnewuser\nTestPass123!\n3\n3\n2";
        Scanner mockScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(mockLoginPage.getScanner()).thenReturn(mockScanner);

        emailValidatorMockedStatic.when(() -> EmailValidator.isValidEmail(anyString()))
                .thenReturn(true);

        registrationProcess = new RegistrationProcess(mockLoginPage);

        registrationProcess.startRegistration();

        assertEquals(2, loginInfo.size());
        assertTrue(loginInfo.containsKey("newuser"));

        NormalUser newUser = loginInfo.get("newuser");
        assertEquals("new@example.com", newUser.getEmail());
        assertEquals(2, loginInfo.size());

        String output = outputStream.toString();
        assertTrue(output.contains("An account with this email already exists"));
    }

    @Test
    void testRegistrationWithDuplicateUsername(){
        NormalUser existingUser = new NormalUser("duplicate@example.com", "duplicateuser", "duplicatepass", 1, "Basketball", "Evening (17:00 - 21:00)" );
        loginInfo.put("duplicateuser", existingUser);

        String input = "new@example.com\nduplicateuser\nnewuser\nTestPass123!\n4\n3\n2";
        Scanner mockScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(mockLoginPage.getScanner()).thenReturn(mockScanner);

        emailValidatorMockedStatic.when(() -> EmailValidator.isValidEmail("new@example.com"))
                .thenReturn(true);

        registrationProcess = new RegistrationProcess(mockLoginPage);
        registrationProcess.startRegistration();

        assertEquals(2, loginInfo.size());
        assertTrue(loginInfo.containsKey("newuser"));

        String output = outputStream.toString();
        assertTrue(output.contains("Username already exists. Please choose a different one"));
    }

    @Test
    void testRegistrationWithCaseInsensitiveEmailDuplicate(){
        NormalUser existingUser = new NormalUser("EXISTING@EXAMPLE.COM", "existinguser", "pass", 1, "Basketball", "Afternoon (12:00 - 17:00)");
        loginInfo.put("existinguser", existingUser);

        String input = "existing@example.com\nnew@example.com\nnewuser\nTestPass123!\n5\n3\n2";
        Scanner mockScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(mockLoginPage.getScanner()).thenReturn(mockScanner);

        emailValidatorMockedStatic.when(() -> EmailValidator.isValidEmail(anyString()))
                .thenReturn(true);

        RegistrationProcess registrationProcess = new RegistrationProcess(mockLoginPage);
        registrationProcess.startRegistration();

        assertEquals(2, loginInfo.size());

        NormalUser newUser = loginInfo.get("newuser");
        assertEquals("new@example.com", newUser.getEmail());

        String output = outputStream.toString();
        assertTrue(output.contains("An account with this email already exists."));
    }

    @Test
    void testRegistrationWithEmptyInputHandling() {
        String input = "   valid@example.com   \n   testuser   \nTestPass123!\n6\n3\n2";
        Scanner mockScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(mockLoginPage.getScanner()).thenReturn(mockScanner);

        emailValidatorMockedStatic.when(() -> EmailValidator.isValidEmail("valid@example.com"))
                .thenReturn(true);

        registrationProcess = new RegistrationProcess(mockLoginPage);

        registrationProcess.startRegistration();

        NormalUser registeredUser = loginInfo.get("testuser");
        assertEquals("valid@example.com", registeredUser.getEmail());
        assertEquals("testuser", registeredUser.getUsername());
    }

    @Test
    void testRegistrationProcessInitialization(){
        registrationProcess = new RegistrationProcess(mockLoginPage);

        assertNotNull(registrationProcess.getRegisteredUsers());
        assertTrue(registrationProcess.getRegisteredUsers().isEmpty());
    }

    @Test
    void testMultipleInvalidEmailsBeforeValid() {
        String input = "bad1\nbad2\nbad3\ngood@example.com\ntester\nTestPass123!\n7\n3\n2";
        Scanner mockScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(mockLoginPage.getScanner()).thenReturn(mockScanner);

        emailValidatorMockedStatic.when(() -> EmailValidator.isValidEmail("bad1"))
                .thenReturn(false);
        emailValidatorMockedStatic.when(() -> EmailValidator.isValidEmail("bad2"))
                .thenReturn(false);
        emailValidatorMockedStatic.when(() -> EmailValidator.isValidEmail("bad3"))
                .thenReturn(false);
        emailValidatorMockedStatic.when(() -> EmailValidator.isValidEmail("good@example.com"))
                .thenReturn(true);

        registrationProcess = new RegistrationProcess(mockLoginPage);

        registrationProcess.startRegistration();

        assertEquals(1, loginInfo.size());
        NormalUser user = loginInfo.get("tester");
        assertEquals("good@example.com", user.getEmail());

        String output = outputStream.toString();
        assertEquals(3, output.split("Your E-Mail address is invalid\\.").length - 1);
    }

    @Test
    void testMultipleDuplicateUsernamesBeforeUnique() {
        loginInfo.put("taken1", new NormalUser("email1@example.com", "taken1", "pass", 1, "Sport", "Evening (17:00 - 21:00)"));
        loginInfo.put("taken2", new NormalUser("email2@example.com", "taken2", "pass", 2, "Sport", "Evening (17:00 - 21:00)"));

        String input = "new@example.com\ntaken1\ntaken2\navailable\nTestPass123!\n8\n3\n2";
        Scanner mockScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(mockLoginPage.getScanner()).thenReturn(mockScanner);

        emailValidatorMockedStatic.when(() -> EmailValidator.isValidEmail("new@example.com"))
                .thenReturn(true);

        registrationProcess = new RegistrationProcess(mockLoginPage);

        registrationProcess.startRegistration();

        assertEquals(3, loginInfo.size());
        assertTrue(loginInfo.containsKey("available"));

        String output = outputStream.toString();
        assertEquals(2, output.split("Username already exists\\.").length - 1);
    }
}
