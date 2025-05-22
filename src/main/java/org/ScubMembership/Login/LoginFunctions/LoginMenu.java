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
                            break;
                        }

                        String selectedSport = SportRegistry.SPORTS.get(sportChoice - 1).getName();
                        org.ScubMembership.ChatSystem.ChatClient.startSportChat(
                                selectedSport, loginPage.getCurrentUser().getUsername()
                        );
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Please enter a valid number.");
                    }
                    break;

                case "8":
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
                case "5":
                    List<SportEvent> events = EventManager.getEvents();
                    if(events.isEmpty()) {
                        System.out.println("❌ No events available.");
                        break;
                    }

                    System.out.println("\uD83D\uDCC5 Available Events: ");
                    for(int i = 0; i < events.size(); i++) {
                        System.out.println((i+1) + ". " + events.get(i));
                    }

                    System.out.println("Select the event you want to register by number: ");
                    try {
                        int eventIndex = Integer.parseInt(scanner.nextLine()) - 1;
                        if(eventIndex < 0 || eventIndex >= events.size()) {
                            System.out.println("❌ Invalid input.");
                            break;
                        }

                        SportEvent event = events.get(eventIndex);
                        String username = loginPage.getCurrentUser().getUsername();

                        if(event.getRegisteredUsers().contains(username)) {
                            System.out.println("⚠\uFE0F You are already registered for this event.");
                            break;
                        }

                        boolean registered = event.registerUser(username);
                        if(registered) {
                            System.out.println("✅ You have been registered for this event.");
                        } else {
                            System.out.println("❌ Sorry! This event is full. No more spots available.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid input.");
                    }
                    break;
                case "9":
                    events = EventManager.getEvents();
                    String currentUser = loginPage.getCurrentUser().getUsername();
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
