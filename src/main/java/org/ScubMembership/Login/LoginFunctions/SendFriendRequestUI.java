package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SendFriendRequestUI {
    private final LoginPage loginPage;

    public SendFriendRequestUI(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void execute() {
        Scanner scanner = loginPage.getScanner();
        HashMap<String, NormalUser> loginInfo = loginPage.getLoginInfo();

        System.out.println("=== 🤝 Friend Request Menu ===");
        System.out.println("1. View Recommended Friends");
        System.out.println("2. Search by username");
        System.out.println("0. Back to menu");
        System.out.print("Select an option: ");

        String choice = scanner.nextLine();

        NormalUser currentUser = loginPage.getCurrentUser();

        // Refresh currentUser from disk for latest friend requests
        refreshCurrentUser();

        switch (choice) {
            case "1":
                System.out.println("⚠️ No recommended friends at the moment."); // Stub or implement later
                break;

            case "2":
                System.out.print("🔎 Enter username to search: ");
                String query = scanner.nextLine().toLowerCase();

                // Search users by username containing query (case-insensitive)
                List<NormalUser> results = loginInfo.values().stream()
                        .filter(u -> u.getUsername().toLowerCase().contains(query))
                        .filter(u -> !u.getUsername().equals(currentUser.getUsername())) // exclude self
                        .toList();

                if (results.isEmpty()) {
                    System.out.println("❌ No users found matching '" + query + "'");
                    return;
                }

                System.out.println("🔍 Search result:");
                for (int i = 0; i < results.size(); i++) {
                    NormalUser user = results.get(i);
                    System.out.printf("%d. %s (Preferred Sport: %s)%n", i + 1, user.getUsername(), user.getPreferredSport());
                }
                System.out.print("Enter the number of the user to send a friend request (0 to cancel): ");
                String input = scanner.nextLine();

                int selected;
                try {
                    selected = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input.");
                    return;
                }

                if (selected == 0) {
                    System.out.println("Cancelled.");
                    return;
                }
                if (selected < 1 || selected > results.size()) {
                    System.out.println("❌ Invalid selection.");
                    return;
                }

                NormalUser targetUser = results.get(selected - 1);

                if (currentUser.getFriends().contains(targetUser.getUsername())) {
                    System.out.println("⚠️ You are already friends with " + targetUser.getUsername());
                    return;
                }
                if (targetUser.getReceivedFriendRequest().contains(currentUser.getUsername())) {
                    System.out.println("⚠️ Friend request already sent to " + targetUser.getUsername());
                    return;
                }
                if (currentUser.getReceivedFriendRequest().contains(targetUser.getUsername())) {
                    System.out.println("⚠️ You have a pending friend request from " + targetUser.getUsername() + ". Consider accepting it.");
                    return;
                }

                // Add to targetUser receivedFriendRequests
                targetUser.getReceivedFriendRequest().add(currentUser.getUsername());
                // Add to currentUser sentFriendRequests if you track that list (optional)
                currentUser.getSentFriendRequest().add(targetUser.getUsername());

                // Put updated users back into loginInfo map
                loginInfo.put(currentUser.getUsername(), currentUser);
                loginInfo.put(targetUser.getUsername(), targetUser);

                // Save updated users
                UserStorage.saveUsers(loginInfo);

                System.out.println("✅ Friend request sent to " + targetUser.getUsername());
                break;

            case "0":
                // Back to menu
                break;

            default:
                System.out.println("❌ Invalid choice.");
                break;
        }
    }

    private void refreshCurrentUser() {
        HashMap<String, NormalUser> freshUsers = UserStorage.loadUsers();
        NormalUser freshUser = freshUsers.get(loginPage.getCurrentUser().getUsername());
        if (freshUser != null) {
            loginPage.setCurrentUser(freshUser);
        }
    }
}
