package org.ScubMembership.ChatSystem;

import java.io.*;
import java.net.*;

public class ChatClient {

    public static void startChat(String username) {
        try (
                Socket socket = new Socket("localhost", 12345);
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Connected to chat as " + username);

            // Thread to listen for messages
            new Thread(() -> {
                try (
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ) {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException ignored) {}
            }).start();

            // Main loop to send messages
            String inputLine;
            while ((inputLine = userInput.readLine()) != null) {
                out.println(username + ": " + inputLine);
            }

        } catch (IOException e) {
            System.out.println("Could not connect to chat server.");
        }
    }
}
