package com.opal.cache.server;

import com.opal.server.AbstractServerProtocol;

public class InMemoryServerProtocol extends AbstractServerProtocol {

    private IStorage storage;


    public InMemoryServerProtocol(IStorage storage) {
        this.storage = storage;
    }


    protected String onConnected(String input) {
        String[] items = input.split(" ");

        switch (items[0]) {
            case "PUT":
                if(3 == items.length) {
                    storage.put(items[1], items[2]);
                    return "success";
                }

                return "Number of arguments must be 3";

            case "QUIT":
                reset();
                return "Bye.";

            default:
            case "GET":
                if(2 == items.length) {
                    return storage.get(items[1]);
                }

                return "Number of arguments must be 2";
        }
    }

    protected String onWaiting(String input) {
        if(input.equalsIgnoreCase("HELLO")) {
            state = CONNECTED;
        }

        return "Connected to KeyValue CacheServer v1.0, Please use GET/PUT command to insert / receive data";
    }

    public void reset() {
        state = WAITING;
    }
}
