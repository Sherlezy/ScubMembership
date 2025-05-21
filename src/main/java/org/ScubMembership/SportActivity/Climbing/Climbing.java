package org.ScubMembership.SportActivity.Climbing;

import org.ScubMembership.SportActivity.abs_sport;

public class Climbing extends abs_sport {

    public Climbing() {
        super(
                "Climbing",
                new String[] {"Harness", "Climbing Shoes", "Helmet", "Rope"},
                "Salewa",
                2,
                "Climbers use ropes and gear to ascend a climbing wall. Safety and technique are crucial.",
                true,
                "Self-managed"
        );
    }
}
