package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.Admin.Admin;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.AdminStorage;
import org.ScubMembership.Utils.UserStorage;
import org.ScubMembership.Utils.Welcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LogoutTest {

    @Mock
    private LoginPage loginPage;

    @Mock
    private NormalUser currentUser;

    private Logout logout;
    private HashMap<String, NormalUser> loginInfo;
    private HashMap<String, Admin> adminInfo;
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

        adminInfo = new HashMap<>();
        Admin mockAdmin = mock(Admin.class);
        adminInfo.put("admin", mockAdmin);

        when(loginPage.getLoginInfo()).thenReturn(loginInfo);

        logout = new Logout(loginPage);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testSuccessfulLogout() {

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(adminInfo);

            logout.startLogout();

            verify(loginPage).setLoggedIn(false);
            verify(loginPage).setCurrentUser(null);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(loginInfo));
            mockedAdminStorage.verify(AdminStorage::loadAdmins);
            mockedWelcome.verify(() -> Welcome.welcome(loginInfo, adminInfo));

            String output = outputStream.toString();
            assertTrue(output.contains("Logging out..."));
            assertTrue(output.contains("Logged out successfully!"));
        }
    }

    @Test
    void testLogoutWithNullLoginInfo() {

        when(loginPage.getLoginInfo()).thenReturn(null);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(adminInfo);

            logout.startLogout();

            verify(loginPage).setLoggedIn(false);
            verify(loginPage).setCurrentUser(null);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(null));
            mockedAdminStorage.verify(AdminStorage::loadAdmins);
            mockedWelcome.verify(() -> Welcome.welcome(null, adminInfo));

            String output = outputStream.toString();
            assertTrue(output.contains("Logging out..."));
            assertTrue(output.contains("Logged out successfully!"));
        }
    }

    @Test
    void testLogoutWithEmptyLoginInfo() {

        HashMap<String, NormalUser> emptyLoginInfo = new HashMap<>();
        when(loginPage.getLoginInfo()).thenReturn(emptyLoginInfo);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(adminInfo);

            logout.startLogout();

            verify(loginPage).setLoggedIn(false);
            verify(loginPage).setCurrentUser(null);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(emptyLoginInfo));
            mockedAdminStorage.verify(AdminStorage::loadAdmins);
            mockedWelcome.verify(() -> Welcome.welcome(emptyLoginInfo, adminInfo));
        }
    }

    @Test
    void testLogoutWithNullAdminInfo() {

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(null);

            logout.startLogout();

            verify(loginPage).setLoggedIn(false);
            verify(loginPage).setCurrentUser(null);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(loginInfo));
            mockedAdminStorage.verify(AdminStorage::loadAdmins);
            mockedWelcome.verify(() -> Welcome.welcome(loginInfo, null));
        }
    }

    @Test
    void testLogoutWithEmptyAdminInfo() {

        HashMap<String, Admin> emptyAdminInfo = new HashMap<>();

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(emptyAdminInfo);

            logout.startLogout();

            verify(loginPage).setLoggedIn(false);
            verify(loginPage).setCurrentUser(null);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(loginInfo));
            mockedAdminStorage.verify(AdminStorage::loadAdmins);
            mockedWelcome.verify(() -> Welcome.welcome(loginInfo, emptyAdminInfo));
        }
    }

    @Test
    void testUserStorageSaveFailure() {

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedUserStorage.when(() -> UserStorage.saveUsers(loginInfo))
                    .thenThrow(new RuntimeException("File save failed"));
            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(adminInfo);

            assertThrows(RuntimeException.class, () -> {
                logout.startLogout();
            });

            verify(loginPage).setLoggedIn(false);
        }
    }

    @Test
    void testAdminStorageLoadFailure() {

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins)
                    .thenThrow(new RuntimeException("Admin load failed"));

            assertThrows(RuntimeException.class, () -> {
                logout.startLogout();
            });

            verify(loginPage).setLoggedIn(false);
            verify(loginPage).setCurrentUser(null);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(loginInfo));
        }
    }

    @Test
    void testWelcomeMethodFailure() {

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(adminInfo);
            mockedWelcome.when(() -> Welcome.welcome(loginInfo, adminInfo))
                    .thenThrow(new RuntimeException("Welcome method failed"));

            assertThrows(RuntimeException.class, () -> {
                logout.startLogout();
            });

            verify(loginPage).setLoggedIn(false);
            verify(loginPage).setCurrentUser(null);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(loginInfo));
            mockedAdminStorage.verify(AdminStorage::loadAdmins);
        }
    }

    @Test
    void testLogoutExecutionOrder() {

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(adminInfo);

            logout.startLogout();

            var inOrder = inOrder(loginPage);
            inOrder.verify(loginPage).setLoggedIn(false);
            inOrder.verify(loginPage).getLoginInfo();
            inOrder.verify(loginPage).setCurrentUser(null);

            mockedUserStorage.verify(() -> UserStorage.saveUsers(loginInfo));
            mockedAdminStorage.verify(AdminStorage::loadAdmins);
            mockedWelcome.verify(() -> Welcome.welcome(loginInfo, adminInfo));
        }
    }

    @Test
    void testLogoutConsoleOutput() {

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(adminInfo);

            logout.startLogout();

            String output = outputStream.toString();
            assertTrue(output.contains("Logging out..."));
            assertTrue(output.contains("Logged out successfully!"));

            String[] lines = output.split("\n");
            boolean foundLoggingOut = false;
            boolean foundLoggedOut = false;
            for (String line : lines) {
                if (line.contains("Logging out...")) {
                    foundLoggingOut = true;
                    assertFalse(foundLoggedOut, "Logging out message should come before logged out message");
                } else if (line.contains("Logged out successfully!")) {
                    foundLoggedOut = true;
                    assertTrue(foundLoggingOut, "Logged out message should come after logging out message");
                }
            }
            assertTrue(foundLoggingOut && foundLoggedOut, "Both messages should be present");
        }
    }

    @Test
    void testConstructorWithNullLoginPage() {
        assertDoesNotThrow(() -> {
            new Logout(null);
        });
    }

    @Test
    void testLogoutWithNullLoginPage() {

        Logout logoutWithNull = new Logout(null);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(adminInfo);

            assertThrows(NullPointerException.class, () -> {
                logoutWithNull.startLogout();
            });
        }
    }

    @Test
    void testLogoutStateChanges() {

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(adminInfo);

            logout.startLogout();

            verify(loginPage, times(1)).setLoggedIn(false);
            verify(loginPage, times(1)).setCurrentUser(null);
            verify(loginPage, never()).setLoggedIn(true);
            verify(loginPage, never()).setCurrentUser(any(NormalUser.class));
        }
    }

    @Test
    void testLogoutWithLargeDataSets() {

        HashMap<String, NormalUser> largeLoginInfo = new HashMap<>();
        HashMap<String, Admin> largeAdminInfo = new HashMap<>();

        for (int i = 0; i < 1000; i++) {
            NormalUser user = mock(NormalUser.class);
            Admin admin = mock(Admin.class);
            largeLoginInfo.put("user" + i, user);
            largeAdminInfo.put("admin" + i, admin);
        }

        when(loginPage.getLoginInfo()).thenReturn(largeLoginInfo);

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(largeAdminInfo);

            logout.startLogout();

            verify(loginPage).setLoggedIn(false);
            verify(loginPage).setCurrentUser(null);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(largeLoginInfo));
            mockedAdminStorage.verify(AdminStorage::loadAdmins);
            mockedWelcome.verify(() -> Welcome.welcome(largeLoginInfo, largeAdminInfo));
        }
    }

    @Test
    void testMultipleLogoutCalls() {

        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(adminInfo);

            logout.startLogout();
            logout.startLogout();
            logout.startLogout();

            verify(loginPage, times(3)).setLoggedIn(false);
            verify(loginPage, times(3)).setCurrentUser(null);
            mockedUserStorage.verify(() -> UserStorage.saveUsers(loginInfo), times(3));
            mockedAdminStorage.verify(AdminStorage::loadAdmins, times(3));
            mockedWelcome.verify(() -> Welcome.welcome(loginInfo, adminInfo), times(3));

            String output = outputStream.toString();
            long loggingOutCount = output.lines().filter(line -> line.contains("Logging out...")).count();
            long loggedOutCount = output.lines().filter(line -> line.contains("Logged out successfully!")).count();
            assertEquals(3, loggingOutCount);
            assertEquals(3, loggedOutCount);
        }
    }

    @Test
    void testLogoutMethodInteractions() {
        try (MockedStatic<UserStorage> mockedUserStorage = mockStatic(UserStorage.class);
             MockedStatic<AdminStorage> mockedAdminStorage = mockStatic(AdminStorage.class);
             MockedStatic<Welcome> mockedWelcome = mockStatic(Welcome.class)) {

            mockedAdminStorage.when(AdminStorage::loadAdmins).thenReturn(adminInfo);

            logout.startLogout();

            verify(loginPage, times(2)).getLoginInfo();

            mockedUserStorage.verify(() -> UserStorage.saveUsers(any()), times(1));
            mockedAdminStorage.verify(AdminStorage::loadAdmins, times(1));
            mockedWelcome.verify(() -> Welcome.welcome(any(), any()), times(1));
        }
    }
}
