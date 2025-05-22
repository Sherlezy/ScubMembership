package org.ScubMembership.SportActivity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SportEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String sportName;
    private final String eventName;
    private final String date;
    private final int maxParticipants;
    private final Set<String> registeredUsers;

    public SportEvent(String sportName, String eventName, String date, int maxParticipants) {
        this.sportName = sportName;
        this.eventName = eventName;
        this.date = date;
        this.maxParticipants = maxParticipants;
        this.registeredUsers = new HashSet<>();
    }

    public String getSportName() {
        return sportName;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDate() {
        return date;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public Set<String> getRegisteredUsers() {
        return registeredUsers;
    }

    public boolean registerUser(String username) {
        if (registeredUsers.size() >= maxParticipants) return false;
        return registeredUsers.add(username);
    }

    @Override
    public String toString() {
        return eventName + " (" + sportName + ") on " + date + " [" + registeredUsers.size() + "/" + maxParticipants + " registered]";
    }
}
