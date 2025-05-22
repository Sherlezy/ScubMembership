package org.ScubMembership.QuizzSystem;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class QuizLeaderboardEntryTest {

    @Test
    void testConstructorAndGetters() {
        String username = "testUser";
        String sport = "Tennis";
        int score = 10;
        boolean isFavorite = true;

        QuizLeaderboardEntry entry = new QuizLeaderboardEntry(username, sport, score, isFavorite);

        assertEquals(username, entry.getUsername());
        assertEquals(sport, entry.getSport());
        assertEquals(score, entry.getScore());
        assertTrue(entry.isFavorite());
        assertNotNull(entry.getTimestamp());

        LocalDateTime now = LocalDateTime.now();
        assertTrue(!entry.getTimestamp().isAfter(now)); // Ensure timestamp is not from the future
    }

    @Test
    void testToStringIncludesAllInfo() {
        QuizLeaderboardEntry entry = new QuizLeaderboardEntry("Alice", "Running", 7, true);

        String result = entry.toString();
        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("Running"));
        assertTrue(result.contains("7"));
        assertTrue(result.contains("★ Favorite Sport"));
        assertTrue(result.matches(".*\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}\\]$")); // Timestamp format check
    }

    @Test
    void testToStringWithoutFavoriteBadge() {
        QuizLeaderboardEntry entry = new QuizLeaderboardEntry("Bob", "Swimming", 5, false);

        String result = entry.toString();
        assertFalse(result.contains("★ Favorite Sport"));
    }

    @Test
    void testEdgeCaseZeroScore() {
        QuizLeaderboardEntry entry = new QuizLeaderboardEntry("ZeroHero", "Yoga", 0, false);
        assertEquals(0, entry.getScore());
        assertTrue(entry.toString().contains("0 pts"));
    }

    @Test
    void testEdgeCaseEmptyUsernameAndSport() {
        QuizLeaderboardEntry entry = new QuizLeaderboardEntry("", "", 1, false);

        assertEquals("", entry.getUsername());
        assertEquals("", entry.getSport());
        assertTrue(entry.toString().contains(" — 1 pts in "));
    }

    @Test
    void testEdgeCaseNegativeScore() {
        QuizLeaderboardEntry entry = new QuizLeaderboardEntry("NegativeNancy", "Climbing", -5, false);

        assertEquals(-5, entry.getScore());
        assertTrue(entry.toString().contains("-5 pts"));
    }
}
