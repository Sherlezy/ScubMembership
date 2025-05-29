package org.ScubMembership.Friends;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FriendManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String requestFromUser;
    private final String requestToUser;
    private final LocalDateTime timestamp;

    public FriendManager(String requestFromUser, String requestToUser, String timestamp) {
        this.requestFromUser = requestFromUser;
        this.requestToUser = requestToUser;
        this.timestamp = LocalDateTime.now();
    }

    public String getRequestFromUser() {
        return requestFromUser;
    }

    public String getRequestToUser() {
        return requestToUser;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
