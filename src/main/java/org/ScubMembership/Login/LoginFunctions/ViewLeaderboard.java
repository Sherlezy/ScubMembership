package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.QuizzSystem.LeaderboardDisplay;
import org.ScubMembership.SportActivity.abs_sport;
import org.ScubMembership.RecommendationLogic.SportUtils;

import java.util.List;
import java.util.Scanner;

public class ViewLeaderboard {

    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();

        List<abs_sport> sortedSports = SportUtils.getSortedSportsWithFavoriteFirst(loginPage.getCurrentUser());

        System.out.println("\n📊 View Leaderboard for a Sport");
        System.out.println("Choose a sport:");

        for (int i = 0; i < sortedSports.size(); i++) {
            System.out.println((i + 1) + ". " + sortedSports.get(i).getName());
        }

        System.out.print("Option (1-" + sortedSports.size() + "): ");
        String input = scanner.nextLine();

        try {
            int selected = Integer.parseInt(input);
            if (selected >= 1 && selected <= sortedSports.size()) {
                String sportName = sortedSports.get(selected - 1).getName();
                LeaderboardDisplay.showLeaderboardForSport(sportName);
            } else {
                System.out.println("❌ Invalid option. Please choose a valid number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a number.");
        }
    }
}
