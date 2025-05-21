package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;

import java.util.Scanner;

public class StartLoginProcess {

   private LoginPage loginPage;

   public StartLoginProcess(LoginPage loginPage){
       this.loginPage = loginPage;
   }

    public void startLoginProcess() {
        Scanner scanner = loginPage.getScanner();

        System.out.println("=== Terminal Login System ===");

        while (!loginPage.isLoggedIn()) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    LoginAttempt loginAttempt = new LoginAttempt(loginPage);
                    loginAttempt.startLoginAttempt();
                    break;
                case "2":
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        LoginMenu menu = new LoginMenu(loginPage);
        menu.showLoginMenu();
    }
}
