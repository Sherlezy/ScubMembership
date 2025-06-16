package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;

import java.util.Scanner;

public class FriendsMenuUI {
    private static LoginPage loginPage;

    public FriendsMenuUI(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();
        while (true) {
            System.out.println("\n👥 Friends Menu:");
            System.out.println("1. Send a friend request");
            System.out.println("2. View my friends");
            System.out.println("3. Accept friend request");
            System.out.println("4. Reject friend request");
            System.out.println("5. View pending friend requests");
            System.out.println("0. Return to the main menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    new SendFriendRequestUI(loginPage).execute();
                    break;
                case "2":
                    ViewFriendsUI.execute(loginPage);
                    break;
                case "3":
                    new AcceptFriendRequestUI(loginPage).execute();
                    break;
                case "4":
                    new RejectFriendRequestUI(loginPage).execute();
                    break;
                case "5":
                    ViewPendingRequestsUI.execute(loginPage);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }

    private static void viewFriends() {
        var currentUser = loginPage.getCurrentUser();
        if (currentUser.getFriends().isEmpty()) {
            System.out.println("⚠️ You have no friends added.");
        } else {
            System.out.println("👥 Your friends:");
            for (String friend : currentUser.getFriends()) {
                System.out.println("- " + friend);
            }
        }
    }

    private static void viewPendingRequests() {
        // Refresh current user to get latest requests
        var freshUsers = org.ScubMembership.Utils.UserStorage.loadUsers();
        var freshUser = freshUsers.get(loginPage.getCurrentUser().getUsername());
        if (freshUser != null) {
            loginPage.setCurrentUser(freshUser);
        }

        var currentUser = loginPage.getCurrentUser();
        var receivedRequests = currentUser.getReceivedFriendRequest();

        if (receivedRequests.isEmpty()) {
            System.out.println("⚠️ You have no pending friend requests.");
        } else {
            System.out.println("📥 Pending friend requests:");
            for (String request : receivedRequests) {
                System.out.println("- " + request);
            }
        }
    }
}
