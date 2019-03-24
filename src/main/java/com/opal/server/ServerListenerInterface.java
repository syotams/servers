package com.opal.server;

public interface ServerListenerInterface {

    void onConnection(Request request, Response response);

}
