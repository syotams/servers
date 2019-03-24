package com.opal.pubsub.commands;

import com.opal.pubsub.PubSub;

public class SubscribeCommand implements CommandInterface {

    private String channel;


    public SubscribeCommand(String channel) {
        this.channel = channel;
    }

    public String getCommand() {
        return "SUBSCRIBE";
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public void execute(PubSub pubSub) {

    }
}
