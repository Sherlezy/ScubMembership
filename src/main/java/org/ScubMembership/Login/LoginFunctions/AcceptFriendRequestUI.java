package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.ScubMembership.Friends.FriendRequest.acceptFriendRequest;

public class AcceptFriendRequestUI {
    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();
        NormalUser currentUser = loginPage.getCurrentUser();
        Map<String, NormalUser> allUsers = UserStorage.loadUsers();

        List<String> requests = currentUser.getReceivedFriendRequest();
        if(requests == null || requests.isEmpty()) {
            System.out.println("\uD83D\uDCED No friend requests to accept");
            return;
        }

        System.out.println("\uD83D\uDCED Received friend requests: ");
        for(int i = 0; i < requests.size(); i++) {
            System.out.println((i+1) + ". " + requests.get(i));
        }

        System.out.println("Enter the number to accept (0 to cancel): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if(choice == 0) return;
            if(choice < 0 || choice > requests.size()) {
                System.out.println("❌ Invalid selection. Returning...");
                return;
            }

            String selectedUsername = requests.get(choice - 1);
            NormalUser sender = allUsers.get(selectedUsername);
            if(sender == null) {
                System.out.println("❌ User not found.");
                return;
            }

            boolean accepted = acceptFriendRequest(sender, currentUser);
            if(accepted) {
                System.out.println("✅ You are now friend with " + selectedUsername);
                UserStorage.saveUsers((HashMap<String, NormalUser>) allUsers);
            } else {
                System.out.println("❌ Could not accept the friend request.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input.");
        }
    }
}
