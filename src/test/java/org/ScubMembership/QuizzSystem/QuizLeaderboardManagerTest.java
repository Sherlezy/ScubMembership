package org.ScubMembership.QuizzSystem;

import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizLeaderboardManagerTest {

    private static final String TEST_FILE = "quiz_leaderboard.ser";

    @BeforeEach
    void setup() throws Exception {
        // Clear leaderboard before each test
        resetLeaderboard(new ArrayList<>());
        deleteTestFile();
    }

    @AfterEach
    void cleanup() throws Exception {
        resetLeaderboard(new ArrayList<>());
        deleteTestFile();
    }

    @Test
    void testAddEntryAndGetLeaderboard() {
        QuizLeaderboardManager.addEntry("Alice", "Tennis", 8, true);
        QuizLeaderboardManager.addEntry("Bob", "Tennis", 10, false);
        QuizLeaderboardManager.addEntry("Charlie", "Football", 5, true);

        List<QuizLeaderboardEntry> tennisLeaderboard = QuizLeaderboardManager.getLeaderboard("Tennis");
        assertEquals(2, tennisLeaderboard.size());

        assertEquals("Bob", tennisLeaderboard.get(0).getUsername()); // Highest score first
        assertEquals("Alice", tennisLeaderboard.get(1).getUsername());

        List<QuizLeaderboardEntry> footballLeaderboard = QuizLeaderboardManager.getLeaderboard("Football");
        assertEquals(1, footballLeaderboard.size());
        assertEquals("Charlie", footballLeaderboard.get(0).getUsername());
    }

    @Test
    void testCaseInsensitiveSportFiltering() {
        QuizLeaderboardManager.addEntry("Diana", "Swimming", 6, false);
        QuizLeaderboardManager.addEntry("Eli", "swimming", 4, false);

        List<QuizLeaderboardEntry> leaderboard = QuizLeaderboardManager.getLeaderboard("SwImMiNg");
        assertEquals(2, leaderboard.size());
    }

    @Test
    void testEmptyLeaderboardReturnsEmptyList() {
        List<QuizLeaderboardEntry> result = QuizLeaderboardManager.getLeaderboard("Yoga");
        assertTrue(result.isEmpty());
    }

    @Test
    void testAddEntryWithZeroOrNegativeScore() {
        QuizLeaderboardManager.addEntry("Zero", "Running", 0, false);
        QuizLeaderboardManager.addEntry("Negative", "Running", -3, true);

        List<QuizLeaderboardEntry> leaderboard = QuizLeaderboardManager.getLeaderboard("Running");
        assertEquals(2, leaderboard.size());
        assertEquals("Zero", leaderboard.get(0).getUsername());
        assertEquals("Negative", leaderboard.get(1).getUsername());
    }

    @Test
    void testPersistenceToFile() {
        QuizLeaderboardManager.addEntry("Saver", "Cycling", 9, false);

        File file = new File(TEST_FILE);
        assertTrue(file.exists() && file.length() > 0, "Leaderboard file should be created and non-empty.");
    }



    private void resetLeaderboard(List<QuizLeaderboardEntry> newList) throws Exception {
        var field = QuizLeaderboardManager.class.getDeclaredField("leaderboard");
        field.setAccessible(true);
        field.set(null, newList);
    }

    private void deleteTestFile() {
        File file = new File(TEST_FILE);
        if (file.exists()) file.delete();
    }
}
