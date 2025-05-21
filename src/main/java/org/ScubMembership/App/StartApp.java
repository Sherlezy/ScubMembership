package org.ScubMembership.App;

import org.ScubMembership.Users.Admin.Admin;
import org.ScubMembership.Utils.AdminStorage;
import org.ScubMembership.Utils.UserStorage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.Welcome;

import java.util.HashMap;

public class StartApp {
    public static void main(String[] args) {
        HashMap<String, NormalUser> loginInfo = UserStorage.loadUsers();
        HashMap<String, Admin> adminInfo = AdminStorage.loadAdmins();

        // Optional: create a default admin on first run
        if (adminInfo.isEmpty()) {
            Admin defaultAdmin = new Admin("admin@scub.com", "admin", "admin123", 0);
            adminInfo.put(defaultAdmin.username, defaultAdmin);
            AdminStorage.saveAdmins(adminInfo);
        }

        Welcome.welcome(loginInfo, adminInfo);  // update Welcome to accept both
    }
}
