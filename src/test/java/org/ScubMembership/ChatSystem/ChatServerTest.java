package org.ScubMembership.ChatSystem;

import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ChatServerTest {

    static class MockSocket extends Socket {
        private final ByteArrayOutputStream out = new ByteArrayOutputStream();

        @Override
        public OutputStream getOutputStream() {
            return out;
        }

        public String getSentOutput() {
            return out.toString().trim();
        }
    }

    private static final Set<Socket> testClientSockets = ConcurrentHashMap.newKeySet();

    @BeforeEach
    void setup() throws Exception {
        // Inject our mock socket set
        var field = ChatServer.class.getDeclaredField("clientSockets");
        field.setAccessible(true);
        field.set(null, testClientSockets);
    }

    @AfterEach
    void clear() {
        testClientSockets.clear();
    }

    @Test
    void testBroadcastSendsToOtherClients() throws Exception {
        MockSocket client1 = new MockSocket();
        MockSocket client2 = new MockSocket();

        testClientSockets.add(client1);
        testClientSockets.add(client2);

        // Use reflection to invoke private broadcast method
        Method broadcastMethod = ChatServer.class.getDeclaredMethod("broadcast", String.class, Socket.class);
        broadcastMethod.setAccessible(true);

        broadcastMethod.invoke(null, "Hello test!", client1);

        assertEquals("Hello test!", client2.getSentOutput());
        assertEquals("", client1.getSentOutput()); // sender should not receive message
    }
}
