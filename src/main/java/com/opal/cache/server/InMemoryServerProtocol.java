package com.opal.cache.server;

import com.opal.server.AbstractServerProtocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InMemoryServerProtocol extends AbstractServerProtocol {

    private StorageInterface storage;


    public InMemoryServerProtocol(StorageInterface storage) {
        this.storage = storage;
    }

    protected String onConnected(String input) {

        Pattern p = Pattern.compile("(?<command>[a-zA-Z]+?)\\s+(?<param>\\w+)(\\s+?(?<data>.+))?");
        Matcher matches = p.matcher(input);

        if(!matches.find()) {
            return "Oops, command not found";
        }

        int count = matches.groupCount();

        String command = matches.group("command");
        String param = matches.group("param");

        switch (command) {
            case "PUT":
                // NUMBER OF ARGUMENTS + 1 FOR REGEX MATCHES THE WHOLE STRING AS A GROUP
                if(4 <= count) {
                    storage.put(param, matches.group("data"));
                    return "success";
                }

                return "Number of arguments must be 3";

            case "GET":
                // NUMBER OF ARGUMENTS + 1 FOR REGEX MATCHES THE WHOLE STRING AS A GROUP
                if(!matches.find(3) && count >= 3) {
                    return storage.get(param);
                }

                return "Number of arguments must be 2";

            // TODO: support QUIT in regex
            case "QUIT":
                reset();
                return "Bye.";

            default:
                reset();
                return "Command not supported";
        }
    }

    protected String onWaiting(String input) {
        if(input.equalsIgnoreCase("HELLO")) {
            state = CONNECTED;
        }

        return "Connected to CacheServer v1.0, Please use GET/PUT commands to insert / receive data";
    }

    public void reset() {
        state = WAITING;
    }
}
