package org.ScubMembership;

import org.ScubMembership.Users.Admin.Admin;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.AdminStorage;
import org.ScubMembership.Utils.UserStorage;
import org.ScubMembership.Utils.Welcome;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashMap<String, NormalUser> loginInfo = UserStorage.loadUsers();
        HashMap<String, Admin> adminInfo = AdminStorage.loadAdmins();
        Welcome.welcome(loginInfo, adminInfo);

    }
}