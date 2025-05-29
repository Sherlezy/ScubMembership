package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.SportActivity.*;
import org.ScubMembership.SportActivity.Basketball.Basketball;
import org.ScubMembership.SportActivity.Climbing.Climbing;
import org.ScubMembership.SportActivity.Running.Running;
import org.ScubMembership.SportActivity.Tennis.Tennis;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewFeedbackAveragesTest {
    @BeforeEach
    void clearEvents() {
        EventManager.getEvents().clear();
    }

    @Test
    void testAverageShownForPastEvent() {
        abs_sport climbing = new Climbing();
        NormalUser user1 = new NormalUser("u1@unibz.it", "user1", "pass", 11, "Climbing", "Evening (17:00 - 21:00)");
        NormalUser user2 = new NormalUser("u2@unibz.it", "user2", "pass", 12, "Swimming", "Afternoon (17:00 - 21:00)");
        SportEvent pastEvent = new SportEvent(climbing, LocalDateTime.now().minusDays(2), 4);

        pastEvent.registerUser(user1.getUsername());
        pastEvent.registerUser(user2.getUsername());

        Feedback f1 = new Feedback(user1.username, 4, "No comment");
        pastEvent.addFeedback(f1);
        Feedback f2 = new Feedback(user2.username, 2.5, "No comment");
        pastEvent.addFeedback(f2);

        EventManager.getEvents().add(pastEvent);

        double average = pastEvent.getAverageRating();
        double expectedAverage = Math.round(((f1.getRating() + f2.getRating()) / 2) * 10.0) / 10.0;

        assertEquals(expectedAverage, average);
    }

    @Test
    void testNoFeedbackReturns0() {
        SportEvent pastEvent = new SportEvent(new Running(), LocalDateTime.now().minusDays(2), 5);
        EventManager.getEvents().add(pastEvent);
        assertEquals(0.0, pastEvent.getAverageRating());
    }

    @Test
    void testFutureEventNotIncludedInAverages() {
        SportEvent futureEvent = new SportEvent(new Tennis(), LocalDateTime.now().plusDays(2), 4);
        EventManager.getEvents().add(futureEvent);
        assertTrue(EventManager.getEvents().stream()
                .noneMatch(event -> event.getDateTime().isBefore(LocalDateTime.now()) && event.getAverageRating() > 0));
    }
}
