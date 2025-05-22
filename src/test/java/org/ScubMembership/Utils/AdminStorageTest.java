package org.ScubMembership.Utils;

import org.ScubMembership.Users.Admin.Admin;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class AdminStorageTest {

    private static final String TEST_FILE_PATH = "test_admins.ser";

    @BeforeEach
    void setup() {
        AdminStorage.setFilePath(TEST_FILE_PATH);
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();
    }

    @AfterEach
    void cleanup() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();
    }

    @Test
    void testSaveAndLoadAdmins() {
        Admin admin1 = new Admin("admin1@scub.com", "admin1", "pass1", 1);
        Admin admin2 = new Admin("admin2@scub.com", "admin2", "pass2", 2);

        HashMap<String, Admin> adminsToSave = new HashMap<>();
        adminsToSave.put(admin1.getUsername(), admin1);
        adminsToSave.put(admin2.getUsername(), admin2);

        AdminStorage.saveAdmins(adminsToSave);
        HashMap<String, Admin> loadedAdmins = AdminStorage.loadAdmins();

        assertEquals(2, loadedAdmins.size());
        assertTrue(loadedAdmins.containsKey("admin1"));
        assertEquals("admin2@scub.com", loadedAdmins.get("admin2").getEmail());
    }

    @Test
    void testLoadFromNonexistentFile() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();
        HashMap<String, Admin> result = AdminStorage.loadAdmins();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
