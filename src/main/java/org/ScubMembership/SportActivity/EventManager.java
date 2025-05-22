package org.ScubMembership.SportActivity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static final String FILE_PATH = "events.ser";
    private static List<SportEvent> events = loadEvents();

    public static List<SportEvent> getEvents() {
        return events;
    }

    public static void addEvent(SportEvent event) {
        events.add(event);
        saveEvents();
    }

    public static void saveEvents() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(events);
        } catch (IOException e) {
            System.out.println("❌ Could not save events: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static List<SportEvent> loadEvents() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<SportEvent>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ Could not load events, starting with an empty list.");
            return new ArrayList<>();
        }
    }
}
