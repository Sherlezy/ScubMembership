package org.ScubMembership.SportActivity;

import org.ScubMembership.RecommendationLogic.TimeSlotSuggester;
import org.ScubMembership.RecommendationLogic.WhatSportSuggester;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class EventManager {
    private static final String FILE_PATH = "Storage/events.ser";
    private static final List<SportEvent> events = loadEvents();

    public static void createEvent(List<abs_sport> availableSports) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n📢 System Recommendation (based on user preferences and feedback):");
        WhatSportSuggester.suggestSportAndTimeSlot();
        System.out.println("\n📅 Create a new Event");


        System.out.println("Available Sports: ");
        for (int i = 0; i < availableSports.size(); i++) {
            System.out.println((i + 1) + ". " + availableSports.get(i).getName());
        }

        System.out.print("Select sport by number: ");
        int sportIndex;
        try {
            sportIndex = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number.");
            return;
        }

        if (sportIndex < 0 || sportIndex >= availableSports.size()) {
            System.out.println("❌ Invalid sport selection.");
            return;
        }

        abs_sport selectedSport = availableSports.get(sportIndex);

        System.out.println(TimeSlotSuggester.suggestBestTimeSlot(selectedSport.getName()));


        System.out.print("Enter date and time (dd-MM-yyyy HH:mm): ");
        String dateTimeStr = scanner.nextLine();
        LocalDateTime dateTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("❌ Invalid date format.");
            return;
        }

        System.out.print("Enter maximum number of participants: ");
        int maxParticipants;
        try {
            maxParticipants = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number.");
            return;
        }

        SportEvent event = new SportEvent(selectedSport, dateTime, maxParticipants);
        events.add(event);
        saveEvents();

        System.out.println("✅ Event created successfully:\n" + event);
    }

    public static void listEvents() {
        if (events.isEmpty()) {
            System.out.println("No events created yet.");
        } else {
            System.out.println("📋 All Events:");
            for (SportEvent event : events) {
                System.out.println("- " + event);
            }
        }
    }

    public static void addEvent(SportEvent event) {
        events.add(event);
        saveEvents();
    }

    public static List<SportEvent> getEvents() {
        return events;
    }

    public static void clearEvents() {
        events.clear();
        saveEvents();
    }

    private static void saveEvents() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(events);
        } catch (IOException e) {
            System.out.println("❌ Failed to save events: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static List<SportEvent> loadEvents() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<SportEvent>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ Could not load saved events.");
            return new ArrayList<>();
        }
    }
}
