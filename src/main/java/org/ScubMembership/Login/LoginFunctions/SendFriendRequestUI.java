package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

import static org.ScubMembership.Friends.FriendRecommender.getRecommendedFriends;
import static org.ScubMembership.Friends.FriendRequest.sendFriendRequest;

public class SendFriendRequestUI {

    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();
        NormalUser currentUser = loginPage.getCurrentUser();
        Map<String, NormalUser> users = UserStorage.loadUsers();
        List<NormalUser> allUsers = new ArrayList<>(users.values());
        allUsers.removeIf(user -> user.getUsername().equals(currentUser.getUsername()));

        while (true) {
            System.out.println("\n=== \uD83E\uDD1D Friend Request Menu ===");
            System.out.println("1. View Recommended Friends");
            System.out.println("2. Search by username");
            System.out.println("0. Back to menu");
            System.out.println("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showRecommendedFriends(scanner, currentUser, allUsers);
                case "2" -> searchAndSendFriendRequest(scanner, currentUser, allUsers);
                case "0" -> {return;}
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }

    private static void showRecommendedFriends(Scanner scanner, NormalUser currentUser, List<NormalUser> allUsers) {
        List<NormalUser> recommended = getRecommendedFriends(currentUser);

        if(recommended.isEmpty()) {
            System.out.println("⚠\uFE0F No recommended friends at the moment.");
            return;
        }

        System.out.println("\uD83D\uDD0D Recommended users to add as friends:");
        for(int i = 0; i < recommended.size(); i++) {
            NormalUser user = recommended.get(i);
            System.out.println((i+1) + ". " + user.getUsername() + " (Preferred Sport: " + user.getPreferredSport()
                    + ")");
        }

        System.out.println("Enter the number of the user to send a friend request (0 to cancel): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if(choice == 0) return;
            if(choice < 1 || choice > recommended.size()) {
                System.out.println("❌ Invalid selection. Returning...");
                return;
            }

            NormalUser selected = recommended.get(choice - 1);
            boolean success = sendFriendRequest(currentUser, selected);
            if(success) {
                System.out.println("✅ Friend request sent to " + selected.getUsername());
            } else {
                System.out.println("⚠\uFE0F Could not send request (maybe already friends or request sent)");
            }
        } catch(NumberFormatException e) {
            System.out.println("❌ Invalid input");
        }
    }

    private static void searchAndSendFriendRequest(Scanner scanner, NormalUser currentUser, List<NormalUser> allUsers) {
        System.out.println("\uD83D\uDD0E Enter username to search: ");
        String query = scanner.nextLine().toLowerCase();

        List<NormalUser> matches = allUsers.stream()
                .filter(user -> user.getUsername().toLowerCase().contains(query))
                .toList();

        if(matches.isEmpty()) {
            System.out.println("❌ No users found with that name");
            return;
        }

        System.out.println("\uD83D\uDD0D Search result:");
        for(int i = 0; i < matches.size(); i++) {
            NormalUser user = matches.get(i);
            System.out.println((i+1) + ". " + user.getUsername() + " (Preferred Sport: " + user.getPreferredSport()
                    + ")");
        }

        System.out.println("Enter the number of the user to send a friend request (0 to cancel): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if(choice == 0) return;
            if(choice < 1 || choice > matches.size()) {
                System.out.println("❌ Invalid selection. Returning...");
                return;
            }

            NormalUser selected = matches.get(choice - 1);
            boolean success = sendFriendRequest(currentUser, selected);
            if(success) {
                System.out.println("✅ Friend request sent to " + selected.getUsername());
            } else {
                System.out.println("⚠\uFE0F Could not send request (maybe already friends or request sent)");
            }
        } catch(NumberFormatException e) {
            System.out.println("❌ Invalid input");
        }
    }
}

