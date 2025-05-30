package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;

import java.util.Scanner;

public class FriendsMenuUI {

    public static void execute(LoginPage loginPage) {
        Scanner scanner = loginPage.getScanner();

        while(true) {
            System.out.println("\n\uD83D\uDC65 Friends Menu:");
            System.out.println("1. Send a friend request");
            System.out.println("2. View my friends");
            System.out.println("3. Accept friend request");
            System.out.println("4. View pending friend requests");
            System.out.println("0. Return to the main menu");
            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    SendFriendRequestUI.execute(loginPage);
                    break;
                case "2":
                    ViewFriendsUI.execute(loginPage);
                    break;
                case "3":
                    AcceptFriendRequest.execute(loginPage);
                    break;
                case "4":
                    ViewPendingRequestsUI.execute(loginPage);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("❌ Invalid option, please try again.");
            }
        }

    }
}
