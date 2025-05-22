package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.QuizzSystem.QuizRunner;

import java.util.Scanner;

public class TakeFavouriteSportQuiz {

    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();
        String favSport = loginPage.getCurrentUser().getPreferredSport();

        if (favSport == null || favSport.isBlank()) {
            System.out.println("❌ No favourite sport found. Please update your profile.");
            return;
        }

        System.out.println("🎯 Launching quiz for your favourite sport: " + favSport);
        QuizRunner.startQuiz(favSport, scanner, loginPage.getCurrentUser());
    }
}
