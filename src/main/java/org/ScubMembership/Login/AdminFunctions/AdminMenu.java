package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.QuizzSystem.QuizQuestion;
import org.ScubMembership.SportActivity.Basketball.Basketball;
import org.ScubMembership.SportActivity.Climbing.Climbing;
import org.ScubMembership.SportActivity.CustomSport.CustomSport;
import org.ScubMembership.SportActivity.Cycling.Cycling;
import org.ScubMembership.SportActivity.EventManager;
import org.ScubMembership.SportActivity.Football.Football;
import org.ScubMembership.SportActivity.Running.Running;
import org.ScubMembership.SportActivity.SportRegistry;
import org.ScubMembership.SportActivity.Swimming.Swimming;
import org.ScubMembership.SportActivity.Tennis.Tennis;
import org.ScubMembership.SportActivity.Volleyball.Volleyball;
import org.ScubMembership.SportActivity.Yoga.Yoga;
import org.ScubMembership.SportActivity.abs_sport;
import org.ScubMembership.Users.Admin.Admin;
import org.ScubMembership.Utils.AdminStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    public static void show(Admin admin) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Admin> admins = AdminStorage.loadAdmins();
        ArrayList<abs_sport> sports = new ArrayList<>();

        sports.add(new Basketball());
        sports.add(new Football());
        sports.add(new Volleyball());
        sports.add(new Climbing());
        sports.add(new Cycling());
        sports.add(new Running());
        sports.add(new Swimming());
        sports.add(new Tennis());
        sports.add(new Yoga());

        boolean running = true;
        while (running) {
            System.out.println("\n👑 Admin Panel");
            System.out.println("1. View all admins");
            System.out.println("2. Add new admin");
            System.out.println("3. Exit admin panel");
            System.out.println("4. Add new sport");
            System.out.println("5. Create a sport event");
            System.out.println("6. Add quiz question");

            System.out.print("Option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.println("Registered Admins:");
                    for (String username : admins.keySet()) {
                        System.out.println("- " + username + " (" + admins.get(username).getEmail() + ")");
                    }
                    break;
                case "2":
                    System.out.print("Enter new admin username: ");
                    String newUsername = scanner.nextLine();
                    if (admins.containsKey(newUsername)) {
                        System.out.println("Username already exists.");
                        break;
                    }

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    int id = admins.size() + 1;
                    Admin newAdmin = new Admin(email, newUsername, password, id);
                    admins.put(newUsername, newAdmin);
                    AdminStorage.saveAdmins(admins);
                    System.out.println("✅ New admin added successfully.");
                    break;
                case "3":
                    running = false;
                    break;
                case "4":
                    System.out.println("Enter sport name: ");
                    String sportName = scanner.nextLine();

                    System.out.println("Enter equipment items (comma-separated): ");
                    String[] equipment = scanner.nextLine().split(",");

                    for(int i = 0; i < equipment.length; i++) {
                        equipment[i] = equipment[i].trim();
                    }

                    System.out.println("Enter gym name: ");
                    String gymName = scanner.nextLine();

                    System.out.println("Enter number of players: ");
                    int numberOfPlayers = Integer.parseInt(scanner.nextLine());

                    System.out.println("Enter the rules of the sport: ");
                    String rules = scanner.nextLine();

                    System.out.println("Is it an indoor sport (yes/no): ");
                    boolean isIndoor = scanner.nextLine().trim().equalsIgnoreCase("yes");

                    System.out.println("Enter the name and surname of the person in charge: ");
                    String personInCharge = scanner.nextLine();

                    abs_sport customSport = new CustomSport(sportName, equipment, gymName, numberOfPlayers, rules, isIndoor, personInCharge);
                    sports.add(customSport);

                    System.out.println("✅Sport \"" + sportName + "\" added successfully.");
                    break;
                case "5":
                    if(sports.isEmpty()) {
                        System.out.println("⚠\uFE0F No sports available. Please add a sport first.");
                    } else {
                        EventManager.createEvent(sports);
                    }
                    break;
                case "6":
                    String selectedSportName = null;

                    while (true) {
                        System.out.println("Choose a sport to add the quiz to:");
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

                    List<QuizQuestion> quiz = org.ScubMembership.Quiz.QuizManager.loadQuiz(selectedSportName);

                    System.out.print("Enter question: ");
                    String question = scanner.nextLine();

                    List<String> options = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        System.out.print("Option " + (i + 1) + ": ");
                        options.add(scanner.nextLine());
                    }

                    System.out.print("Correct option (1-4): ");
                    int correct = Integer.parseInt(scanner.nextLine()) - 1;

                    quiz.add(new QuizQuestion(question, options, correct));
                    org.ScubMembership.Quiz.QuizManager.saveQuiz(selectedSportName, quiz);

                    System.out.println("✅ Question added to quiz for " + selectedSportName);
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
