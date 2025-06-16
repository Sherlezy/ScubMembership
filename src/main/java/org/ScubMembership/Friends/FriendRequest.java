package org.ScubMembership.Friends;

import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;

public class FriendRequest {

    public static boolean sendFriendRequest(NormalUser sender, NormalUser receiver){
        if (sender == null || receiver == null || sender.equals(receiver)) return false;

        if (receiver.getReceivedFriendRequest().contains(sender.getUsername()) ||
                receiver.getFriends().contains(sender.getUsername())) {
            return false;
        }

        sender.sendFriendRequest(receiver.getUsername());
        receiver.receiveFriendRequest(sender.getUsername());

        HashMap<String, NormalUser> users = UserStorage.loadUsers();
        users.put(sender.getUsername(), sender);
        users.put(receiver.getUsername(), receiver);
        UserStorage.saveUsers(users);

        System.out.println("After saving users:");
        UserStorage.loadUsers().forEach((k,v) -> {
            System.out.println(k + " receivedFriendRequests: " + v.getReceivedFriendRequest());
        });

        return true;
    }

    public static boolean acceptFriendRequest(NormalUser sender, NormalUser receiver) {
        if (!receiver.getReceivedFriendRequest().contains(sender.getUsername())) {
            return false;
        }

        receiver.getReceivedFriendRequest().remove(sender.getUsername());
        sender.getSentFriendRequest().remove(receiver.getUsername());

        receiver.getFriends().add(sender.getUsername());
        sender.getFriends().add(receiver.getUsername());

        HashMap<String, NormalUser> users = UserStorage.loadUsers();
        users.put(sender.getUsername(), sender);
        users.put(receiver.getUsername(), receiver);
        UserStorage.saveUsers(users);

        return true;
    }

    public static boolean rejectFriendRequest(NormalUser sender, NormalUser receiver){
        if (!receiver.getReceivedFriendRequest().contains(sender.getUsername())) return false;

        receiver.getReceivedFriendRequest().remove(sender.getUsername());
        sender.getSentFriendRequest().remove(receiver.getUsername());

        HashMap<String, NormalUser> users = UserStorage.loadUsers();
        users.put(sender.getUsername(), sender);
        users.put(receiver.getUsername(), receiver);
        UserStorage.saveUsers(users);

        return true;
    }

    public static boolean areFriend(NormalUser user1, NormalUser user2){
        return user1.getFriends().contains(user2.getUsername()) &&
                user2.getFriends().contains(user1.getUsername());
    }
}
