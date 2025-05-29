package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.ChatSystem.ChatClient;
import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.SportActivity.Basketball.Basketball;
import org.ScubMembership.SportActivity.SportRegistry;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JoinSportChatTest {
    private LoginPage loginPage;
    private NormalUser mockUser;

    @BeforeEach
    void setup() {
        NormalUser user = new NormalUser("u@unibz.it", "testUser", "pass", 1, "Basketball", "Morning (08:00 - 12:00)");

        SportRegistry.SPORTS.clear();
        SportRegistry.SPORTS.add(new Basketball());

        loginPage = new LoginPage(new HashMap<>());
        loginPage.setCurrentUser(user);

        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("1");
        loginPage.setScanner(mockScanner);
    }

    @Test
    void testJoinSportChat_withValidInput_startsChat() {
        try(var mocked = Mockito.mockStatic(ChatClient.class)) {
            JoinSportChat.execute(loginPage);

            mocked.verify(() -> ChatClient.startSportChat("Basketball", "testUser"));
        }
    }
}
