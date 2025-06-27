package org.ScubMembership.QuizzSystem;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaderboardDisplayTest {

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
    void testShowLeaderboard_noEntries() {
        try (MockedStatic<QuizLeaderboardManager> mock = mockStatic(QuizLeaderboardManager.class)) {
            mock.when(() -> QuizLeaderboardManager.getLeaderboard("Basketball"))
                    .thenReturn(List.of());

            LeaderboardDisplay.showLeaderboardForSport("Basketball");

            String output = outContent.toString().trim();
            assertTrue(output.contains("📭 No quiz entries yet for Basketball."));
        }
    }

    @Test
    void testShowLeaderboard_withEntries() {
        QuizLeaderboardEntry entry1 = mock(QuizLeaderboardEntry.class);
        when(entry1.getUsername()).thenReturn("alice");
        when(entry1.getScore()).thenReturn(12);
        when(entry1.isFavorite()).thenReturn(true);
        when(entry1.getTimestamp()).thenReturn(LocalDateTime.of(2025, 6, 27, 15, 30));

        QuizLeaderboardEntry entry2 = mock(QuizLeaderboardEntry.class);
        when(entry2.getUsername()).thenReturn("bob");
        when(entry2.getScore()).thenReturn(9);
        when(entry2.isFavorite()).thenReturn(false);
        when(entry2.getTimestamp()).thenReturn(LocalDateTime.of(2025, 6, 26, 10, 0));

        List<QuizLeaderboardEntry> mockList = List.of(entry1, entry2);

        try (MockedStatic<QuizLeaderboardManager> mock = mockStatic(QuizLeaderboardManager.class)) {
            mock.when(() -> QuizLeaderboardManager.getLeaderboard("Basketball"))
                    .thenReturn(mockList);

            LeaderboardDisplay.showLeaderboardForSport("Basketball");

            String output = outContent.toString().trim();
            assertTrue(output.contains("🏆 Leaderboard for Basketball:"));
            assertTrue(output.contains(" 1. alice           — 12 pts ⭐ Favorite Sport [2025-06-27 15:30]"));
            assertTrue(output.contains(" 2. bob             —  9 pts [2025-06-26 10:00]"));
        }
    }
}
