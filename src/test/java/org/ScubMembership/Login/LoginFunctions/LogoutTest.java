

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
    private LoginPage mockLoginPage;

    private Logout logout;
    private HashMap<String, NormalUser> loginInfo;
    private HashMap<String, Admin> adminInfo;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private MockedStatic<UserStorage> userStorageMockedStatic;
    private MockedStatic<AdminStorage> adminStorageMockedStatic;
    private MockedStatic<Welcome> welcomeMockedStatic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        loginInfo = new HashMap<>();
        adminInfo = new HashMap<>();
        Admin mockAdmin = mock(Admin.class);
        adminInfo.put("admin", mockAdmin);

        when(mockLoginPage.getLoginInfo()).thenReturn(loginInfo);

        userStorageMockedStatic = mockStatic(UserStorage.class);
        adminStorageMockedStatic = mockStatic(AdminStorage.class);
        welcomeMockedStatic = mockStatic(Welcome.class);

        adminStorageMockedStatic.when(AdminStorage::loadAdmins).thenReturn(adminInfo);

        logout = new Logout(mockLoginPage);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        if (userStorageMockedStatic != null) userStorageMockedStatic.close();
        if (adminStorageMockedStatic != null) adminStorageMockedStatic.close();
        if (welcomeMockedStatic != null) welcomeMockedStatic.close();
    }

    @Test
    void testSuccessfulLogout() {
        logout.startLogout();

        verify(mockLoginPage).setLoggedIn(false);
        verify(mockLoginPage).setCurrentUser(null);
        userStorageMockedStatic.verify(() -> UserStorage.saveUsers(loginInfo));
        adminStorageMockedStatic.verify(AdminStorage::loadAdmins);
        welcomeMockedStatic.verify(() -> Welcome.welcome(loginInfo, adminInfo));

        String output = outputStream.toString();
        assertTrue(output.contains("Logging out..."));
        assertTrue(output.contains("Logged out successfully!"));
    }

    @Test
    void testLogoutWithNullLoginInfo() {
        when(mockLoginPage.getLoginInfo()).thenReturn(null);

        logout.startLogout();

        verify(mockLoginPage).setLoggedIn(false);
        verify(mockLoginPage).setCurrentUser(null);
        userStorageMockedStatic.verify(() -> UserStorage.saveUsers(null));
        adminStorageMockedStatic.verify(AdminStorage::loadAdmins);
        welcomeMockedStatic.verify(() -> Welcome.welcome(null, adminInfo));

        String output = outputStream.toString();
        assertTrue(output.contains("Logging out..."));
        assertTrue(output.contains("Logged out successfully!"));
    }

    @Test
    void testLogoutWithEmptyLoginInfo() {
        HashMap<String, NormalUser> emptyLoginInfo = new HashMap<>();
        when(mockLoginPage.getLoginInfo()).thenReturn(emptyLoginInfo);

        logout.startLogout();

        verify(mockLoginPage).setLoggedIn(false);
        verify(mockLoginPage).setCurrentUser(null);
        userStorageMockedStatic.verify(() -> UserStorage.saveUsers(emptyLoginInfo));
        adminStorageMockedStatic.verify(AdminStorage::loadAdmins);
        welcomeMockedStatic.verify(() -> Welcome.welcome(emptyLoginInfo, adminInfo));
    }

    @Test
    void testLogoutWithNullAdminInfo() {
        adminStorageMockedStatic.when(AdminStorage::loadAdmins).thenReturn(null);

        logout.startLogout();

        verify(mockLoginPage).setLoggedIn(false);
        verify(mockLoginPage).setCurrentUser(null);
        userStorageMockedStatic.verify(() -> UserStorage.saveUsers(loginInfo));
        adminStorageMockedStatic.verify(AdminStorage::loadAdmins);
        welcomeMockedStatic.verify(() -> Welcome.welcome(loginInfo, null));
    }

    @Test
    void testLogoutWithEmptyAdminInfo() {
        HashMap<String, Admin> emptyAdminInfo = new HashMap<>();
        adminStorageMockedStatic.when(AdminStorage::loadAdmins).thenReturn(emptyAdminInfo);

        logout.startLogout();

        verify(mockLoginPage).setLoggedIn(false);
        verify(mockLoginPage).setCurrentUser(null);
        userStorageMockedStatic.verify(() -> UserStorage.saveUsers(loginInfo));
        adminStorageMockedStatic.verify(AdminStorage::loadAdmins);
        welcomeMockedStatic.verify(() -> Welcome.welcome(loginInfo, emptyAdminInfo));
    }

    @Test
    void testUserStorageSaveFailure() {
        userStorageMockedStatic.when(() -> UserStorage.saveUsers(loginInfo))
                .thenThrow(new RuntimeException("File save failed"));

        assertThrows(RuntimeException.class, () -> {
            logout.startLogout();
        });

        verify(mockLoginPage).setLoggedIn(false);
    }

    @Test
    void testAdminStorageLoadFailure() {
        adminStorageMockedStatic.when(AdminStorage::loadAdmins)
                .thenThrow(new RuntimeException("Admin load failed"));

        assertThrows(RuntimeException.class, () -> {
            logout.startLogout();
        });

        verify(mockLoginPage).setLoggedIn(false);
        verify(mockLoginPage).setCurrentUser(null);
        userStorageMockedStatic.verify(() -> UserStorage.saveUsers(loginInfo));
    }

    @Test
    void testWelcomeMethodFailure() {
        welcomeMockedStatic.when(() -> Welcome.welcome(loginInfo, adminInfo))
                .thenThrow(new RuntimeException("Welcome method failed"));

        assertThrows(RuntimeException.class, () -> {
            logout.startLogout();
        });

        verify(mockLoginPage).setLoggedIn(false);
        verify(mockLoginPage).setCurrentUser(null);
        userStorageMockedStatic.verify(() -> UserStorage.saveUsers(loginInfo));
        adminStorageMockedStatic.verify(AdminStorage::loadAdmins);
    }

    @Test
    void testLogoutExecutionOrder() {
        logout.startLogout();

        var inOrder = inOrder(mockLoginPage);
        inOrder.verify(mockLoginPage).setLoggedIn(false);
        inOrder.verify(mockLoginPage).getLoginInfo();
        inOrder.verify(mockLoginPage).setCurrentUser(null);

        userStorageMockedStatic.verify(() -> UserStorage.saveUsers(loginInfo));
        adminStorageMockedStatic.verify(AdminStorage::loadAdmins);
        welcomeMockedStatic.verify(() -> Welcome.welcome(loginInfo, adminInfo));
    }

    @Test
    void testLogoutConsoleOutput() {
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

    @Test
    void testConstructorWithNullLoginPage() {
        assertDoesNotThrow(() -> {
            new Logout(null);
        });
    }

    @Test
    void testLogoutWithNullLoginPage() {
        Logout logoutWithNull = new Logout(null);

        assertThrows(NullPointerException.class, () -> {
            logoutWithNull.startLogout();
        });
    }

    @Test
    void testLogoutStateChanges() {
        logout.startLogout();

        verify(mockLoginPage, times(1)).setLoggedIn(false);
        verify(mockLoginPage, times(1)).setCurrentUser(null);
        verify(mockLoginPage, never()).setLoggedIn(true);
        verify(mockLoginPage, never()).setCurrentUser(any(NormalUser.class));
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

        when(mockLoginPage.getLoginInfo()).thenReturn(largeLoginInfo);
        adminStorageMockedStatic.when(AdminStorage::loadAdmins).thenReturn(largeAdminInfo);

        logout.startLogout();

        verify(mockLoginPage).setLoggedIn(false);
        verify(mockLoginPage).setCurrentUser(null);
        userStorageMockedStatic.verify(() -> UserStorage.saveUsers(largeLoginInfo));
        adminStorageMockedStatic.verify(AdminStorage::loadAdmins);
        welcomeMockedStatic.verify(() -> Welcome.welcome(largeLoginInfo, largeAdminInfo));
    }

    @Test
    void testMultipleLogoutCalls() {
        logout.startLogout();
        logout.startLogout();
        logout.startLogout();

        verify(mockLoginPage, times(3)).setLoggedIn(false);
        verify(mockLoginPage, times(3)).setCurrentUser(null);
        userStorageMockedStatic.verify(() -> UserStorage.saveUsers(loginInfo), times(3));
        adminStorageMockedStatic.verify(AdminStorage::loadAdmins, times(3));
        welcomeMockedStatic.verify(() -> Welcome.welcome(loginInfo, adminInfo), times(3));

        String output = outputStream.toString();
        long loggingOutCount = output.lines().filter(line -> line.contains("Logging out...")).count();
        long loggedOutCount = output.lines().filter(line -> line.contains("Logged out successfully!")).count();
        assertEquals(3, loggingOutCount);
        assertEquals(3, loggedOutCount);
    }
}
