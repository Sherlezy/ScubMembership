package org.ScubMembership.QuizzSystem;

import org.ScubMembership.SportActivity.abs_sport;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.RecommendationLogic.SportUtils;

import java.util.List;
import java.util.Scanner;

public class QuizMenu {

    public static void show(NormalUser user, Scanner scanner) {
        while (true) {
            List<abs_sport> sortedSports = SportUtils.getSortedSportsWithFavoriteFirst(user);

            System.out.println("\n🎯 Quiz Menu:");
            for (int i = 0; i < sortedSports.size(); i++) {
                System.out.println((i + 1) + ". Take quiz: " + sortedSports.get(i).getName());
            }
            System.out.println("L. 📊 View leaderboard");
            System.out.println("0. Exit quiz menu");

            System.out.print("Your choice: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("0")) return;

            if (input.equalsIgnoreCase("L")) {
                System.out.println("Which sport's leaderboard would you like to see?");
                for (int i = 0; i < sortedSports.size(); i++) {
                    System.out.println((i + 1) + ". " + sortedSports.get(i).getName());
                }

                System.out.print("Enter number: ");
                String sportInput = scanner.nextLine();
                try {
                    int sportIndex = Integer.parseInt(sportInput);
                    if (sportIndex >= 1 && sportIndex <= sortedSports.size()) {
                        String sportName = sortedSports.get(sportIndex - 1).getName();
                        LeaderboardDisplay.showLeaderboardForSport(sportName);
                    } else {
                        System.out.println("❌ Invalid number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Please enter a valid number.");
                }
                continue;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= sortedSports.size()) {
                    String selectedSport = sortedSports.get(choice - 1).getName();
                    QuizRunner.startQuiz(selectedSport, scanner, user);
                } else {
                    System.out.println("❌ Please select a valid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input.");
            }
        }
    }
}
