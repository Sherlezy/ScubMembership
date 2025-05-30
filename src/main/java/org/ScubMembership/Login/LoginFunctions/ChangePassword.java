package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.PasswordSecurity.PasswordEncryptor;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;
import java.util.Scanner;

public class ChangePassword {

    private LoginPage loginPage;

    public ChangePassword(LoginPage loginPage){
        this.loginPage = loginPage;
    }

    public void startChangePassword(){
        Scanner scanner = loginPage.getScanner();
        HashMap<String, NormalUser> loginInfo = loginPage.getLoginInfo();
        NormalUser currentUser = loginPage.getCurrentUser();

        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();

        if (currentUser.getPassword().equals(PasswordEncryptor.hashPassword(currentPassword))) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();

            System.out.print("Confirm new password: ");
            String confirmPassword = scanner.nextLine();

            if (newPassword.equals(confirmPassword)) {
                currentUser.setPassword(PasswordEncryptor.hashPassword(newPassword));
                UserStorage.saveUsers(loginInfo);
                System.out.println("Password changed successfully!");

            } else System.out.println("Passwords do not match. No changes made.");
        } else System.out.println("Incorrect current password. No changes made.");
    }


}
