package com.opal.pubsub.commands;

import com.opal.pubsub.PubSub;

public interface CommandInterface {
    void execute(PubSub pubSub);
}
