package org.ScubMembership.Utils;

import org.ScubMembership.Users.NormalUser.NormalUser;

import java.io.*;
import java.util.HashMap;

public class UserStorage {
    private static String filePath = "Storage/users.ser";

    public static void setFilePath(String newPath) {
        filePath = newPath;
    }

    public static void saveUsers(HashMap<String, NormalUser> users) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(users);
        } catch (IOException e) {
            System.out.println("❌ Failed to save users: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, NormalUser> loadUsers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = in.readObject();
            if(obj instanceof HashMap) {
                return (HashMap<String, NormalUser>) obj;
            } else {
                System.out.println("❌ Invalid data format: expected HashMap but got " + obj.getClass().getSimpleName());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ No existing user data found. Starting fresh.");
        }
        return new HashMap<>();
    }

    public static NormalUser getUserByUsername(String username) {
        HashMap<String, NormalUser> allUsers = loadUsers();  // always fresh from disk
        return allUsers.get(username);  // may be null if not found
    }

}
