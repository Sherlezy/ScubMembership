package org.ScubMembership.Utils;

import org.ScubMembership.Users.NormalUser.NormalUser;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class UserStorageTest {

    private static final String TEST_FILE_PATH = "test_users.ser";

    @BeforeEach
    void setup() {
        UserStorage.setFilePath(TEST_FILE_PATH); // use isolated test file
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();
    }

    @AfterEach
    void cleanup() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();
    }

    @Test
    void testSaveAndLoadUsers() {
        NormalUser user1 = new NormalUser("a@a.com", "user1", "pass1", 1, "Football", "20.00");
        NormalUser user2 = new NormalUser("b@b.com", "user2", "pass2", 2, "Basketball", "19.00");

        HashMap<String, NormalUser> users = new HashMap<>();
        users.put(user1.getUsername(), user1);
        users.put(user2.getUsername(), user2);

        UserStorage.saveUsers(users);
        HashMap<String, NormalUser> loaded = UserStorage.loadUsers();

        assertEquals(2, loaded.size());
        assertEquals("a@a.com", loaded.get("user1").getEmail());
        assertEquals("Basketball", loaded.get("user2").getPreferredSport());
    }

    @Test
    void testLoadWhenFileDoesNotExist() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();

        HashMap<String, NormalUser> loaded = UserStorage.loadUsers();
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }
}
