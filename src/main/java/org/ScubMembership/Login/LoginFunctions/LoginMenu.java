package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;

import java.util.Scanner;

public class LoginMenu {

    private LoginPage loginPage;

    public LoginMenu (LoginPage loginPage){
        this.loginPage = loginPage;
    }

    public void showLoginMenu(){
        Scanner scanner = loginPage.getScanner();

        boolean running = true;

        while (running && loginPage.isLoggedIn()) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("Logged in as: " + loginPage.getCurrentUser().getUsername());
            System.out.println("1. Change Password");
            System.out.println("2. View User Details");
            System.out.println("3. Logout");
            System.out.print("Option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    ChangePassword changePassword = new ChangePassword(loginPage);
                    changePassword.startChangePassword();
                    break;
                case "2":
                    ViewUserDetails userDetails = new ViewUserDetails(loginPage);
                    userDetails.seeUserDetails();
                    break;
                case "3":
                    Logout logout = new Logout(loginPage);
                    logout.startLogout();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        if (!loginPage.isLoggedIn()) {
            StartLoginProcess loginProcess = new StartLoginProcess(loginPage);
            loginProcess.startLoginProcess();
        }
    }

}
