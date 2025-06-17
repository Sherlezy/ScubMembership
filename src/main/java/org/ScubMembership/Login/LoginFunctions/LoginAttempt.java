package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.PasswordSecurity.PasswordEncryptor;
import org.ScubMembership.Utils.UserStorage;
import org.ScubMembership.SportActivity.EventManager;

import java.util.HashMap;
import java.util.Scanner;

public class LoginAttempt {
    private final LoginPage loginPage;

    public LoginAttempt(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void startLoginAttempt() {
        Scanner scanner = loginPage.getScanner();

        // ❗️RELOAD USER DATA from disk
        HashMap<String, NormalUser> freshLoginInfo = UserStorage.loadUsers();
        loginPage.setLoginInfo(freshLoginInfo);  // update loginPage to hold latest user map

        EventManager.reloadEvents();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (freshLoginInfo.containsKey(username)) {
            String encryptedInputPassword = PasswordEncryptor.hashPassword(password);
            NormalUser freshUser = freshLoginInfo.get(username);

            if (freshUser.getPassword().equals(encryptedInputPassword)) {
                loginPage.setLoginInfo(freshLoginInfo); // ensure LoginPage has fresh data
                loginPage.setCurrentUser(freshUser);
                loginPage.setLoggedIn(true);
                System.out.println("✅ Login successful! Welcome, " + freshUser.getUsername() + "!");
            } else {
                System.out.println("❌ Incorrect password. Please try again.");
            }
        } else {
            System.out.println("❌ Username not found. Please try again.");
        }
    }
}
