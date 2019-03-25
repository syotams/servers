package com.opal.pubsub;

import com.opal.server.ConnectionHandlerInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectionHandler implements ConnectionHandlerInterface {

    private final PubSub pubSub;

    private final PubSubProtocol protocol;


    public ConnectionHandler(PubSubProtocol protocol, PubSub pubSub) {
        this.protocol = protocol;
        this.pubSub = pubSub;
    }

    public void onConnection(Socket socket) {

        String clientInput;
        BufferedReader in = null;

        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((clientInput = in.readLine()) != null) {
                // if client closes connection, we do not want to use null reference
                if(clientInput.length() == 0) {
                    break;
                }

                String command = protocol.onConnected(clientInput);
                handle(command, socket);

                if(!shouldWaitForIncomingData(command)) {
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();

            try {
                if(null!=in) {
                    in.close();
                }
                else {
                    socket.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    // returns true if should keep reading data
    private void handle(String command, Socket socket) throws IOException {
        switch (command) {
            case "PUBLISH":
                pubSub.publish(protocol.getChannel(), protocol.getData());
                break;

            case "SUBSCRIBE":
                pubSub.subscribe(protocol.getChannel(), socket);
                break;
        }
    }

    private boolean shouldWaitForIncomingData(String command) {
        return command.equalsIgnoreCase("publish");
    }
}
