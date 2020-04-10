package com.opal.server.file.server;

import com.opal.server.ConnectionHandler;
import com.opal.server.Server;
import com.opal.server.helpers.Console;

public class FilerServer {

    public static void main(String args[]) {
        int portNumber = Console.getInt(args, 0, "4000");

        FileStorageProtocol protocol = new FileStorageProtocol();

        Server server = new Server(new ConnectionHandler(protocol));
        server.listen(portNumber);
        server.start();
    }

}
