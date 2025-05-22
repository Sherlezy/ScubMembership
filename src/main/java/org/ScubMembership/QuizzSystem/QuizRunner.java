package org.ScubMembership.QuizzSystem;

import org.ScubMembership.Users.NormalUser.NormalUser;

import java.util.List;
import java.util.Scanner;

public class QuizRunner {

    public static void startQuiz(String sport, Scanner scanner, NormalUser user) {
        List<QuizQuestion> questions = QuizManager.loadQuiz(sport);
        String username = user.getUsername();

        if (questions.isEmpty()) {
            System.out.println("❌ No quiz available for " + sport);
            return;
        }

        int alreadyAnswered = QuizProgressTracker.getLastAnsweredIndex(username, sport);

        if (alreadyAnswered >= questions.size()) {
            System.out.println("🛑 You've already completed all available questions for " + sport + ".");
            return;
        }

        int score = 0;

        for (int i = alreadyAnswered; i < questions.size(); i++) {
            QuizQuestion q = questions.get(i);

            System.out.println("\nQuestion " + (i + 1) + ": " + q.getQuestion());
            List<String> opts = q.getOptions();
            for (int j = 0; j < opts.size(); j++) {
                System.out.println((j + 1) + ". " + opts.get(j));
            }

            int answer = -1;
            while (answer < 1 || answer > opts.size()) {
                System.out.print("Your answer: ");
                try {
                    answer = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException ignored) {
                    System.out.println("❌ Invalid input. Please enter a number.");
                }
            }

            if (answer - 1 == q.getCorrectIndex()) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.println("❌ Wrong. Correct answer: " + opts.get(q.getCorrectIndex()));
            }

            // Update progress after each question
            QuizProgressTracker.updateProgress(username, sport, i + 1);
        }

        System.out.println("\n🎉 Quiz complete! You answered " + score + " new question(s) correctly.");

        // 🔽 Add to leaderboard
        boolean isFavorite = user.getPreferredSport().equalsIgnoreCase(sport);
        QuizLeaderboardManager.addEntry(username, sport, score, isFavorite);
    }
}
