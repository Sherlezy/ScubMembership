// ChatClient.java
package org.ScubMembership.ChatSystem;

import java.io.*;
import java.net.*;

public class ChatClient {

    public static void startSportChat(String sport, String username) {
        try (
                Socket socket = new Socket("localhost", 12345);
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // Send SPORT_NAME|USERNAME as first message
            out.println(sport + "|" + username);

            System.out.println("🔗 Connected to chat for " + sport + " as " + username);

            new Thread(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException ignored) {}
            }).start();

            String inputLine;
            while ((inputLine = userInput.readLine()) != null) {
                out.println(inputLine);
            }

        } catch (IOException e) {
            System.out.println("❌ Could not connect to chat server.");
        }
    }
}
