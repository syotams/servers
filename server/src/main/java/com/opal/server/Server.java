package com.opal.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Server {

    private static int DEFAULT_PORT = 4000;

    private int portNumber = DEFAULT_PORT;

    // currently no supported
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
                Socket clientSocket = serverSocket.accept();

                long startTime = System.nanoTime();

                //System.out.println("Incoming connection");

                connectionHandler.onConnection(clientSocket);

                long elapsedTime = System.nanoTime() - startTime;

                /*System.out.println("Connection results: elapsed time "
                        + TimeUnit.NANOSECONDS.toMillis(elapsedTime)
                        + "ms"
                );*/
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
    }
}
