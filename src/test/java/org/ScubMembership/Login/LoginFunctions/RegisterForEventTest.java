package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.SportActivity.EventManager;
import org.ScubMembership.SportActivity.Running.Running;
import org.ScubMembership.SportActivity.SportEvent;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RegisterForEventTest {
    private LoginPage loginPage;
    private NormalUser mockUser;
    private SportEvent futureEvent;

    @BeforeEach
    void setup() {
        mockUser = new NormalUser("u@unibz.it", "testUser", "pass", 1, "Running");
        loginPage = new LoginPage(new HashMap<>());
        loginPage.setCurrentUser(mockUser);

        futureEvent = new SportEvent(new Running(), LocalDateTime.now().plusDays(2), 5);

        EventManager.getEvents().clear();
        EventManager.getEvents().add(futureEvent);
    }

    @Test
    void testRegisterForEvent_validSelection_registerSuccessfully() {
        Scanner scannerMock = mock(Scanner.class);
        when(scannerMock.nextLine()).thenReturn("1");
        loginPage.setScanner(scannerMock);

        RegisterForEvent.execute(loginPage);

        assertTrue(futureEvent.getRegisteredUsers().contains(mockUser.getUsername()));
    }
}
