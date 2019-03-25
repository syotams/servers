package com.opal.cache.server;

import com.opal.helpers.Console;
import com.opal.server.Server;

public class CacheServer {

    public static void main(String args[]) {
        int portNumber = Console.getInt(args, 0, "4000");

        InMemoryServerProtocol protocol = new InMemoryServerProtocol(new InMemoryStorage());

        Server server = new Server(new ConnectionHandler(protocol));
        server.listen(portNumber);
        server.start();
    }
}