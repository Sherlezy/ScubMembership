package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.Login.LoginFunctions.StartLoginProcess;
import org.ScubMembership.Login.LoginPage;
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
    public static void show(Admin admin, LoginPage loginPage) {
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
            System.out.println("7. Show every normal user");
            System.out.println("8. Modify a user's information");
            System.out.println("9. View average feedback for the past events");

            System.out.print("Option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    ShowAllAdmins.execute();
                    break;
                case "2":
                    RegisterNewAdmin.execute(scanner);
                    break;
                case "3":
                    StartLoginProcess process = new StartLoginProcess(loginPage);
                    process.startLoginProcess();
                    running = false;
                    break;
                case "4":
                    CreateNewSport.execute(scanner, sports);
                    break;
                case "5":
                    CreateNewSportEvent.execute(sports);
                    break;
                case "6":
                    AddNewQuizQuestion.execute(scanner);
                    break;
                case "7":
                    ShowAllNormalUsers.execute();
                    break;
                case "8":
                    ModifyUsersInformations.execute(scanner);
                    break;
                case "9":
                    ViewFeedbackAverage.execute();
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
