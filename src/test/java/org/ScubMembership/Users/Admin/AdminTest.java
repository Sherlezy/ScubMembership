package org.ScubMembership.Users.Admin;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    @Test
    void testConstructorAndGetters() {
        Admin admin = new Admin("admin@scub.com", "adminUser", "adminPass", 1);

        assertEquals("admin@scub.com", admin.getEmail());
        assertEquals("adminUser", admin.getUsername());
        assertEquals("adminPass", admin.getPassword());
        assertEquals(1, admin.getID());
    }

    @Test
    void testSetters() {
        Admin admin = new Admin("a@a.com", "oldUser", "oldPass", 0);

        admin.setEmail("new@scub.com");
        admin.setUsername("newUser");
        admin.setPassword("newPass");
        admin.setID(42);

        assertEquals("new@scub.com", admin.getEmail());
        assertEquals("newUser", admin.getUsername());
        assertEquals("newPass", admin.getPassword());
        assertEquals(42, admin.getID());
    }

    @Test
    void testEdgeCases() {
        Admin admin = new Admin(null, "", " ", -100);

        assertNull(admin.getEmail());
        assertEquals("", admin.getUsername());
        assertEquals(" ", admin.getPassword());
        assertEquals(-100, admin.getID());

        admin.setEmail(null);
        admin.setUsername("");
        admin.setPassword(null);
        admin.setID(Integer.MIN_VALUE);

        assertNull(admin.getEmail());
        assertEquals("", admin.getUsername());
        assertNull(admin.getPassword());
        assertEquals(Integer.MIN_VALUE, admin.getID());
    }


}
