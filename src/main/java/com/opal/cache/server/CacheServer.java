package com.opal.cache.server;

import com.opal.server.Server;

public class CacheServer {

    public static void main(String args[]) {
        int portNumber = Integer.parseInt(args[0]);

        InMemoryServerProtocol protocol = new InMemoryServerProtocol(new InMemoryStorage());

        Server server = new Server(new ConnectionHandler(protocol));
        server.listen(portNumber);
        server.start();
    }
}