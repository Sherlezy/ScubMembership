package org.ScubMembership.SportActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventManager {
    private static final List<SportEvent> events = new ArrayList<>();

    public static void createEvent(List<abs_sport> availableSports) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\uD83D\uDCC5 Create a new Event");

        System.out.println("Available Sports: ");
        for(int i = 0; i < availableSports.size(); i++) {
            System.out.println((i+1) + ". " + availableSports.get(i).getName());
        }

        System.out.println("Select sport by number: ");
        int sportIndex = Integer.parseInt(scanner.nextLine())- 1;

        if(sportIndex < 0 || sportIndex >= availableSports.size()) {
            System.out.println("❌ Invalid sport selection");
            return;
        }

        abs_sport selectedSport = availableSports.get(sportIndex);

        System.out.println("Enter date and time (dd-MM-yyyy HH:mm): ");
        String dateTimeStr = scanner.nextLine();
        LocalDateTime dateTime;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid date format");
            return;
        }

        if(dateTime.isBefore(LocalDateTime.now())) {
            System.out.println("❌ Cannot create an event in the past. Please enter a future date.");
            return;
        }

        System.out.println("Enter maximum number of participants: ");
        int maxParticipants = Integer.parseInt(scanner.nextLine());

        SportEvent event = new SportEvent(selectedSport, dateTime, maxParticipants);
        events.add(event);

        System.out.println("✅ Event created successfully:\n" + event);
    }

    public static void listEvents() {
        if(events.isEmpty()) {
            System.out.println("No events created yet.");
        } else {
            System.out.println("📋 All Events:");
            for(SportEvent event : events) {
                System.out.println("- " + event);
            }
        }
    }

    public static void addEvent(SportEvent event) {
        events.add(event);
    }

    public static List<SportEvent> getEvents() {
        return events;
    }

    public static void clearEvents() {
        events.clear();
    }
}
