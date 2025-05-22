package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.QuizzSystem.LeaderboardDisplay;
import org.ScubMembership.SportActivity.SportRegistry;

import java.util.Scanner;

public class ViewLeaderboard {

    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();

        System.out.println("\n📊 View Leaderboard for a Sport");
        System.out.println("Choose a sport:");

        for (int i = 0; i < SportRegistry.SPORTS.size(); i++) {
            System.out.println((i + 1) + ". " + SportRegistry.SPORTS.get(i).getName());
        }

        System.out.print("Option (1-" + SportRegistry.SPORTS.size() + "): ");
        String input = scanner.nextLine();

        try {
            int selected = Integer.parseInt(input);
            if (selected >= 1 && selected <= SportRegistry.SPORTS.size()) {
                String sportName = SportRegistry.SPORTS.get(selected - 1).getName();
                LeaderboardDisplay.showLeaderboardForSport(sportName);
            } else {
                System.out.println("❌ Invalid option. Please choose a valid number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a number.");
        }
    }
}
