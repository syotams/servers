package com.opal.pubsub.client;

import com.opal.server.helpers.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Listener {

    public static void main(String args[]) {
        String host = Console.getArgument(args, 0, "localhost");
        int port    = Console.getInt(args, 1, "4000");
        String channel = Console.getArgument(args, 2, "default");

        String command = "SUBSCRIBE " + channel;

        try (
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            out.println(command);

            String fromServer;

            while ((fromServer = in.readLine()) != null) {
                if (fromServer.equals("Bye.")) {
                    System.out.println("Received Bye from server, connection closed");
                    break;
                }

                System.out.println(fromServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
