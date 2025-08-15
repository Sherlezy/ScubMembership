package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;

import java.util.Scanner;

public class FriendsMenuUI {
    private static LoginPage loginPage;

    public FriendsMenuUI(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();
        while (true) {
            System.out.println("\n👥 Friends Menu:");
            System.out.println("1. Send a friend request");
            System.out.println("2. View my friends");
            System.out.println("3. Accept friend request");
            System.out.println("4. Reject friend request");
            System.out.println("5. View pending friend requests");
            System.out.println("0. Return to the main menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    new SendFriendRequestUI(loginPage).execute();
                    break;
                case "2":
                    ViewFriendsUI.execute(loginPage);
                    break;
                case "3":
                    new AcceptFriendRequestUI(loginPage).execute();
                    break;
                case "4":
                    new RejectFriendRequestUI(loginPage).execute();
                    break;
                case "5":
                    ViewPendingRequestsUI.execute(loginPage);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }
}
