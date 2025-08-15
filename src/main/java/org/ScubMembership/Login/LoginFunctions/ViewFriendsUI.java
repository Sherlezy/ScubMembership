package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;

import java.util.*;

public class ViewFriendsUI {
    public static void execute (LoginPage loginPage) {
        NormalUser currentUser = loginPage.getCurrentUser();
        Set<String> friends = currentUser.getFriends();

        System.out.println("\n\uD83D\uDC65 Your friends: ");
        if(friends == null || friends.isEmpty()) {
            System.out.println("You have no friends yet.");
            return;
        }

        for(String friend : friends) {
            System.out.println("• " + friend);
        }
    }
}
