package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class RejectFriendRequestUI {
    private final LoginPage loginPage;

    public RejectFriendRequestUI(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void execute() {
        Scanner scanner = loginPage.getScanner();
        HashMap<String, NormalUser> loginInfo = loginPage.getLoginInfo();

        // Refresh current user
        refreshCurrentUser();
        NormalUser currentUser = loginPage.getCurrentUser();

        List<String> receivedRequests = currentUser.getReceivedFriendRequest();

        if (receivedRequests.isEmpty()) {
            System.out.println("⚠️ No friend requests to reject.");
            return;
        }

        System.out.println("📥 Friend requests received:");
        for (int i = 0; i < receivedRequests.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, receivedRequests.get(i));
        }
        System.out.print("Enter the number of the friend request to reject (0 to cancel): ");

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
        if (selected < 1 || selected > receivedRequests.size()) {
            System.out.println("❌ Invalid selection.");
            return;
        }

        String senderUsername = receivedRequests.get(selected - 1);

        NormalUser senderUser = loginInfo.get(senderUsername);
        if (senderUser == null) {
            System.out.println("❌ User " + senderUsername + " not found.");
            return;
        }

        // Remove the friend request from currentUser's received and sender's sent lists
        currentUser.getReceivedFriendRequest().remove(senderUsername);
        senderUser.getSentFriendRequest().remove(currentUser.getUsername());

        // Update the map with modified users
        loginInfo.put(currentUser.getUsername(), currentUser);
        loginInfo.put(senderUser.getUsername(), senderUser);

        // Save the updated user data
        UserStorage.saveUsers(loginInfo);

        System.out.println("✅ Friend request rejected.");
    }

    private void refreshCurrentUser() {
        HashMap<String, NormalUser> freshUsers = UserStorage.loadUsers();
        NormalUser freshUser = freshUsers.get(loginPage.getCurrentUser().getUsername());
        if (freshUser != null) {
            loginPage.setCurrentUser(freshUser);
        }
    }
}
