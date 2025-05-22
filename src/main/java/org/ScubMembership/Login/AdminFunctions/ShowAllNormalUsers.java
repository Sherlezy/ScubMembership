package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;

public class ShowAllNormalUsers {

    public static void execute() {
        HashMap<String, NormalUser> users = UserStorage.loadUsers();

        if (users.isEmpty()) {
            System.out.println("📭 No users registered.");
            return;
        }

        System.out.println("👥 Registered Normal Users:");
        for (NormalUser user : users.values()) {
            System.out.println("- " + user.getUsername() +
                    " | Email: " + user.getEmail() +
                    " | ID: " + user.getID() +
                    " | Sport: " + user.getPreferredSport());
        }
    }
}
