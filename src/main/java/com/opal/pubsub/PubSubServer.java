package com.opal.pubsub;

import com.opal.server.Server;

public class PubSubServer {

    public static void main(String args[]) {
        Server server = new Server(new ConnectionHandler(new PubSubProtocol(), new PubSub()));
        server.start();
    }

}