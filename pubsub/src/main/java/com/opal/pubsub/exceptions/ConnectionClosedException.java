package com.opal.pubsub.exceptions;

public class ConnectionClosedException extends RuntimeException {

    public ConnectionClosedException() {
        super("Connection is closed");
    }

}
