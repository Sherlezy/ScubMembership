package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.SportActivity.EventManager;
import org.ScubMembership.SportActivity.SportEvent;
import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.QuizzSystem.LeaderboardDisplay;
import org.ScubMembership.QuizzSystem.QuizMenu;
import org.ScubMembership.QuizzSystem.QuizRunner;
import org.ScubMembership.SportActivity.SportRegistry;

import java.util.List;
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
            System.out.println("4. Join a Sport Chat");
            System.out.println("5. Take your Favourite Sport Quiz");
            System.out.println("6. Take a Quiz for Another Sport");
            System.out.println("7. View a Sport's Leaderboard");
            System.out.println("8. Register for an event");
            System.out.println("9. Leave a feedback on an event");
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
                    JoinSportChat.execute(loginPage);
                    break;
                case "5":
                    TakeFavouriteSportQuiz.execute(loginPage);
                    break;
                case "6":
                    QuizMenu.show(loginPage.getCurrentUser(), scanner);
                    break;
                case "7":
                   ViewLeaderboard.execute(loginPage);
                    break;
                case "8":
                    RegisterForEvent.execute(loginPage);
                    break;
                case "9":
                    LeaveFeedback.execute(loginPage);
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
