package com.opal.pubsub;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PubSub {

    // channels queue list
    private final Map<String, LinkedListQueue<String>> queues = new HashMap<>();

    // channel subscribers list
    private Map<String, Subscribers> channelSubscribers = new HashMap<>();


    public void publish(String channel, String item) {
        enqueue(channel, item);
    }

    public void subscribe(String channel, Socket socket) {
        LinkedListQueue<String> queue = getOrCreateQueueForChannel(channel); // FIXME: is it really needed here?
        Subscribers subscribers = channelSubscribers.get(channel);

        if(subscribers==null) {
            subscribers = new Subscribers(queue);
            channelSubscribers.put(channel, subscribers);
            subscribers.start();
        }

        Subscriber subscriber = null;

        try {
            subscriber = new Subscriber(socket);
            subscribers.addSubscriber(subscriber);
        } catch (IOException e) {
            if(null!=subscriber) subscribers.remove(subscriber);
        }
    }

    public void stopRunning() throws InterruptedException {
        for (Map.Entry<String, Subscribers> entry : channelSubscribers.entrySet()) {
            Subscribers subscribers = entry.getValue();
            subscribers.stopRunning();
            notifyQueues();
            subscribers.join();
        }
    }

    private void notifyQueues() {
        for (Map.Entry<String, LinkedListQueue<String>> entry : queues.entrySet()) {

            synchronized (queues) {
                entry.getValue().notifyAll();
            }
        }
    }

    public void queueStatus() {
        for (Map.Entry<String, LinkedListQueue<String>> entry : queues.entrySet()) {
            System.out.println(entry.getKey() + " has " + entry.getValue().size() + " items in queue");
        }
    }

    private void enqueue(String channel, String item) {
        getOrCreateQueueForChannel(channel).enqueue(item);
    }

    private LinkedListQueue<String> getOrCreateQueueForChannel(String channel) {
        LinkedListQueue<String> queue = queues.get(channel);

        if(null == queue) {
            queue = new LinkedListQueue<>();
            queues.put(channel, queue);
        }

        return queue;
    }
}
