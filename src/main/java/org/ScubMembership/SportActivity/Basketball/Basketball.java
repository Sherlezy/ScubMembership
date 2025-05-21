package org.ScubMembership.SportActivity.Basketball;

import org.ScubMembership.SportActivity.abs_sport;

public class Basketball extends abs_sport {

    public Basketball() {
        super(
                "Basketball",
                new String[] {"Basketball Shoes", "Jerseys"},
                "Talvera",
                10,
                "Two teams of five players each try to score by shooting a ball through a hoop."
                        + "The game consists of four quarters (each of 10 minutes). The team with the most points wins.",
                true,
                "Lorenzo Vezzani"
        );
    }
}