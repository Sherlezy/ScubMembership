package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.PasswordSecurity.PasswordEncryptor;
import org.ScubMembership.Users.NormalUser.NormalUser;
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
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChangePasswordTest {

    @Mock
    private LoginPage loginPage;

    @Mock
    private NormalUser currentUser;

    private ChangePassword changePassword;
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
        loginInfo.put("testuser", currentUser);

        when(loginPage.getLoginInfo()).thenReturn(loginInfo);
        when(loginPage.getCurrentUser()).thenReturn(currentUser);

        changePassword = new ChangePassword(loginPage);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testSuccessfulPasswordChange() {

        String currentPassword = "oldPassword123";
        String newPassword = "newPassword456";
        String confirmPassword = "newPassword456";
        String hashedCurrentPassword = PasswordEncryptor.hashPassword(currentPassword);
        String hashedNewPassword = PasswordEncryptor.hashPassword(newPassword);

        String input = currentPassword + "\n" + newPassword + "\n" + confirmPassword + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(loginPage.getScanner()).thenReturn(scanner);
        when(currentUser.getPassword()).thenReturn(hashedCurrentPassword);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            changePassword.startChangePassword();

            verify(currentUser).setPassword(hashedNewPassword);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(loginInfo));
            assertTrue(outputStream.toString().contains("Password changed successfully!"));
        }
    }

    @Test
    void testIncorrectCurrentPassword() {

        String currentPassword = "oldPassword123";
        String wrongCurrentPassword = "wrongPassword";
        String newPassword = "newPassword456";
        String confirmPassword = "newPassword456";
        String hashedCurrentPassword = PasswordEncryptor.hashPassword(currentPassword);

        String input = wrongCurrentPassword + "\n" + newPassword + "\n" + confirmPassword + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(loginPage.getScanner()).thenReturn(scanner);
        when(currentUser.getPassword()).thenReturn(hashedCurrentPassword);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            changePassword.startChangePassword();

            verify(currentUser, never()).setPassword(anyString());
            mockedUserStorage.verify(() -> UserStorage.saveUsers(any()), never());
            assertTrue(outputStream.toString().contains("Incorrect current password. No changes made."));
        }
    }

    @Test
    void testPasswordConfirmationMismatch() {

        String currentPassword = "oldPassword123";
        String newPassword = "newPassword456";
        String wrongConfirmPassword = "differentPassword789";
        String hashedCurrentPassword = PasswordEncryptor.hashPassword(currentPassword);

        String input = currentPassword + "\n" + newPassword + "\n" + wrongConfirmPassword + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(loginPage.getScanner()).thenReturn(scanner);
        when(currentUser.getPassword()).thenReturn(hashedCurrentPassword);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            changePassword.startChangePassword();

            verify(currentUser, never()).setPassword(anyString());
            mockedUserStorage.verify(() -> UserStorage.saveUsers(any()), never());
            assertTrue(outputStream.toString().contains("Passwords do not match. No changes made."));
        }
    }

    @Test
    void testEmptyCurrentPassword() {

        String currentPassword = "oldPassword123";
        String emptyCurrentPassword = "";
        String newPassword = "newPassword456";
        String confirmPassword = "newPassword456";
        String hashedCurrentPassword = PasswordEncryptor.hashPassword(currentPassword);

        String input = emptyCurrentPassword + "\n" + newPassword + "\n" + confirmPassword + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(loginPage.getScanner()).thenReturn(scanner);
        when(currentUser.getPassword()).thenReturn(hashedCurrentPassword);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            changePassword.startChangePassword();

            verify(currentUser, never()).setPassword(anyString());
            mockedUserStorage.verify(() -> UserStorage.saveUsers(any()), never());
            assertTrue(outputStream.toString().contains("Incorrect current password. No changes made."));
        }
    }

    @Test
    void testEmptyNewPassword() {
        String currentPassword = "oldPassword123";
        String emptyNewPassword = "";
        String confirmPassword = "";
        String hashedCurrentPassword = PasswordEncryptor.hashPassword(currentPassword);

        String input = currentPassword + "\n" + emptyNewPassword + "\n" + confirmPassword + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(loginPage.getScanner()).thenReturn(scanner);
        when(currentUser.getPassword()).thenReturn(hashedCurrentPassword);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            changePassword.startChangePassword();

            verify(currentUser, never()).setPassword(anyString());
            mockedUserStorage.verify(() -> UserStorage.saveUsers(any()), never());
            assertTrue(outputStream.toString().contains("Password cannot be empty or whitespace. No changes made."));
        }
    }

    @Test
    void testWhitespacePasswords() {
        String currentPassword = "oldPassword123";
        String newPassword = "  ";
        String confirmPassword = "  ";
        String hashedCurrentPassword = PasswordEncryptor.hashPassword(currentPassword);

        String input = currentPassword + "\n" + newPassword + "\n" + confirmPassword + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(loginPage.getScanner()).thenReturn(scanner);
        when(currentUser.getPassword()).thenReturn(hashedCurrentPassword);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            changePassword.startChangePassword();

            verify(currentUser, never()).setPassword(anyString());
            mockedUserStorage.verify(() -> UserStorage.saveUsers(any()), never());
            assertTrue(outputStream.toString().contains("Password cannot be empty or whitespace. No changes made."));
        }
    }

    @Test
    void testSpecialCharactersInPassword() {

        String currentPassword = "old@#$%^&*()";
        String newPassword = "new!@#$%^&*()_+";
        String confirmPassword = "new!@#$%^&*()_+";
        String hashedCurrentPassword = PasswordEncryptor.hashPassword(currentPassword);
        String hashedNewPassword = PasswordEncryptor.hashPassword(newPassword);

        String input = currentPassword + "\n" + newPassword + "\n" + confirmPassword + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(loginPage.getScanner()).thenReturn(scanner);
        when(currentUser.getPassword()).thenReturn(hashedCurrentPassword);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            changePassword.startChangePassword();

            verify(currentUser).setPassword(hashedNewPassword);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(loginInfo));
            assertTrue(outputStream.toString().contains("Password changed successfully!"));
        }
    }

    @Test
    void testVeryLongPasswords() {

        String currentPassword = "a".repeat(1000);
        String newPassword = "b".repeat(1000);
        String confirmPassword = "b".repeat(1000);
        String hashedCurrentPassword = PasswordEncryptor.hashPassword(currentPassword);
        String hashedNewPassword = PasswordEncryptor.hashPassword(newPassword);

        String input = currentPassword + "\n" + newPassword + "\n" + confirmPassword + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(loginPage.getScanner()).thenReturn(scanner);
        when(currentUser.getPassword()).thenReturn(hashedCurrentPassword);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            changePassword.startChangePassword();

            verify(currentUser).setPassword(hashedNewPassword);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(loginInfo));
            assertTrue(outputStream.toString().contains("Password changed successfully!"));
        }
    }

    @Test
    void testSamePasswordAsOld() {

        String currentPassword = "samePassword123";
        String newPassword = "samePassword123";
        String confirmPassword = "samePassword123";
        String hashedCurrentPassword = PasswordEncryptor.hashPassword(currentPassword);
        String hashedNewPassword = PasswordEncryptor.hashPassword(newPassword);

        String input = currentPassword + "\n" + newPassword + "\n" + confirmPassword + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(loginPage.getScanner()).thenReturn(scanner);
        when(currentUser.getPassword()).thenReturn(hashedCurrentPassword);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            changePassword.startChangePassword();

            verify(currentUser).setPassword(hashedNewPassword);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(loginInfo));
            assertTrue(outputStream.toString().contains("Password changed successfully!"));
        }
    }

    @Test
    void testNullCurrentUser() {

        when(loginPage.getCurrentUser()).thenReturn(null);

        Scanner scanner = new Scanner(new ByteArrayInputStream("anything\n".getBytes()));
        when(loginPage.getScanner()).thenReturn(scanner);


        assertThrows(NullPointerException.class, () -> {
            changePassword.startChangePassword();
        });
    }

    @Test
    void testConstructorWithNullLoginPage() {

        assertDoesNotThrow(() -> {
            new ChangePassword(null);
        });
    }

    @Test
    void testPromptMessages() {
        String currentPassword = "oldPassword123";
        String newPassword = "newPassword456";
        String confirmPassword = "newPassword456";
        String hashedCurrentPassword = PasswordEncryptor.hashPassword(currentPassword);

        String input = currentPassword + "\n" + newPassword + "\n" + confirmPassword + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        when(loginPage.getScanner()).thenReturn(scanner);
        when(currentUser.getPassword()).thenReturn(hashedCurrentPassword);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class)) {
            changePassword.startChangePassword();

            String output = outputStream.toString();
            assertTrue(output.contains("Enter current password: "));
            assertTrue(output.contains("Enter new password: "));
            assertTrue(output.contains("Confirm new password: "));
            assertTrue(output.contains("✅ Password changed successfully!"));
        }
    }
}