package org.ScubMembership.QuizzSystem;

import org.ScubMembership.Users.NormalUser.NormalUser;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class QuizRunnerTest {

    private NormalUser user;

    @BeforeEach
    public void setup() {
        user = new NormalUser("test@example.com", "testUser", "password", 1, "Football");

        // Prepare test quiz
        List<QuizQuestion> sampleQuiz = List.of(
                new QuizQuestion("What is 2 + 2?", List.of("3", "4", "5", "6"), 1),
                new QuizQuestion("Capital of France?", List.of("Berlin", "Paris", "Rome", "Madrid"), 1)
        );

        QuizManager.saveQuiz("Football", sampleQuiz);

        // Clear progress for clean state
        QuizProgressTracker.updateProgress(user.getUsername(), "Football", 0);
    }

    @AfterEach
    public void cleanup() {
        QuizProgressTracker.updateProgress(user.getUsername(), "Football", 0);
    }

    @Test
    public void testCompleteQuizSuccessfully() {
        // Simulate correct answers: "2" for both
        String simulatedInput = "2\n2\n";
        ByteArrayInputStream input = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(input);

        QuizRunner.startQuiz("Football", scanner, user);

        assertTrue(QuizProgressTracker.getLastAnsweredIndex(user.getUsername(), "Football") >= 2);
        List<QuizLeaderboardEntry> leaderboard = QuizLeaderboardManager.getLeaderboard("Football");

        boolean entryExists = leaderboard.stream().anyMatch(
                e -> e.getUsername().equals(user.getUsername()) && e.getScore() == 2
        );

        assertTrue(entryExists, "Leaderboard should contain entry with score 2.");
    }

    @Test
    public void testNoQuizAvailable() {
        ByteArrayInputStream input = new ByteArrayInputStream("1\n".getBytes());
        Scanner scanner = new Scanner(input);
        QuizRunner.startQuiz("NonexistentSport", scanner, user);
        // Should print "❌ No quiz available"
        assertEquals(0, QuizProgressTracker.getLastAnsweredIndex(user.getUsername(), "NonexistentSport"));
    }

    @Test
    public void testQuizAlreadyCompleted() {
        QuizProgressTracker.updateProgress(user.getUsername(), "Football", 2);

        ByteArrayInputStream input = new ByteArrayInputStream("1\n".getBytes());
        Scanner scanner = new Scanner(input);
        QuizRunner.startQuiz("Football", scanner, user);

    }

    @Test
    public void testInvalidAnswerHandled() {
        String simulatedInput = "abc\n5\n2\n2\n"; // First answer: invalid, then valid
        ByteArrayInputStream input = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(input);

        QuizRunner.startQuiz("Football", scanner, user);

        assertEquals(2, QuizProgressTracker.getLastAnsweredIndex(user.getUsername(), "Football"));
    }
}
