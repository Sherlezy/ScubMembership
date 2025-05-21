package org.ScubMembership.SportActivity.Running;

import org.ScubMembership.SportActivity.abs_sport;

public class Running extends abs_sport {
    public Running() {
        super(
                "Running",
                 new String[] {"Running Shoes"},
                "Outdoor",
                1,
                "Participants run over a set distance on a track or route. The fastest runner wins.",
                false,
                "Self-managed"
        );
    }
}
