package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;

import java.util.*;

import static org.ScubMembership.Friends.FriendRecommender.getRecommendedFriends;
import static org.ScubMembership.Friends.FriendRequest.sendFriendRequest;

public class SendFriendRequestUI {

    public static void execute(LoginPage loginPage, List<NormalUser> allUsers) {
        Scanner scanner = loginPage.getScanner();
        NormalUser currentUser = loginPage.getCurrentUser();

        System.out.println("\uD83D\uDD0D Recommended users to add as friends:");
        List<NormalUser> recommended = getRecommendedFriends(currentUser, allUsers);

        if(recommended.isEmpty()) {
            System.out.println("⚠\uFE0F No recommended friends at the moment.");
            return;
        }

        for(int i = 0; i < recommended.size(); i++) {
            NormalUser user = recommended.get(i);
            System.out.println((i+1) + ". " + user.getUsername() + " (Preferred Sport: " + user.getPreferredSport()
                    + ")");
        }

        System.out.println("Enter the number of the user to send a friend request (0 to cancel): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if(choice <= 0 || choice > recommended.size()) {
                System.out.println("❌ Invalid selection. Returning...");
                return;
            }

            NormalUser selected = recommended.get(choice - 1);
            boolean success = sendFriendRequest(currentUser, selected);
            if(success) {
                System.out.println("✅ Friend request sent to " + selected.getUsername());
            } else {
                System.out.println("⚠\uFE0F Could not send request (maybe already friends or request sent)");
            }
        } catch(NumberFormatException e) {
            System.out.println("❌ Invalid input");
        }
    }
}
