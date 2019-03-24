package com.opal.pubsub;

import com.opal.server.AbstractServerProtocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PubSubProtocol extends AbstractServerProtocol {

    private String command, channel, data;


    @Override
    protected String onWaiting(String input) {
        return null;
    }

    @Override
    protected String onConnected(String input) {
        System.out.println(input);

        command = channel = data = null;

        Pattern p = Pattern.compile("(?<command>[a-zA-Z]+?)\\s+(?<channel>\\w+)(\\s+?(?<data>.+))?");
        Matcher matches = p.matcher(input);

        if(!matches.find()) {
            return "Oops, command not found";
        }

        int count = matches.groupCount();

        String command = matches.group("command");

        try {
            switch (command) {
                case "PUBLISH":
                    handlePublish(matches, count);
                    break;

                case "SUBSCRIBE":
                    handleSubscribe(matches, count);
                    break;

                case "KEEP_ALIVE":
                    System.out.println("KEEP_ALIVE");
                    break;
            }
        } catch (Exception e) {
            return e.getMessage();
        }

        return command;
    }

    private void handleSubscribe(Matcher matches, int count) throws Exception {
        System.out.println("Processing subscribe");

        if(count < 2) {
            // throw exception
            throw new Exception("PublishCommand must be at least 2 phrases: SUBSCRIBE channelName");
        }

        command = "SUBSCRIBE";
        channel = matches.group("channel");

        System.out.println(String.format("Processed: %s %s", command, channel));
    }

    private void handlePublish(Matcher matches, int count) throws Exception {
        System.out.println("Processing publish");

        if(count < 3) {
            // throw exception
            throw new Exception("PublishCommand must be at least 2 phrases: PUBLISH channelName data");
        }

        command = "PUBLISH";
        channel = matches.group("channel");
        data = matches.group("data");

        System.out.println(String.format("Processed: %s %s %s", command, channel, data));
    }

    @Override
    public void reset() {

    }

    public String getCommand() {
        return command;
    }

    public String getChannel() {
        return channel;
    }

    public String getData() {
        return data;
    }
}
