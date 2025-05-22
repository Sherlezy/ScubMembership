package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.QuizzSystem.QuizManager;
import org.ScubMembership.QuizzSystem.QuizQuestion;
import org.ScubMembership.SportActivity.SportRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddNewQuizQuestion {

    public static void execute(Scanner scanner) {
        String selectedSportName = null;

        // Select sport
        while (true) {
            System.out.println("🎯 Choose a sport to add the quiz to:");
            for (int i = 0; i < SportRegistry.SPORTS.size(); i++) {
                System.out.println((i + 1) + ". " + SportRegistry.SPORTS.get(i).getName());
            }

            System.out.print("Option (1-" + SportRegistry.SPORTS.size() + "): ");
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= SportRegistry.SPORTS.size()) {
                    selectedSportName = SportRegistry.SPORTS.get(choice - 1).getName();
                    break;
                } else {
                    System.out.println("❌ Invalid number. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }

        // Load existing quiz for that sport
        List<QuizQuestion> quiz = QuizManager.loadQuiz(selectedSportName);

        // Create new question
        System.out.print("Enter question: ");
        String question = scanner.nextLine();

        List<String> options = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            System.out.print("Option " + (i + 1) + ": ");
            options.add(scanner.nextLine());
        }

        int correct = -1;
        while (correct < 0 || correct >= 4) {
            System.out.print("Correct option (1-4): ");
            try {
                correct = Integer.parseInt(scanner.nextLine()) - 1;
                if (correct < 0 || correct >= 4) {
                    System.out.println("❌ Please enter a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
            }
        }

        // Save to file
        quiz.add(new QuizQuestion(question, options, correct));
        QuizManager.saveQuiz(selectedSportName, quiz);

        System.out.println("✅ Question added to quiz for " + selectedSportName);
    }
}
