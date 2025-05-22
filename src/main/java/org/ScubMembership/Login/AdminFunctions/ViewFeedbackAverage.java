package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.SportActivity.EventManager;
import org.ScubMembership.SportActivity.SportEvent;

import java.time.LocalDateTime;
import java.util.List;

public class ViewFeedbackAverage {
    public static void execute() {
        List<SportEvent> pastEvents = EventManager.getEvents().stream()
                .filter(event -> event.getDateTime().isBefore(LocalDateTime.now()))
                .toList();

        if(pastEvents.isEmpty()) {
            System.out.println("\uD83D\uDCED No past events found.");
            return;
        }

        System.out.println("\uD83D\uDCCA Average Feedback for Past Events: ");
        for(SportEvent event : pastEvents) {
            double averageRating = event.getAverageRating();
            if(averageRating == 0.0) {
                System.out.println("- "+ event.getSport().getName() + " at " + event.getSport().getGymName()
                        + " on " + event.getDateTime() + ": No feedback submitted.");
            } else {
                System.out.println("⭐ " + event.getSport().getName() + " at " + event.getSport().getGymName()
                        + " om " + event.getDateTime() + " -> Average rating: " + averageRating);
            }
        }
    }
}
