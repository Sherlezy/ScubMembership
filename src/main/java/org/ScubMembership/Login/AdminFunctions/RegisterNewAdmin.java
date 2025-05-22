package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.Users.Admin.Admin;
import org.ScubMembership.Utils.AdminStorage;

import java.util.HashMap;
import java.util.Scanner;

public class RegisterNewAdmin {

    public static void execute(Scanner scanner) {
        HashMap<String, Admin> admins = AdminStorage.loadAdmins();

        System.out.print("Enter new admin username: ");
        String newUsername = scanner.nextLine().trim();

        if (admins.containsKey(newUsername)) {
            System.out.println("❌ Username already exists.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        int id = admins.size() + 1;
        Admin newAdmin = new Admin(email, newUsername, password, id);
        admins.put(newUsername, newAdmin);
        AdminStorage.saveAdmins(admins);

        System.out.println("✅ New admin added successfully.");
    }
}
