package org.ScubMembership.QuizzSystem;

import org.ScubMembership.RecommendationLogic.SportUtils;
import org.ScubMembership.SportActivity.abs_sport;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizMenuTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    private static class TestSport extends abs_sport {
        public TestSport(String name) {
            super(name, new String[]{}, "", 0, "", true, "");
        }
    }

    @BeforeEach
    void setupStreams() {
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testExitMenuImmediately() {
        NormalUser mockUser = mock(NormalUser.class);
        Scanner scanner = new Scanner(new ByteArrayInputStream("0\n".getBytes()));

        try (MockedStatic<SportUtils> sportUtilsMock = mockStatic(SportUtils.class)) {
            sportUtilsMock.when(() -> SportUtils.getSortedSportsWithFavoriteFirst(mockUser))
                    .thenReturn(List.of(new TestSport("Basketball")));

            QuizMenu.show(mockUser, scanner);

            String output = outContent.toString();
            assertTrue(output.contains("🎯 Quiz Menu:"));
            assertTrue(output.contains("0. Exit quiz menu"));
        }
    }

    @Test
    void testTakeQuizOption() {
        NormalUser mockUser = mock(NormalUser.class);
        Scanner scanner = new Scanner(new ByteArrayInputStream("1\n0\n".getBytes())); // Take quiz, then exit

        abs_sport sport = new TestSport("Tennis");

        try (
                MockedStatic<SportUtils> sportUtilsMock = mockStatic(SportUtils.class);
                MockedStatic<QuizRunner> quizRunnerMock = mockStatic(QuizRunner.class)
        ) {
            sportUtilsMock.when(() -> SportUtils.getSortedSportsWithFavoriteFirst(mockUser))
                    .thenReturn(List.of(sport));

            quizRunnerMock.when(() -> QuizRunner.startQuiz("Tennis", scanner, mockUser))
                    .thenAnswer(inv -> {
                        System.out.println("▶️ Quiz started for Tennis");
                        return null;
                    });

            QuizMenu.show(mockUser, scanner);

            String output = outContent.toString();
            assertTrue(output.contains("▶️ Quiz started for Tennis"));
        }
    }

    @Test
    void testLeaderboardViewValid() {
        NormalUser mockUser = mock(NormalUser.class);
        String input = "L\n1\n0\n"; // view leaderboard, choose sport 1, exit
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        abs_sport sport = new TestSport("Football");

        try (
                MockedStatic<SportUtils> sportUtilsMock = mockStatic(SportUtils.class);
                MockedStatic<LeaderboardDisplay> leaderboardMock = mockStatic(LeaderboardDisplay.class)
        ) {
            sportUtilsMock.when(() -> SportUtils.getSortedSportsWithFavoriteFirst(mockUser))
                    .thenReturn(List.of(sport));

            leaderboardMock.when(() -> LeaderboardDisplay.showLeaderboardForSport("Football"))
                    .thenAnswer(inv -> {
                        System.out.println("📊 Showing leaderboard for Football");
                        return null;
                    });

            QuizMenu.show(mockUser, scanner);

            String output = outContent.toString();
            assertTrue(output.contains("📊 Showing leaderboard for Football"));
        }
    }

    @Test
    void testInvalidSportSelection() {
        NormalUser mockUser = mock(NormalUser.class);
        Scanner scanner = new Scanner(new ByteArrayInputStream("99\n0\n".getBytes())); // invalid selection, then exit

        abs_sport sport = new TestSport("Swimming");

        try (MockedStatic<SportUtils> sportUtilsMock = mockStatic(SportUtils.class)) {
            sportUtilsMock.when(() -> SportUtils.getSortedSportsWithFavoriteFirst(mockUser))
                    .thenReturn(List.of(sport));

            QuizMenu.show(mockUser, scanner);

            String output = outContent.toString();
            assertTrue(output.contains("❌ Please select a valid number."));
        }
    }

    @Test
    void testInvalidInputInLeaderboard() {
        NormalUser mockUser = mock(NormalUser.class);
        String input = "L\nabc\n0\n"; // invalid input for leaderboard
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        abs_sport sport = new TestSport("Boxing");

        try (MockedStatic<SportUtils> sportUtilsMock = mockStatic(SportUtils.class)) {
            sportUtilsMock.when(() -> SportUtils.getSortedSportsWithFavoriteFirst(mockUser))
                    .thenReturn(List.of(sport));

            QuizMenu.show(mockUser, scanner);

            String output = outContent.toString();
            assertTrue(output.contains("❌ Please enter a valid number."));
        }
    }
}
