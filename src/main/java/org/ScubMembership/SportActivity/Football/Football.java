package org.ScubMembership.SportActivity.Football;

import org.ScubMembership.SportActivity.abs_sport;

public class Football extends abs_sport {

    public Football() {
        super(
                "Football",
                new String[] {"Football Cleats", "Jerseys", "Shin Guards"},
                "Talvera",
                16,
                "Two teams of 8 players each try to score by getting ball into the opponent's goal."
                        + "The game is played in two halves of 45 minutes. The team with the most goals wins.",
                false,
                "Andrea Ziccarelli"
        );
    }
}