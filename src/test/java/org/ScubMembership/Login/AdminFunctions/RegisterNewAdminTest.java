package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.Users.Admin.Admin;
import org.ScubMembership.Utils.AdminStorage;
import org.ScubMembership.PasswordSecurity.PasswordEncryptor;
import org.ScubMembership.PasswordSecurity.PasswordValidator;
import org.ScubMembership.Utils.EmailValidator;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterNewAdminTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setupStreams() {
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testSuccessfulAdminRegistration() {
        // Simulated user input:
        // - username: newadmin
        // - password: Valid1!
        // - email: admin@example.com
        String input = String.join(System.lineSeparator(),
                "newadmin",
                "Valid1!",
                "admin@example.com"
        ) + System.lineSeparator();

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Mock dependencies
        try (
                MockedStatic<AdminStorage> adminStorageMock = mockStatic(AdminStorage.class);
                MockedStatic<PasswordValidator> passwordValidatorMock = mockStatic(PasswordValidator.class);
                MockedStatic<EmailValidator> emailValidatorMock = mockStatic(EmailValidator.class);
                MockedStatic<PasswordEncryptor> passwordEncryptorMock = mockStatic(PasswordEncryptor.class)
        ) {
            HashMap<String, Admin> adminMap = new HashMap<>();
            adminStorageMock.when(AdminStorage::loadAdmins).thenReturn(adminMap);
            adminStorageMock.when(() -> AdminStorage.saveAdmins(any())).thenAnswer(invocation -> {
                HashMap<String, Admin> savedMap = invocation.getArgument(0);
                assertTrue(savedMap.containsKey("newadmin"));
                return null;
            });

            passwordValidatorMock.when(() -> PasswordValidator.isStrongPassword("Valid1!")).thenReturn(true);
            emailValidatorMock.when(() -> EmailValidator.isValidEmail("admin@example.com")).thenReturn(true);
            passwordEncryptorMock.when(() -> PasswordEncryptor.hashPassword("Valid1!")).thenReturn("hashed_password");

            RegisterNewAdmin.execute(scanner);

            String output = outContent.toString().trim();
            assertTrue(output.contains("✅ New admin added successfully."));
        }
    }

    @Test
    void testUsernameAlreadyExists() {
        String input = String.join(System.lineSeparator(),
                "admin1",     // already exists
                "admin2",     // new
                "Valid1!",
                "admin@example.com"
        ) + System.lineSeparator();

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        try (
                MockedStatic<AdminStorage> adminStorageMock = mockStatic(AdminStorage.class);
                MockedStatic<PasswordValidator> passwordValidatorMock = mockStatic(PasswordValidator.class);
                MockedStatic<EmailValidator> emailValidatorMock = mockStatic(EmailValidator.class);
                MockedStatic<PasswordEncryptor> passwordEncryptorMock = mockStatic(PasswordEncryptor.class)
        ) {
            HashMap<String, Admin> adminMap = new HashMap<>();
            adminMap.put("admin1", mock(Admin.class)); // already taken
            adminStorageMock.when(AdminStorage::loadAdmins).thenReturn(adminMap);
            adminStorageMock.when(() -> AdminStorage.saveAdmins(any())).thenAnswer(invocation -> null);

            passwordValidatorMock.when(() -> PasswordValidator.isStrongPassword("Valid1!")).thenReturn(true);
            emailValidatorMock.when(() -> EmailValidator.isValidEmail("admin@example.com")).thenReturn(true);
            passwordEncryptorMock.when(() -> PasswordEncryptor.hashPassword("Valid1!")).thenReturn("hashed");

            RegisterNewAdmin.execute(scanner);

            String output = outContent.toString();
            assertTrue(output.contains("❌ Username already exists"));
            assertTrue(output.contains("✅ New admin added successfully"));
        }
    }
}
