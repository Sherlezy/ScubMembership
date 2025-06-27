package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowAllNormalUsersTest {

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
    void testExecute_noUsers() {
        try (MockedStatic<UserStorage> mock = mockStatic(UserStorage.class)) {
            mock.when(UserStorage::loadUsers).thenReturn(new HashMap<>());

            ShowAllNormalUsers.execute();

            String output = outContent.toString().trim();
            assertTrue(output.contains("📭 No users registered."));
        }
    }

    @Test
    void testExecute_withUsers() {
        NormalUser mockUser = mock(NormalUser.class);
        when(mockUser.getUsername()).thenReturn("user123");
        when(mockUser.getEmail()).thenReturn("user@example.com");
        when(mockUser.getID()).thenReturn(42);
        when(mockUser.getPreferredSport()).thenReturn("Basketball");
        when(mockUser.getPreferredTimeSlot()).thenReturn("Evening");

        HashMap<String, NormalUser> userMap = new HashMap<>();
        userMap.put("user123", mockUser);

        try (MockedStatic<UserStorage> mock = mockStatic(UserStorage.class)) {
            mock.when(UserStorage::loadUsers).thenReturn(userMap);

            ShowAllNormalUsers.execute();

            String output = outContent.toString().trim();

            assertTrue(output.contains("👥 Registered Normal Users:"));
            assertTrue(output.contains("- user123 | Email: user@example.com | ID: 42 | Sport: Basketball | Timeslot: Evening"));
        }
    }
}
