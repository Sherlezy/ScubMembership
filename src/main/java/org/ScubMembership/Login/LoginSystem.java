package org.ScubMembership.Login;

import org.ScubMembership.Login.LoginFunctions.StartLoginProcess;
import org.ScubMembership.Users.NormalUser.NormalUser;

import java.util.HashMap;

public class LoginSystem {

    public static void main(String[] args) {

        NormalUser user1 = new NormalUser("a@a.com", "user1", "pass1", 1, "Football");
        NormalUser user2 = new NormalUser("b@b.com", "user2", "pass2", 2, "Basketball");

        HashMap<String, NormalUser> loginInfo = new HashMap<>();
        loginInfo.put(user1.getUsername(), user1);
        loginInfo.put(user2.getUsername(), user2);

        LoginPage loginPage = new LoginPage(loginInfo);

        StartLoginProcess loginProcess = new StartLoginProcess(loginPage);
        loginProcess.startLoginProcess();
    }
}
