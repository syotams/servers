package com.opal.pubsub.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Listener {

    public static void main(String args[]) {
        String host = getArgument(args, 0, "localhost");
        int port = Integer.parseInt(getArgument(args, 1, "4000"));

        try (
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            out.println("SUBSCRIBE default");

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

    private static String getArgument(String[] args, int index, String defaultVal) {
        if(args.length > index) {
            return args[index];
        }

        return defaultVal;
    }
}
