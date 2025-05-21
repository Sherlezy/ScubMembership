package org.ScubMembership.Utils;

import org.ScubMembership.Login.LoginFunctions.StartLoginProcess;
import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Registration.RegistrationProcess;
import org.ScubMembership.Users.NormalUser.NormalUser;

import java.util.HashMap;
import java.util.Scanner;

public class Welcome {
    public static void welcome(HashMap<String, NormalUser> loginInfo){
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
                    StartLoginProcess loginProcess = new StartLoginProcess(loginPage);
                    loginProcess.startLoginProcess();
                    running = false;
                    break;
                default:
                    System.out.println("Please select 1 or 2");
            }
        }
    }

}
