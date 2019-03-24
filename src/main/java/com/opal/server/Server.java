package com.opal.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static int DEFAULT_PORT = 4000;

    private int portNumber = DEFAULT_PORT;

    private ServerListenerInterface listener;

    private ConnectionHandlerInterface connectionHandler;


    public Server(ConnectionHandlerInterface connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void onConnection(ServerListenerInterface listener) {
        this.listener = listener;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server initiated on port " + portNumber);

            while (true) {
                System.out.println("Waiting for connection");

                Socket clientSocket = serverSocket.accept();

                System.out.println("Incoming connection");

                connectionHandler.onConnection(clientSocket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(int portNumber) {
        setPortNumber(portNumber);
    }

    private void setPortNumber(int portNumber) {
        if(portNumber > 1024) {
            this.portNumber = portNumber;
        }
        else {
            this.portNumber = Server.DEFAULT_PORT;
        }
    }
}
