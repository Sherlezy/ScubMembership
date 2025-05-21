package org.ScubMembership.Utils;

import org.ScubMembership.Users.NormalUser.NormalUser;

import java.io.*;
import java.util.HashMap;

public class UserStorage {
    private static final String FILE_PATH = "users.ser";

    public static void saveUsers(HashMap<String, NormalUser> users) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(users);
            System.out.println("✅ User data saved.");
        } catch (IOException e) {
            System.out.println("❌ Failed to save users: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, NormalUser> loadUsers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (HashMap<String, NormalUser>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ No existing user data found. Starting fresh.");
            return new HashMap<>();
        }
    }
}
