package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.Users.Admin.Admin;
import org.ScubMembership.Utils.AdminStorage;
import org.ScubMembership.PasswordSecurity.PasswordEncryptor;
import org.ScubMembership.PasswordSecurity.PasswordValidator;
import org.ScubMembership.Utils.EmailValidator;

import java.util.HashMap;
import java.util.Scanner;

public class RegisterNewAdmin {

    public static void execute(Scanner scanner) {
        HashMap<String, Admin> admins = AdminStorage.loadAdmins();

        String newUsername;
        while (true) {
            System.out.print("Enter new admin username: ");
            newUsername = scanner.nextLine().trim();

            if (admins.containsKey(newUsername)) {
                System.out.println("❌ Username already exists. Try again.");
            } else {
                break;
            }
        }

        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine().trim();

            if (!PasswordValidator.isStrongPassword(password)) {
                System.out.println("❌ Password must be at least 8 characters long and include:");
                System.out.println("- Uppercase letter");
                System.out.println("- Lowercase letter");
                System.out.println("- Digit");
                System.out.println("- Special character (@$!%*#?&)");
            } else {
                break;
            }
        }

        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine().trim();

            if (!EmailValidator.isValidEmail(email)) {
                System.out.println("❌ Invalid email format. Try again.");
            } else {
                break;
            }
        }

        String hashedPassword = PasswordEncryptor.hashPassword(password);
        int id = admins.size() + 1;
        Admin newAdmin = new Admin(email, newUsername, hashedPassword, id);
        admins.put(newUsername, newAdmin);
        AdminStorage.saveAdmins(admins);

        System.out.println("✅ New admin added successfully.");
    }

}
