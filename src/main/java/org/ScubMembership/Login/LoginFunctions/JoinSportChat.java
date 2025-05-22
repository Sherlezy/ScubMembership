package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.ChatSystem.ChatClient;
import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.SportActivity.SportRegistry;

import java.util.Scanner;

public class JoinSportChat {

    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();

        System.out.println("\n🏟️ Available sport chat rooms:");
        for (int i = 0; i < SportRegistry.SPORTS.size(); i++) {
            System.out.println((i + 1) + ". " + SportRegistry.SPORTS.get(i).getName());
        }

        System.out.print("Enter number to join chat: ");
        String input = scanner.nextLine();

        try {
            int sportChoice = Integer.parseInt(input);
            if (sportChoice < 1 || sportChoice > SportRegistry.SPORTS.size()) {
                System.out.println("❌ Invalid sport number.");
                return;
            }

            String selectedSport = SportRegistry.SPORTS.get(sportChoice - 1).getName();
            ChatClient.startSportChat(
                    selectedSport,
                    loginPage.getCurrentUser().getUsername()
            );
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number.");
        }
    }
}
