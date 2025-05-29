package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.QuizzSystem.QuizRunner;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.HashMap;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class TakeFavouriteSportQuizTest {
    private LoginPage loginPage;
    private NormalUser mockUser;
    private Scanner mockScanner;

    @BeforeEach
    void setup() {
        mockUser = new NormalUser("u@unibz.it", "testUser", "pass", 1, "Volleyball", "testTime");

        loginPage = new LoginPage(new HashMap<>());
        loginPage.setCurrentUser(mockUser);

        mockScanner = mock(Scanner.class);
        loginPage.setScanner(mockScanner);
    }

    @Test
    void testQuizRunsWhenFavoriteSportIsPresent() {
        try (MockedStatic<QuizRunner> mockedStatic = mockStatic(QuizRunner.class)) {
            TakeFavouriteSportQuiz.execute(loginPage);

            mockedStatic.verify(() -> QuizRunner.startQuiz("Volleyball", mockScanner, mockUser));
        }
    }
}
