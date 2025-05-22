package org.ScubMembership.QuizzSystem;

import java.io.*;
import java.util.*;

public class QuizLeaderboardManager {
    private static final String FILE_PATH = "Storage/quiz_leaderboard.ser";
    private static List<QuizLeaderboardEntry> leaderboard = load();

    public static void addEntry(String username, String sport, int score, boolean isFavorite) {
        leaderboard.add(new QuizLeaderboardEntry(username, sport, score, isFavorite));
        save();
    }

    public static List<QuizLeaderboardEntry> getLeaderboard(String sport) {
        List<QuizLeaderboardEntry> filtered = new ArrayList<>();
        for (QuizLeaderboardEntry entry : leaderboard) {
            if (entry.getSport().equalsIgnoreCase(sport)) {
                filtered.add(entry);
            }
        }

        filtered.sort((a, b) -> Integer.compare(b.getScore(), a.getScore())); // highest score first
        return filtered;
    }

    private static void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(leaderboard);
        } catch (IOException e) {
            System.out.println("❌ Failed to save leaderboard.");
        }
    }

    private static List<QuizLeaderboardEntry> load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<QuizLeaderboardEntry>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
