package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;
import java.util.Scanner;

public class ModifyUsersInformations {

    public static void execute(Scanner scanner) {
        HashMap<String, NormalUser> users = UserStorage.loadUsers();

        if (users.isEmpty()) {
            System.out.println("📭 No users to modify.");
            return;
        }

        System.out.println("🔧 Modify User Information");
        System.out.println("Available users:");
        for (String username : users.keySet()) {
            System.out.println("- " + username);
        }

        System.out.print("Enter username to modify: ");
        String username = scanner.nextLine().trim();

        if (!users.containsKey(username)) {
            System.out.println("❌ User not found.");
            return;
        }

        NormalUser user = users.get(username);

        System.out.println("\nCurrent info:");
        System.out.println("1. Email: " + user.getEmail());
        System.out.println("2. Username: " + user.getUsername());
        System.out.println("3. Password: " + "*".repeat(user.getPassword().length()));
        System.out.println("4. Preferred Sport: " + user.getPreferredSport());
        System.out.println("Enter the number of the field to change (or 0 to cancel): ");

        String input = scanner.nextLine();

        switch (input) {
            case "1":
                System.out.print("New email: ");
                String newEmail = scanner.nextLine().trim();
                user.setEmail(newEmail);
                break;
            case "2":
                System.out.print("New username: ");
                String newUsername = scanner.nextLine().trim();
                if (users.containsKey(newUsername)) {
                    System.out.println("❌ Username already taken.");
                    return;
                }
                users.remove(username);
                user.setUsername(newUsername);
                users.put(newUsername, user);
                break;
            case "3":
                System.out.print("New password: ");
                String newPassword = scanner.nextLine().trim();
                user.setPassword(newPassword);
                break;
            case "4":
                System.out.print("New preferred sport: ");
                String newSport = scanner.nextLine().trim();
                user.setPreferredSport(newSport);
                break;
            case "0":
                System.out.println("Cancelled.");
                return;
            default:
                System.out.println("❌ Invalid choice.");
                return;
        }

        UserStorage.saveUsers(users);
        System.out.println("✅ User information updated.");
    }
}
