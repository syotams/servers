package com.opal.server;

abstract public class AbstractServerProtocol implements ServerProtocolInterface {

    protected static final int WAITING = 0;

    protected static final int CONNECTED = 1;

    protected int state = WAITING;


    abstract protected String onWaiting(String input);

    abstract protected String onConnected(String input);


    public String process(String input) {
        switch (state) {
            default:
            case WAITING:
                return onWaiting(input);

            case CONNECTED:
                return onConnected(input);
        }
    }
}
