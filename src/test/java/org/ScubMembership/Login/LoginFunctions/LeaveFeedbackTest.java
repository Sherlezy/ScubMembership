package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.SportActivity.Cycling.Cycling;
import org.ScubMembership.SportActivity.EventManager;
import org.ScubMembership.SportActivity.Feedback;
import org.ScubMembership.SportActivity.SportEvent;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LeaveFeedbackTest {
    private LoginPage loginPage;
    private NormalUser mockUser;
    private SportEvent event;

    @BeforeEach
    void setup() {
        mockUser = new NormalUser("u@unibz.it", "testUser", "pass", 1, "Cycling", "Morning (08:00 - 12:00)");
        loginPage = new LoginPage(new HashMap<>());
        loginPage.setCurrentUser(mockUser);

        event = new SportEvent(new Cycling(), LocalDateTime.now().minusDays(2), 4);
        event.registerUser(mockUser.getUsername());

        EventManager.getEvents().clear();
        EventManager.getEvents().add(event);
    }

    @Test
    void testLeaveFeedback_validInput_feedbackAdded() {
        Scanner scannerMock = mock(Scanner.class);
        when(scannerMock.nextLine()).thenReturn("1", "4", "Great Event!");
        loginPage.setScanner(scannerMock);

        LeaveFeedback.execute(loginPage);

        List<Feedback> feedbackList = event.getFeedbackList();
        assertEquals("testUser", feedbackList.get(0).getUsername());
        assertEquals(4.0, feedbackList.get(0).getRating());
        assertEquals(1, feedbackList.size());
        assertEquals("Great Event!", feedbackList.get(0).getComment());
    }
}
