package org.ScubMembership.Login;

import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;
import java.util.Scanner;

public class LoginPage {
    protected HashMap<String, NormalUser> loginInfo;
    private Scanner scanner;
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
    public void setCurrentUser(NormalUser user) {
        this.currentUser = user;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setLoginInfo(HashMap<String, NormalUser> loginInfo) {
        this.loginInfo = loginInfo;
    }

    public void refreshCurrentUser() {
        if (currentUser == null) return;
        NormalUser updated = UserStorage.getUserByUsername(currentUser.getUsername());
        if (updated != null) {
            currentUser = updated;
        }
    }



}
