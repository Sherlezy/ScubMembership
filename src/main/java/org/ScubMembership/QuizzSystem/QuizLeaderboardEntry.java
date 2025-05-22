package org.ScubMembership.QuizzSystem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuizLeaderboardEntry implements Serializable {
    private static final long serialVersionUID = 2L;

    private final String username;
    private final String sport;
    private final int score;
    private final LocalDateTime timestamp;
    private final boolean isFavorite;

    public QuizLeaderboardEntry(String username, String sport, int score, boolean isFavorite) {
        this.username = username;
        this.sport = sport;
        this.score = score;
        this.isFavorite = isFavorite;
        this.timestamp = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public String getSport() {
        return sport;
    }

    public int getScore() {
        return score;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        String badge = isFavorite ? " ★ Favorite Sport" : "";
        String date = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return username + " — " + score + " pts in " + sport + badge + " [" + date + "]";
    }
}
