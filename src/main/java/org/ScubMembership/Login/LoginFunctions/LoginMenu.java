package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.QuizzSystem.LeaderboardDisplay;
import org.ScubMembership.QuizzSystem.QuizMenu;
import org.ScubMembership.QuizzSystem.QuizRunner;

import java.util.Scanner;

public class LoginMenu {

    private final LoginPage loginPage;

    public LoginMenu(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void showLoginMenu() {
        Scanner scanner = loginPage.getScanner();

        boolean running = true;

        while (running && loginPage.isLoggedIn()) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("Logged in as: " + loginPage.getCurrentUser().getUsername());
            System.out.println("1. Change Password");
            System.out.println("2. View User Details");
            System.out.println("3. Logout");
            System.out.println("4. Join General Chat");
            System.out.println("5. Take your Favourite Sport Quiz");
            System.out.println("6. Take a Quiz for Another Sport");
            System.out.println("7. View a Sport's Leaderboard");

            System.out.print("Option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    new ChangePassword(loginPage).startChangePassword();
                    break;
                case "2":
                    new ViewUserDetails(loginPage).seeUserDetails();
                    break;
                case "3":
                    new Logout(loginPage).startLogout();
                    running = false;
                    break;
                case "4":
                    org.ScubMembership.ChatSystem.ChatClient.startChat(
                            loginPage.getCurrentUser().getUsername()
                    );
                    break;
                case "5":
                    String favSport = loginPage.getCurrentUser().getPreferredSport();
                    QuizRunner.startQuiz(favSport, scanner, loginPage.getCurrentUser());
                    break;
                case "6":
                    QuizMenu.show(loginPage.getCurrentUser(), scanner);
                    break;
                case "7":
                    System.out.print("Enter sport to view leaderboard: ");
                    String sport = scanner.nextLine().trim();
                    LeaderboardDisplay.showLeaderboardForSport(sport);
                    break;
                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }

        if (!loginPage.isLoggedIn()) {
            new StartLoginProcess(loginPage).startLoginProcess();
        }
    }
}
