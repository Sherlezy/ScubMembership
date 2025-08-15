package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Friends.AbstractFriendRequestUI;
import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;

public class RejectFriendRequestUI extends AbstractFriendRequestUI {
    public RejectFriendRequestUI(LoginPage loginPage) {
        super(loginPage);
    }

    @Override
    protected void handleRequest(NormalUser currentUser, NormalUser senderUser) {
        // Remove from received and sent lists
        currentUser.getReceivedFriendRequest().remove(senderUser.getUsername());
        senderUser.getSentFriendRequest().remove(currentUser.getUsername());

        System.out.println("❌ Friend request from " + senderUser.getUsername() + " rejected!");
    }
}
