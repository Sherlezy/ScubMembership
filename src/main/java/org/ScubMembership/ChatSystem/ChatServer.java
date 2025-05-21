package org.ScubMembership.ChatSystem;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {

    private static final int PORT = 12345;
    private static Set<Socket> clientSockets = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Chat Server started on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            clientSockets.add(clientSocket);
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private static void handleClient(Socket client) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ) {
            String message;
            while ((message = in.readLine()) != null) {
                broadcast(message, client);
            }
        } catch (IOException e) {
            System.out.println("A client disconnected.");
        } finally {
            clientSockets.remove(client);
            try {
                client.close();
            } catch (IOException ignored) {}
        }
    }

    private static void broadcast(String message, Socket sender) {
        for (Socket socket : clientSockets) {
            if (socket != sender) {
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(message);
                } catch (IOException ignored) {}
            }
        }
    }
}
