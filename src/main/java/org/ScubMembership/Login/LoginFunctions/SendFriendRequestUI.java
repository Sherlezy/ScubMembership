package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Friends.AbstractFriends;
import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static org.ScubMembership.Friends.FriendRecommender.getRecommendedFriends;

public class SendFriendRequestUI extends AbstractFriends {
    public SendFriendRequestUI(LoginPage loginPage) {
        super(loginPage);
    }

    @Override
    public void execute() {
        Scanner scanner = loginPage.getScanner();
        HashMap<String, NormalUser> loginInfo = loginPage.getLoginInfo();

        System.out.println();
        System.out.println("=== 🤝 Friend Request Menu ===");
        System.out.println("1. View Recommended Friends");
        System.out.println("2. Search by username");
        System.out.println("0. Back to menu");
        System.out.print("Select an option: ");

        String choice = scanner.nextLine();

        // Refresh currentUser from disk for latest friend requests
        refreshCurrentUser();
        NormalUser currentUser = loginPage.getCurrentUser();
        
        switch (choice) {
            case "1":
                List<NormalUser> recommendedFriends = getRecommendedFriends(currentUser);
                if(recommendedFriends.isEmpty()) {
                    System.out.println("⚠️ No recommended friends at the moment.");
                } else {
                    System.out.println("🔍 Search result:");
                    for (int i = 0; i < recommendedFriends.size(); i++) {
                        NormalUser user = recommendedFriends.get(i);
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
                    if (selected < 1 || selected > recommendedFriends.size()) {
                        System.out.println("❌ Invalid selection.");
                        return;
                    }

                    NormalUser targetUser = recommendedFriends.get(selected - 1);

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
                }
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
}
