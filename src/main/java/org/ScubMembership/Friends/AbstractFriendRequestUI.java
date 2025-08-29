package org.ScubMembership.Friends;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractFriendRequestUI extends AbstractFriends{
    protected AbstractFriendRequestUI(LoginPage loginPage) {
        super(loginPage);
    }

    public void execute() {
        refreshCurrentUser();
        NormalUser currentUser = getCurrentUser();
        HashMap<String, NormalUser> loginInfo = getLoginInfo();

        List<String> receivedRequests = new ArrayList<>(currentUser.getReceivedFriendRequest());

        System.out.println();

        if(receivedRequests.isEmpty()) {
            System.out.println("⚠\uFE0F No friend requests.");
            return;
        }

        System.out.println("📥 Friend requests received:");
        for (int i = 0; i < receivedRequests.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, receivedRequests.get(i));
        }

        System.out.printf("Enter the number of the friend request to %s (0 to cancel): ", getActionVerb());
        int selected = getValidSelection(receivedRequests.size());
        if(selected == 0) {
            System.out.println("Cancelled");
            return;
        }

        String senderUsername = receivedRequests.get(selected - 1);
        NormalUser senderUser = loginInfo.get(senderUsername);

        if(senderUser == null) {
            System.out.println("❌ User not found");
            return;
        }

        handleRequest(currentUser, senderUser);

        loginInfo.put(currentUser.getUsername(), currentUser);
        loginInfo.put(senderUser.getUsername(), senderUser);
        UserStorage.saveUsers(loginInfo);
    }

    protected abstract void handleRequest(NormalUser currentUser, NormalUser senderUser);

    protected abstract String getActionVerb();

    private int getValidSelection(int max) {
        try {
            int selection = Integer.parseInt(loginPage.getScanner().nextLine());
            if(selection < 0 ||selection > max) {return -1;}
            return selection;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
