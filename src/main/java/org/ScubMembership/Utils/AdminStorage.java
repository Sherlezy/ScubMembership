package org.ScubMembership.Utils;

import org.ScubMembership.Users.Admin.Admin;

import java.io.*;
import java.util.HashMap;

public class AdminStorage {
    private static final String FILE_PATH = "admins.ser";

    public static void saveAdmins(HashMap<String, Admin> admins) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(admins);
            System.out.println("✅ Admin data saved.");
        } catch (IOException e) {
            System.out.println("❌ Failed to save admins: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, Admin> loadAdmins() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (HashMap<String, Admin>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ No existing admin data found.");
            return new HashMap<>();
        }
    }
}
