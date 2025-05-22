package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.SportActivity.EventManager;
import org.ScubMembership.SportActivity.abs_sport;


import java.util.List;

public class CreateNewSportEvent {

    public static void execute(List<abs_sport> sports) {
        if (sports == null || sports.isEmpty()) {
            System.out.println("⚠️ No sports available. Please add a sport first.");
        } else {
            EventManager.createEvent(sports);
        }
    }
}
