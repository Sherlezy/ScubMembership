package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.PasswordSecurity.PasswordEncryptor;
import org.ScubMembership.PasswordSecurity.PasswordValidator;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.EmailValidator;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;
import java.util.Scanner;
import static org.ScubMembership.SportActivity.SportRegistry.SPORTS;

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
                while (true) {
                    System.out.print("New email (or type 'exit' to cancel): ");
                    String newEmail = scanner.nextLine().trim();

                    if (newEmail.equalsIgnoreCase("exit")) {
                        System.out.println("❌ Email change cancelled.");
                        return;
                    }

                    if (!EmailValidator.isValidEmail(newEmail)) {
                        System.out.println("❌ Invalid email format. Please try again.");
                    } else {
                        user.setEmail(newEmail);
                        System.out.println("✅ Email updated.");
                        break;
                    }
                }
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
                while (true) {
                    System.out.print("New password (or type 'exit' to cancel): ");
                    String newPassword = scanner.nextLine().trim();

                    if (newPassword.equalsIgnoreCase("exit")) {
                        System.out.println("❌ Password change cancelled.");
                        return;
                    }

                    if (!PasswordValidator.isStrongPassword(newPassword)) {
                        System.out.println("❌ Password too weak. It must be at least 8 characters and include:");
                        System.out.println("- At least one lowercase letter");
                        System.out.println("- At least one uppercase letter");
                        System.out.println("- At least one digit");
                        System.out.println("- At least one special character (@$!%*#?&)");
                    } else {
                        String hashedPassword = PasswordEncryptor.hashPassword(newPassword);
                        user.setPassword(hashedPassword);
                        System.out.println("✅ Password updated.");
                        break;
                    }
                }
                break;

            case "4":
                if (SPORTS.isEmpty()) {
                    System.out.println("❌ No sports available.");
                    return;
                }

                while (true) {
                    System.out.println("Choose a new preferred sport:");
                    for (int i = 0; i < SPORTS.size(); i++) {
                        System.out.println((i + 1) + ". " + SPORTS.get(i).getName());
                    }

                    System.out.print("Option (1-" + SPORTS.size() + ", or type 'exit' to cancel): ");
                    String sportInput = scanner.nextLine().trim();

                    if (sportInput.equalsIgnoreCase("exit")) {
                        System.out.println("❌ Sport change cancelled.");
                        return;
                    }

                    try {
                        int selected = Integer.parseInt(sportInput);
                        if (selected >= 1 && selected <= SPORTS.size()) {
                            String selectedSport = SPORTS.get(selected - 1).getName();
                            user.setPreferredSport(selectedSport);
                            System.out.println("✅ Preferred sport updated to: " + selectedSport);
                            break;
                        } else {
                            System.out.println("❌ Invalid number. Please try again.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid input. Please enter a number.");
                    }
                }
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
