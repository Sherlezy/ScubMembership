package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.SportActivity.EventManager;
import org.ScubMembership.SportActivity.Feedback;
import org.ScubMembership.SportActivity.SportEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeaveFeedback {
    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();
        String currentUser = loginPage.getCurrentUser().getUsername();

        List<SportEvent> allEvents = EventManager.getEvents();
        List<SportEvent> attendedEvents = allEvents.stream()
                .filter(event -> event.getRegisteredUsers().contains(currentUser))
                .filter(event -> event.getDateTime().isBefore(LocalDateTime.now()))
                .toList();

        List<SportEvent> notYetReviewed = new ArrayList<>();

        if(attendedEvents.isEmpty()) {
            System.out.println("⚠\uFE0F You haven't participated in any events yet.");
            return;
        }

        for(SportEvent event : attendedEvents) {
            if(!event.hasFeedbackFrom(loginPage.getCurrentUser().getUsername())) {
                notYetReviewed.add(event);
            }
        }

        if(notYetReviewed.isEmpty()) {
            System.out.println("✅ You've reviewed all events you've attended!");
            return;
        }

        System.out.println("\uD83D\uDCCB Events you haven't reviewed yet: ");
        for(int i = 0; i < notYetReviewed.size(); i++) {
            System.out.println((i+1) + ". " + notYetReviewed.get(i));
        }

        System.out.print("Select the event number you want to review: ");
        int eventIndex;
        try {
            eventIndex = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input.");
            return;
        }

        if(eventIndex < 0 || eventIndex >= allEvents.size()) {
            System.out.println("❌ Invalid input.");
            return;
        }

        SportEvent selectedEvent = notYetReviewed.get(eventIndex);

        System.out.print("Enter rating (1-5): ");
        double rating;
        try {
            rating = Float.parseFloat(scanner.nextLine());
            if(rating < 0 || rating > 5) {
                System.out.println("❌ Rating must be between 1 and 5.");
                return;
            }
        } catch (NumberFormatException e ) {
            System.out.println("❌ Invalid rating format.");
            return;
        }
        rating = (double) Math.round((rating * 10) / 10.0);

        System.out.println("Write your feedback comment: ");
        String comment = scanner.nextLine();

        Feedback feedback = new Feedback(currentUser, rating, comment);
        selectedEvent.addFeedback(feedback);
        EventManager.saveEvents();

        System.out.println("✅ Feedback submitted. Thank you!");
    }
}
