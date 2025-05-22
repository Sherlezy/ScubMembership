package org.ScubMembership.SportActivity;

import org.ScubMembership.SportActivity.Tennis.Tennis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventManagerTest {
    @BeforeEach
    public void setup() {
        EventManager.clearEvents();
    }

    @Test
    public void testEventAddition() {
        abs_sport tennis = new Tennis();
        EventManager.addEvent(new SportEvent(tennis, LocalDateTime.now().plusDays(1), 10));

        List<SportEvent> events = EventManager.getEvents();
        assertEquals(1, events.size());
        assertEquals("Tennis", events.get(0).getSport().getName());
    }
}
