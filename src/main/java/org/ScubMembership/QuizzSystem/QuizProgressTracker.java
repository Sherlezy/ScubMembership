package org.ScubMembership.QuizzSystem;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class QuizProgressTracker {
    private static final String FILE_PATH = "Storage/quiz_progress.ser";

    // username → (sport → questionsAnsweredCount)
    private static Map<String, Map<String, Integer>> progress = loadProgress();

    public static boolean hasUserSeenAll(String username, String sport, int totalQuestions) {
        return progress
                .getOrDefault(username, new HashMap<>())
                .getOrDefault(sport, 0) >= totalQuestions;
    }

    public static int getLastAnsweredIndex(String username, String sport) {
        return progress
                .getOrDefault(username, new HashMap<>())
                .getOrDefault(sport, 0);
    }

    public static void updateProgress(String username, String sport, int totalAnswered) {
        progress.putIfAbsent(username, new HashMap<>());
        progress.get(username).put(sport, totalAnswered);
        saveProgress();
    }

    private static void saveProgress() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(progress);
        } catch (IOException e) {
            System.out.println("❌ Could not save quiz progress: " + e.getMessage());
        }
    }

    private static Map<String, Map<String, Integer>> loadProgress() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (Map<String, Map<String, Integer>>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }
}
