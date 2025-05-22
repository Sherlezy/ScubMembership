package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.SportActivity.EventManager;
import org.ScubMembership.SportActivity.SportEvent;
import org.ScubMembership.Login.LoginPage;

import java.util.List;
import java.util.Scanner;

public class LoginMenu {

    private LoginPage loginPage;

    public LoginMenu (LoginPage loginPage){
        this.loginPage = loginPage;
    }

    public void showLoginMenu(){
        Scanner scanner = loginPage.getScanner();

        boolean running = true;

        while (running && loginPage.isLoggedIn()) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("Logged in as: " + loginPage.getCurrentUser().getUsername());
            System.out.println("1. Change Password");
            System.out.println("2. View User Details");
            System.out.println("3. Logout");
            System.out.println("4. Join General Chat");
            System.out.println("5. Register for an event");
            System.out.print("Option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    ChangePassword changePassword = new ChangePassword(loginPage);
                    changePassword.startChangePassword();
                    break;
                case "2":
                    ViewUserDetails userDetails = new ViewUserDetails(loginPage);
                    userDetails.seeUserDetails();
                    break;
                case "3":
                    Logout logout = new Logout(loginPage);
                    logout.startLogout();
                    running = false;
                    break;
                case "4":
                    org.ScubMembership.ChatSystem.ChatClient.startChat(loginPage.getCurrentUser().getUsername());
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
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        if (!loginPage.isLoggedIn()) {
            StartLoginProcess loginProcess = new StartLoginProcess(loginPage);
            loginProcess.startLoginProcess();
        }
    }

}
