package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Utils.UserStorage;
import org.ScubMembership.Utils.Welcome;

import java.util.Scanner;

public class Logout {

    private LoginPage loginPage;


    public Logout(LoginPage loginPage){
        this.loginPage = loginPage;
    }

    public void startLogout(){
        System.out.println("Logging out...");
        loginPage.setLoggedIn(false);
        UserStorage.saveUsers(loginPage.getLoginInfo());

        loginPage.setCurrentUser(null);
        System.out.println("Logged out successfully!");
        Welcome.welcome(loginPage.getLoginInfo());
    }

}
