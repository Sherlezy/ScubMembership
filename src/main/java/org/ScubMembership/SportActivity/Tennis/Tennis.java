package org.ScubMembership.SportActivity.Tennis;

import org.ScubMembership.SportActivity.abs_sport;

public class Tennis extends abs_sport {
    public Tennis() {
        super(
               "Tennis",
                new String[] {"Tennis Racket", "Tennis Balls", "Tennis Shoes"},
                "Tennis Court",
                2,
                "Players use rackets to hit a ball over a net. Points are scored when the opponent fails to "
                        + "return the ball within bounds.",
                false,
                "Alessandro Ercolani"
        );
    }
}
