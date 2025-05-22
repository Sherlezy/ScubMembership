package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.SportActivity.EventManager;
import org.ScubMembership.SportActivity.SportEvent;
import org.ScubMembership.RecommendationLogic.SportUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class RegisterForEvent {

    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();
        String favoriteSport = loginPage.getCurrentUser().getPreferredSport();

        List<SportEvent> events = EventManager.getEvents();
        List<SportEvent> upcomingEvents = events.stream()
                .filter(event -> event.getDateTime().isAfter(LocalDateTime.now()))
                .toList();

        if (upcomingEvents.isEmpty()) {
            System.out.println("❌ No upcoming events available for registration.");
            return;
        }


        List<SportEvent> sortedEvents = SportUtils.sortEventsByFavoriteSportFirst(upcomingEvents, loginPage.getCurrentUser());

        System.out.println("\n📅 Upcoming Events:");
        for (int i = 0; i < sortedEvents.size(); i++) {
            SportEvent event = sortedEvents.get(i);
            boolean isFavorite = event.getSport().getName().equalsIgnoreCase(favoriteSport);
            String badge = isFavorite ? "🏅 " : "";
            System.out.println((i + 1) + ". " + badge + event);
        }

        System.out.print("Select the event you want to register for by number: ");
        try {
            int eventIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if (eventIndex < 0 || eventIndex >= sortedEvents.size()) {
                System.out.println("❌ Invalid input.");
                return;
            }

            SportEvent selectedEvent = sortedEvents.get(eventIndex);
            String username = loginPage.getCurrentUser().getUsername();

            if (selectedEvent.getRegisteredUsers().contains(username)) {
                System.out.println("⚠️ You are already registered for this event.");
                return;
            }

            boolean registered = selectedEvent.registerUser(username);
            if (registered) {
                System.out.println("✅ You have been registered for this event.");
            } else {
                System.out.println("❌ Sorry! This event is full. No more spots available.");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a number.");
        }
    }
}
