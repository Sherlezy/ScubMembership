package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.Users.Admin.Admin;
import org.ScubMembership.Utils.AdminStorage;

import java.util.Map;

public class ShowAllAdmins {

    public static void execute() {
        Map<String, Admin> admins = AdminStorage.loadAdmins();

        if (admins.isEmpty()) {
            System.out.println("📭 No admins found.");
            return;
        }

        System.out.println("👑 Registered Admins:");
        for (Map.Entry<String, Admin> entry : admins.entrySet()) {
            String username = entry.getKey();
            Admin admin = entry.getValue();
            System.out.println("- " + username + " (" + admin.getEmail() + ")");
        }
    }
}
