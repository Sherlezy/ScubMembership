package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.PasswordSecurity.PasswordEncryptor;

import java.util.HashMap;
import java.util.Scanner;

public class LoginAttempt {
    private final LoginPage loginPage;

    public LoginAttempt(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void startLoginAttempt() {
        Scanner scanner = loginPage.getScanner();
        HashMap<String, NormalUser> loginInfo = loginPage.getLoginInfo();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (loginInfo.containsKey(username)) {
            NormalUser user = loginInfo.get(username);
            String encryptedInputPassword = PasswordEncryptor.hashPassword(password);

            if (user.getPassword().equals(encryptedInputPassword)) {
                System.out.println("✅ Login successful! Welcome, " + username + "!");
                loginPage.setLoggedIn(true);
                loginPage.setCurrentUser(user);
            } else {
                System.out.println("❌ Incorrect password. Please try again.");
            }
        } else {
            System.out.println("❌ Username not found. Please try again.");
        }
    }
}
