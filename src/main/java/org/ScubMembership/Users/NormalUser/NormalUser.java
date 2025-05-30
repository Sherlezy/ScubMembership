package org.ScubMembership.Users.NormalUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NormalUser implements Serializable {
    private static final long serialVersionUID = 1L;
    public String email;
    public String username;
    public String password;
    public int ID;
    public String preferredSport;
    private String preferredTimeSlot;  // NEW FIELD
    private List<String> friends = new ArrayList<>(); // NEW FIELD
    private List<String> sentFriendRequest = new ArrayList<>(); // NEW FIELD
    private List<String> receivedFriendRequest = new ArrayList<>(); // NEW FIELD

    public NormalUser(String email, String username, String password, int ID, String preferredSport, String preferredTimeSlot) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.ID = ID;
        this.preferredSport = preferredSport;
        this.preferredTimeSlot = preferredTimeSlot;
    }

    public String getPreferredTimeSlot() {
        return preferredTimeSlot;
    }

    public void setPreferredTimeSlot(String preferredTimeSlot) {
        this.preferredTimeSlot = preferredTimeSlot;
    }

    public List<String> getFriends() {
        if(friends == null) friends = new ArrayList<>();
        return friends;
    }

    public List<String> getSentFriendRequest() {
        if(sentFriendRequest == null) sentFriendRequest = new ArrayList<>();
        return sentFriendRequest;
    }

    public List<String> getReceivedFriendRequest() {
        if(receivedFriendRequest == null) receivedFriendRequest = new ArrayList<>();
        return receivedFriendRequest;
    }

    public void sendFriendRequest(String toUsername) {
        if(!sentFriendRequest.contains(toUsername)) {
            sentFriendRequest.add(toUsername);
        }
    }

    public void receiveFriendRequest(String fromUsername) {
        if(!receivedFriendRequest.contains(fromUsername)) {
            receivedFriendRequest.add(fromUsername);
        }
    }

    public void acceptFriendRequest(String fromUsername) {
        if(receivedFriendRequest.remove(fromUsername)) {
            friends.add(fromUsername);
        }
    }

    public void confirmFriendship(String otherUsername) {
        if(!friends.contains(otherUsername)) {
            friends.add(otherUsername);
        }
    }

    public void rejectFriendRequest(String fromUsername) {
        receivedFriendRequest.remove(fromUsername);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPreferredSport(){
        return preferredSport;
    }

    public void setPreferredSport(String preferredSport){
        this.preferredSport = preferredSport;
    }
}
