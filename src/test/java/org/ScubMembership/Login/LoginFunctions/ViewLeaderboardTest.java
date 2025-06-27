package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.QuizzSystem.LeaderboardDisplay;
import org.ScubMembership.SportActivity.abs_sport;
import org.ScubMembership.RecommendationLogic.SportUtils;
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

class ViewLeaderboardTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    private static class TestSport extends abs_sport {
        public TestSport(String name) {
            super(name, new String[]{}, "", 0, "", true, "");
        }
    }

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
    void testValidInput_showsLeaderboard() {
        // Setup
        NormalUser mockUser = mock(NormalUser.class);
        LoginPage mockLoginPage = mock(LoginPage.class);
        when(mockLoginPage.getCurrentUser()).thenReturn(mockUser);

        String input = "1\n"; // select sport 1
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(mockLoginPage.getScanner()).thenReturn(scanner);

        abs_sport sport = new TestSport("Basketball");

        try (
                MockedStatic<SportUtils> sportUtilsMock = mockStatic(SportUtils.class);
                MockedStatic<LeaderboardDisplay> leaderboardMock = mockStatic(LeaderboardDisplay.class)
        ) {
            sportUtilsMock.when(() -> SportUtils.getSortedSportsWithFavoriteFirst(mockUser))
                    .thenReturn(List.of(sport));

            leaderboardMock.when(() -> LeaderboardDisplay.showLeaderboardForSport("Basketball"))
                    .then(invocation -> {
                        System.out.println("🏆 Leaderboard for Basketball");
                        return null;
                    });

            ViewLeaderboard.execute(mockLoginPage);

            String output = outContent.toString().trim();
            assertTrue(output.contains("📊 View Leaderboard for a Sport"));
            assertTrue(output.contains("1. Basketball"));
            assertTrue(output.contains("🏆 Leaderboard for Basketball"));

            leaderboardMock.verify(() -> LeaderboardDisplay.showLeaderboardForSport("Basketball"), times(1));
        }
    }

    @Test
    void testInvalidOption_outOfRange() {
        NormalUser mockUser = mock(NormalUser.class);
        LoginPage mockLoginPage = mock(LoginPage.class);
        when(mockLoginPage.getCurrentUser()).thenReturn(mockUser);

        String input = "5\n"; // out of range
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(mockLoginPage.getScanner()).thenReturn(scanner);

        abs_sport sport = new TestSport("Basketball");

        try (MockedStatic<SportUtils> sportUtilsMock = mockStatic(SportUtils.class)) {
            sportUtilsMock.when(() -> SportUtils.getSortedSportsWithFavoriteFirst(mockUser))
                    .thenReturn(List.of(sport));

            ViewLeaderboard.execute(mockLoginPage);

            String output = outContent.toString().trim();
            assertTrue(output.contains("❌ Invalid option. Please choose a valid number."));
        }
    }

    @Test
    void testInvalidInput_notANumber() {
        NormalUser mockUser = mock(NormalUser.class);
        LoginPage mockLoginPage = mock(LoginPage.class);
        when(mockLoginPage.getCurrentUser()).thenReturn(mockUser);

        String input = "abc\n"; // not a number
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(mockLoginPage.getScanner()).thenReturn(scanner);

        abs_sport sport = new TestSport("Basketball");

        try (MockedStatic<SportUtils> sportUtilsMock = mockStatic(SportUtils.class)) {
            sportUtilsMock.when(() -> SportUtils.getSortedSportsWithFavoriteFirst(mockUser))
                    .thenReturn(List.of(sport));

            ViewLeaderboard.execute(mockLoginPage);

            String output = outContent.toString().trim();
            assertTrue(output.contains("❌ Invalid input. Please enter a number."));
        }
    }
}
