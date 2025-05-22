package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.ChatSystem.ChatClient;
import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.SportActivity.abs_sport;
import org.ScubMembership.RecommendationLogic.SportUtils;

import java.util.List;
import java.util.Scanner;

public class JoinSportChat {

    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();

        List<abs_sport> sortedSports = SportUtils.getSortedSportsWithFavoriteFirst(loginPage.getCurrentUser());

        System.out.println("\n🏟️ Available sport chat rooms (Your favourite is listed first):");
        for (int i = 0; i < sortedSports.size(); i++) {
            abs_sport sport = sortedSports.get(i);
            String label = sport.getName().equalsIgnoreCase(loginPage.getCurrentUser().getPreferredSport()) ? " 🏅" : "";
            System.out.println((i + 1) + ". " + sport.getName() + label);
        }

        System.out.print("Enter number to join chat: ");
        String input = scanner.nextLine();

        try {
            int sportChoice = Integer.parseInt(input);
            if (sportChoice < 1 || sportChoice > sortedSports.size()) {
                System.out.println("❌ Invalid sport number.");
                return;
            }

            String selectedSport = sortedSports.get(sportChoice - 1).getName();
            ChatClient.startSportChat(
                    selectedSport,
                    loginPage.getCurrentUser().getUsername()
            );
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number.");
        }
    }
}
