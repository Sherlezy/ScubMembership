package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.SportActivity.abs_sport;
import java.time.LocalDateTime;

public class SportEvent {
    private abs_sport sport;
    private LocalDateTime dateTime;
    private int maxParticipants;

    public SportEvent(abs_sport sport, LocalDateTime dateTime, int maxParticipants) {
        this.sport = sport;
        this.dateTime = dateTime;
        this.maxParticipants = maxParticipants;
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
        return "Event for " + sport.getName() + " on " + dateTime + " (Max " + maxParticipants + " participants)";
    }
}
