package org.ScubMembership.Friends;

import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;

public abstract class AbstractFriends {
    protected final LoginPage loginPage;

    protected AbstractFriends(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    protected void refreshCurrentUser() {
        HashMap<String, NormalUser> freshUsers = UserStorage.loadUsers();
        NormalUser freshUser = freshUsers.get(loginPage.getCurrentUser().getUsername());
        if (freshUser != null) {
            loginPage.setCurrentUser(freshUser);
        }
    }

    protected NormalUser getCurrentUser() {
        return loginPage.getCurrentUser();
    }

    protected HashMap<String, NormalUser> getLoginInfo() {
        return loginPage.getLoginInfo();
    }

    public abstract void execute();
}
