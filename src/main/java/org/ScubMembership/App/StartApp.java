package org.ScubMembership.App;

import org.ScubMembership.Utils.UserStorage;
import org.ScubMembership.Users.NormalUser.NormalUser;

import java.util.HashMap;

public class StartApp {
    public static void main(String[] args){
        HashMap<String, NormalUser> loginInfo = UserStorage.loadUsers();

// load from file
        org.ScubMembership.Utils.Welcome.welcome(loginInfo); // pass it to app
    }
}
