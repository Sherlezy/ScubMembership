package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.Admin.Admin;
import org.ScubMembership.Utils.AdminStorage;
import org.ScubMembership.Utils.UserStorage;
import org.ScubMembership.Utils.Welcome;
import org.ScubMembership.Users.NormalUser.NormalUser;

import java.util.HashMap;

public class Logout {
    private final LoginPage loginPage;

    public Logout(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void startLogout() {
        System.out.println("Logging out...");

        NormalUser currentUser = loginPage.getCurrentUser();
        HashMap<String, NormalUser> loginInfo = loginPage.getLoginInfo();

       if (currentUser != null && loginInfo != null) {
            loginInfo.put(currentUser.getUsername(), currentUser);
        }

        try {
            UserStorage.saveUsers(loginInfo);
        } catch (Exception e) {
            System.err.println("Failed to save users: " + e.getMessage());
        }

        loginPage.setCurrentUser(null);
        loginPage.setLoggedIn(false);

        System.out.println("Logged out successfully!");

        HashMap<String, Admin> adminInfo = AdminStorage.loadAdmins();
        Welcome.welcome(loginInfo, adminInfo);
    }
}
