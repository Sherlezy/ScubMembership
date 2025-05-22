package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.QuizzSystem.LeaderboardDisplay;

import java.util.Scanner;

public class ViewLeaderboard {

    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();

        System.out.print("Enter sport to view leaderboard: ");
        String sport = scanner.nextLine().trim();

        if (sport.isEmpty()) {
            System.out.println("❌ Sport name cannot be empty.");
            return;
        }

        LeaderboardDisplay.showLeaderboardForSport(sport);
    }
}
