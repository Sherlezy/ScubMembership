package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.Users.Admin.Admin;
import org.ScubMembership.Utils.AdminStorage;

import java.util.HashMap;
import java.util.Scanner;

public class AdminMenu {
    public static void show(Admin admin) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Admin> admins = AdminStorage.loadAdmins();

        boolean running = true;
        while (running) {
            System.out.println("\n👑 Admin Panel");
            System.out.println("1. View all admins");
            System.out.println("2. Add new admin");
            System.out.println("3. Exit admin panel");
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

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
