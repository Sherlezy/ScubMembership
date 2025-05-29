package org.ScubMembership.Friends;

import org.ScubMembership.Users.NormalUser.NormalUser;
public class FriendRequest {

    public static boolean sendFriendRequest(NormalUser sender, NormalUser receiver){
        if (sender.getUsername().equals(receiver.getUsername())) return false;
        if (sender.getFriends().contains(receiver.getUsername())) return false;
        if (sender.getSentFriendRequest().contains(receiver.getUsername())) return false;
        sender.sendFriendRequest(receiver.getUsername());
        receiver.receiveFriendRequest(sender.getUsername());
        return true;
    }

    public static boolean acceptFriendRequest(NormalUser sender, NormalUser receiver){
        if (!receiver.getSentFriendRequest().contains(sender.getUsername())) return false;

        receiver.acceptFriendRequest(sender.getUsername());
        sender.confirmFriendship(receiver.getUsername());

        sender.getSentFriendRequest().remove(sender.getUsername());
        receiver.getReceivedFriendRequest().remove(receiver.getUsername());

        return true;
    }

    public static boolean rejectFriendRequest(NormalUser sender, NormalUser receiver){
        if (!receiver.getSentFriendRequest().contains(sender.getUsername())) return false;

        receiver.rejectFriendRequest(sender.getUsername());
        sender.getSentFriendRequest().remove(receiver.getUsername());

        return true;
    }

    public static boolean areFriend(NormalUser user1, NormalUser user2){
        return user1.getFriends().contains(user2.getUsername()) &&
                user2.getFriends().contains(user1.getUsername());
    }
}

