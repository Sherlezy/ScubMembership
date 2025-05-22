package org.ScubMembership.SportActivity;

public class Feedback {
    private String username;
    private double rating;
    private String comment;

    public Feedback(String username, double rating, String comment) {
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return username + " (" + rating + "/5): " + comment;
    }
}
