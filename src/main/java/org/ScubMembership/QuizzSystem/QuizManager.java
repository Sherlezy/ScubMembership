package org.ScubMembership.QuizzSystem;

import org.ScubMembership.QuizzSystem.QuizQuestion;

import java.io.*;
import java.util.*;

public class QuizManager {

    private static final String QUIZ_FOLDER = "quizzes/";

    static {
        new File(QUIZ_FOLDER).mkdirs(); // ensure folder exists
    }

    public static List<QuizQuestion> loadQuiz(String sport) {
        File file = new File(QUIZ_FOLDER + sport + ".quiz");
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<QuizQuestion>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void saveQuiz(String sport, List<QuizQuestion> questions) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(QUIZ_FOLDER + sport + ".quiz"))) {
            out.writeObject(questions);
        } catch (IOException e) {
            System.out.println("Failed to save quiz for " + sport);
        }
    }
}
