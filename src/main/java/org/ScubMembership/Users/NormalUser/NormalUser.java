package org.ScubMembership.Users.NormalUser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NormalUser implements Serializable {
    private static final long serialVersionUID = 1L;
    public String email;
    public String username;
    public String password;
    public int ID;
    public String preferredSport;
    private String preferredTimeSlot;
    private Set<String> friends = new HashSet<>();
    private Set<String> sentFriendRequest = new HashSet<>();
    private Set<String> receivedFriendRequest = new HashSet<>();

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

    public Set<String> getFriends() {
        if(friends == null) friends = new HashSet<>();
        return friends;
    }

    public Set<String> getSentFriendRequest() {
        if(sentFriendRequest == null) sentFriendRequest = new HashSet<>();
        return sentFriendRequest;
    }

    public Set<String> getReceivedFriendRequest() {
        if(receivedFriendRequest == null) receivedFriendRequest = new HashSet<>();
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

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if(friends == null) friends = new HashSet<>();
        if(sentFriendRequest == null) sentFriendRequest = new HashSet<>();
        if(receivedFriendRequest == null) receivedFriendRequest = new HashSet<>();
    }
}
