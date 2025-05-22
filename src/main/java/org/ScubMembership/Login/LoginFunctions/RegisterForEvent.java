package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.SportActivity.EventManager;
import org.ScubMembership.SportActivity.SportEvent;

import java.util.List;
import java.util.Scanner;

public class RegisterForEvent {

    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();
        List<SportEvent> events = EventManager.getEvents();

        if (events.isEmpty()) {
            System.out.println("❌ No events available.");
            return;
        }

        System.out.println("\n📅 Available Events:");
        for (int i = 0; i < events.size(); i++) {
            System.out.println((i + 1) + ". " + events.get(i));
        }

        System.out.print("Select the event you want to register for by number: ");
        try {
            int eventIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if (eventIndex < 0 || eventIndex >= events.size()) {
                System.out.println("❌ Invalid input.");
                return;
            }

            SportEvent selectedEvent = events.get(eventIndex);
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
