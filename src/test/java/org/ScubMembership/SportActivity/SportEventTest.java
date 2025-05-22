package org.ScubMembership.SportActivity;

import org.ScubMembership.SportActivity.Football.Football;
import org.ScubMembership.SportActivity.Swimming.Swimming;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SportEventTest {
    @Test
    public void testEventRegistrationLimits() {
        abs_sport football = new Football();
        SportEvent event = new SportEvent(football, LocalDateTime.now().plusDays(1), 2);

        assertTrue(event.registerUser("alessandro"));
        assertTrue(event.registerUser("andrea"));
        assertFalse(event.registerUser("alessio"));

        assertEquals(2, event.getRegisteredUsers().size());
    }

    @Test
    public void testDuplicateRegistration() {
        abs_sport swimming = new Swimming();
        SportEvent event = new SportEvent(swimming, LocalDateTime.now().plusDays(1),5);

        assertTrue(event.registerUser("andrea"));
        assertFalse(event.registerUser("andrea"));
    }
}
