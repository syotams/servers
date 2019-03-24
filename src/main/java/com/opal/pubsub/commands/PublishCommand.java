package com.opal.pubsub.commands;

import com.opal.pubsub.PubSub;

public class PublishCommand implements CommandInterface {

    private String channel;

    private String data;

    public PublishCommand(String channel, String data) {
        this.channel = channel;
    }

    public String getCommand() {
        return "PUBLISH";
    }

    public String getChannel() {
        return channel;
    }

    public String getData() {
        return data;
    }

    @Override
    public void execute(PubSub pubSub) {

    }
}
