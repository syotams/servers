package com.opal.cache.client;

import com.opal.cache.common.ArgsUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        ArgsUtils argsUtils = new ArgsUtils(args);
        String host = argsUtils.getString("server", "localhost");
        int port = argsUtils.getInteger("port", 4000);

        try (
            Socket socket = new Socket(host, port);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            out.println("HELLO");

            String fromServer, fromUser;

            while ((fromServer = in.readLine()) != null) {
                if (fromServer.equals("Bye.")) {
                    System.out.println("Received Bye from server, connection closed");
                    break;
                }

                System.out.println(fromServer);

                fromUser = stdIn.readLine();

                if (fromUser != null) {
                    out.println(fromUser);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
