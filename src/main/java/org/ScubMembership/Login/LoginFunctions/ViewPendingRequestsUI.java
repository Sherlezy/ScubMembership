package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;

import java.util.List;

public class ViewPendingRequestsUI {

    public static void execute(LoginPage loginPage) {
        NormalUser currentUser = loginPage.getCurrentUser();
        List<String> pending = currentUser.getSentFriendRequest();

        System.out.println("\n\uD83D\uDCE4Pending friend requests:");
        if(pending == null || pending.isEmpty()) {
            System.out.println("You have no pending friend requests.");
            return;
        }

        for(String username : pending) {
            System.out.println("• " + username);
        }
    }
}
