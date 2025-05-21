package org.ScubMembership;

import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.Welcome;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashMap<String, NormalUser> loginInfo = new HashMap<>();

        Welcome.welcome(loginInfo);
    }
}