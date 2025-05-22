package org.ScubMembership.SportActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SportEvent {
    private abs_sport sport;
    private LocalDateTime dateTime;
    private int maxParticipants;
    private List<String> registeredUsers = new ArrayList<>();

    public SportEvent(abs_sport sport, LocalDateTime dateTime, int maxParticipants) {
        this.sport = sport;
        this.dateTime = dateTime;
        this.maxParticipants = maxParticipants;
    }

    public boolean registerUser(String username) {
        if(registeredUsers.size() >= maxParticipants || registeredUsers.contains(username)) {
            return false;
        }
        registeredUsers.add(username);
        return true;
    }

    public List<String> getRegisteredUsers() {
        return registeredUsers;
    }

    public abs_sport getSport() {
        return sport;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    @Override
    public String toString() {
        return "Event for " + sport.getName() + " on " + dateTime + " (Max " + maxParticipants + ", Registered: " +
                registeredUsers.size() + ")";
    }
}
