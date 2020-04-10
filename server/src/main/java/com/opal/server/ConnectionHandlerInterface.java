package com.opal.server;

import java.net.Socket;

public interface ConnectionHandlerInterface {

    void onConnection(Socket clientSocket);

}
