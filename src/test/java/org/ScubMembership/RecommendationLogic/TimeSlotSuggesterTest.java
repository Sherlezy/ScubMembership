package org.ScubMembership.RecommendationLogic;

import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimeSlotSuggesterTest {

    @Test
    void testNoUsersForSport() {
        HashMap<String, NormalUser> users = new HashMap<>();

        try (MockedStatic<UserStorage> mock = mockStatic(UserStorage.class)) {
            mock.when(UserStorage::loadUsers).thenReturn(users);

            String result = TimeSlotSuggester.suggestBestTimeSlot("Tennis");
            assertTrue(result.contains("⚠️ No users found with preferred sport"));
        }
    }

    @Test
    void testClearWinnerTimeSlot() {
        NormalUser u1 = mock(NormalUser.class);
        when(u1.getPreferredSport()).thenReturn("Tennis");
        when(u1.getPreferredTimeSlot()).thenReturn("Morning");

        NormalUser u2 = mock(NormalUser.class);
        when(u2.getPreferredSport()).thenReturn("Tennis");
        when(u2.getPreferredTimeSlot()).thenReturn("Morning");

        NormalUser u3 = mock(NormalUser.class);
        when(u3.getPreferredSport()).thenReturn("Tennis");
        when(u3.getPreferredTimeSlot()).thenReturn("Afternoon");

        HashMap<String, NormalUser> users = new HashMap<>();
        users.put("u1", u1);
        users.put("u2", u2);
        users.put("u3", u3);

        try (MockedStatic<UserStorage> mock = mockStatic(UserStorage.class)) {
            mock.when(UserStorage::loadUsers).thenReturn(users);

            String result = TimeSlotSuggester.suggestBestTimeSlot("Tennis");
            assertTrue(result.contains("📌 Suggestion"));
            assertTrue(result.contains("Morning"));
            assertTrue(result.contains("2 user(s) prefer this time"));
        }
    }

    @Test
    void testTieBetweenSlots() {
        NormalUser u1 = mock(NormalUser.class);
        when(u1.getPreferredSport()).thenReturn("Basketball");
        when(u1.getPreferredTimeSlot()).thenReturn("Morning");

        NormalUser u2 = mock(NormalUser.class);
        when(u2.getPreferredSport()).thenReturn("Basketball");
        when(u2.getPreferredTimeSlot()).thenReturn("Evening");

        HashMap<String, NormalUser> users = new HashMap<>();
        users.put("u1", u1);
        users.put("u2", u2);

        try (MockedStatic<UserStorage> mock = mockStatic(UserStorage.class)) {
            mock.when(UserStorage::loadUsers).thenReturn(users);

            String result = TimeSlotSuggester.suggestBestTimeSlot("Basketball");
            assertTrue(result.contains("Morning"));
            assertTrue(result.contains("Evening"));
            assertTrue(result.contains("or"));
            assertTrue(result.contains("1 user(s) prefer this time"));

            assertTrue(result.contains("1 user(s) prefer this time"));
        }
    }

    @Test
    void testNullTimeSlotIgnored() {
        NormalUser u1 = mock(NormalUser.class);
        when(u1.getPreferredSport()).thenReturn("Swimming");
        when(u1.getPreferredTimeSlot()).thenReturn(null);

        HashMap<String, NormalUser> users = new HashMap<>();
        users.put("u1", u1);

        try (MockedStatic<UserStorage> mock = mockStatic(UserStorage.class)) {
            mock.when(UserStorage::loadUsers).thenReturn(users);

            String result = TimeSlotSuggester.suggestBestTimeSlot("Swimming");
            assertTrue(result.contains("0 user(s) prefer this time"));
        }
    }
}
