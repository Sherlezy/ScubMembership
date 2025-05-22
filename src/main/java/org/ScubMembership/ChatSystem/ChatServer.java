// ChatServer.java
package org.ScubMembership.ChatSystem;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {

    private static final int PORT = 12345;
    private static final Map<String, Set<Socket>> rooms = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("🏁 Chat Server started on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // First message should be: SPORT_NAME|USERNAME
            String[] firstLine = in.readLine().split("\\|");
            if (firstLine.length != 2) {
                out.println("❌ Invalid protocol.");
                clientSocket.close();
                return;
            }

            String sport = firstLine[0];
            String username = firstLine[1];

            rooms.putIfAbsent(sport, ConcurrentHashMap.newKeySet());
            rooms.get(sport).add(clientSocket);

            out.println("✅ Joined " + sport + " chat as " + username);

            String msg;
            while ((msg = in.readLine()) != null) {
                broadcast(sport, username + ": " + msg, clientSocket);
            }

        } catch (IOException e) {
            System.out.println("💥 Client disconnected.");
        } finally {
            rooms.values().forEach(set -> set.remove(clientSocket));
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }

    private static void broadcast(String sport, String message, Socket sender) {
        Set<Socket> clients = rooms.getOrDefault(sport, Collections.emptySet());
        for (Socket socket : clients) {
            if (!socket.equals(sender)) {
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(message);
                } catch (IOException ignored) {}
            }
        }
    }
}
