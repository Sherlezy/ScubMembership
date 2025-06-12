package org.ScubMembership.Utils;

import org.ScubMembership.Login.AdminFunctions.AdminMenu;
import org.ScubMembership.Login.LoginFunctions.LoginMenu;
import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Registration.RegistrationProcess;
import org.ScubMembership.Users.Admin.Admin;
import org.ScubMembership.Users.NormalUser.NormalUser;

import java.util.HashMap;
import java.util.Scanner;

public class Welcome {
    public static void welcome(HashMap<String, NormalUser> loginInfo, HashMap<String, Admin> adminInfo){
        LoginPage loginPage = new LoginPage(loginInfo);
        Scanner sc = loginPage.getScanner();

        System.out.println("===== Welcome to SCUB portal =====");
        System.out.println("Are you a new user? Press 1 to proceed with registration. Otherwise press 2 to log in");

        boolean running = true;
        while (running){
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    RegistrationProcess registration = new RegistrationProcess(loginPage);
                    registration.startRegistration();
                    running = false;
                    break;
                case "2":
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    if (adminInfo.containsKey(username) && adminInfo.get(username).getPassword().equals(password)) {
                        System.out.println("👑 Admin login successful!");
                        Admin admin = adminInfo.get(username);
                        AdminMenu.show(admin);
                        running = false;
                    } else if (loginInfo.containsKey(username) && loginInfo.get(username).getPassword().equals(password)) {
                        System.out.println("✅ User login successful!");
                        NormalUser user = loginInfo.get(username);
                        loginPage.setCurrentUser(user);
                        loginPage.setLoggedIn(true);
                        LoginMenu userMenu = new LoginMenu(loginPage);
                        userMenu.showLoginMenu();
                        running = false;
                    } else {
                        System.out.println("❌ Invalid credentials.");
                    }
                    break;


                default:
                    System.out.println("Please select 1 or 2");
            }
        }
    }

}
