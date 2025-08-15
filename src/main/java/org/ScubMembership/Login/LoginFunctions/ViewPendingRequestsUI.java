package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;
import java.util.List;

public class ViewPendingRequestsUI {

    public static void execute(LoginPage loginPage) {
        HashMap<String, NormalUser> Users = UserStorage.loadUsers();
        NormalUser freshUser = Users.get(loginPage.getCurrentUser().getUsername());
        if (freshUser != null) {
            loginPage.setCurrentUser(freshUser);
        }
        NormalUser currentUser = loginPage.getCurrentUser();
        List<String> pending = currentUser.getSentFriendRequest().stream()
                .filter(username -> !currentUser.getFriends().contains(username))
                .toList();

        System.out.println("\n\uD83D\uDCE4Pending friend requests:");
        if(pending.isEmpty()) {
            System.out.println("You have no pending friend requests.");
            return;
        }

        for(String username : pending) {
            System.out.println("• " + username);
        }
    }
}
