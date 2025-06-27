package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.Users.Admin.Admin;
import org.ScubMembership.Utils.AdminStorage;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowAllAdminsTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUpStreams() {
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testExecute_noAdmins() {
        try (MockedStatic<AdminStorage> mock = mockStatic(AdminStorage.class)) {
            mock.when(AdminStorage::loadAdmins).thenReturn(new HashMap<>());

            ShowAllAdmins.execute();

            String output = outContent.toString().trim();
            assertTrue(output.contains("📭 No admins found."));
        }
    }

    @Test
    void testExecute_withAdmins() {
        Admin mockAdmin = mock(Admin.class);
        when(mockAdmin.getEmail()).thenReturn("admin@example.com");

        HashMap<String, Admin> mockAdmins = new HashMap<>();
        mockAdmins.put("admin1", mockAdmin);

        try (MockedStatic<AdminStorage> mock = mockStatic(AdminStorage.class)) {
            mock.when(AdminStorage::loadAdmins).thenReturn(mockAdmins);

            ShowAllAdmins.execute();

            String output = outContent.toString().trim();
            assertTrue(output.contains("👑 Registered Admins:"));
            assertTrue(output.contains("- admin1 (admin@example.com)"));
        }
    }
}
