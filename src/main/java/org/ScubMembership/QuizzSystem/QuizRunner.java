package org.ScubMembership.Quiz;

import org.ScubMembership.QuizzSystem.QuizQuestion;

import java.util.List;
import java.util.Scanner;

public class QuizRunner {

    public static void startQuiz(String sport, Scanner scanner) {
        List<QuizQuestion> questions = org.ScubMembership.Quiz.QuizManager.loadQuiz(sport);
        if (questions.isEmpty()) {
            System.out.println("No quiz available for " + sport);
            return;
        }

        int score = 0;

        for (QuizQuestion q : questions) {
            System.out.println("\n" + q.getQuestion());
            List<String> opts = q.getOptions();
            for (int i = 0; i < opts.size(); i++) {
                System.out.println((i + 1) + ". " + opts.get(i));
            }

            System.out.print("Your answer: ");
            int answer = Integer.parseInt(scanner.nextLine());

            if (answer - 1 == q.getCorrectIndex()) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.println("❌ Wrong. Correct answer: " + opts.get(q.getCorrectIndex()));
            }
        }

        System.out.println("\nQuiz complete! Your score: " + score + "/" + questions.size());
    }
}
