package org.ScubMembership.Login;

import org.ScubMembership.Users.NormalUser.NormalUser;

import java.util.HashMap;
import java.util.Scanner;

public class LoginPage {
    protected HashMap<String, NormalUser> loginInfo;
    protected Scanner scanner;
    protected boolean loggedIn;
    protected NormalUser currentUser;

    public LoginPage(HashMap<String, NormalUser> loginInfo) {
        this.loginInfo = loginInfo;
        this.scanner = new Scanner(System.in);
        this.loggedIn = false;
        this.currentUser = null;
    }

    public HashMap<String, NormalUser> getLoginInfo() {
        return loginInfo;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn){
        this.loggedIn = loggedIn;
    }

    public Scanner getScanner() {
        return scanner;
    }
    public NormalUser getCurrentUser(){
        return currentUser;
    }
    public void setCurrentUser(NormalUser currentUser){
        this.currentUser = currentUser;
    }

}
