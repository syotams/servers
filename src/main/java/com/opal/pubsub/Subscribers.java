package com.opal.pubsub;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Subscribers extends Thread {

    private final LinkedListQueue<String> queue;

    private final List<Subscriber> subscribers = new ArrayList<>();

    private volatile boolean shouldRun = true;


    public Subscribers(LinkedListQueue<String> queue) {
        this.queue = queue;
    }

    public void addSubscriber(Subscriber subscriber) {
        synchronized (subscribers) {
            subscribers.add(subscriber);
        }
    }

    public void remove(Subscriber subscriber) {
        synchronized (subscribers) {
            subscribers.remove(subscriber);
        }
    }

    @Override
    public void run() {
        System.out.println("Subscriber thread started");

        String item;

        try {
            while (shouldRun) {
                if (null != (item = consume())) {
                    publish(item);
                    queue.dequeue();
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace(); // TODO: should handle exception and remove subscribers / restart
        }
    }

    // TODO: should remove closed connections from list
    private void publish(String item) {
        synchronized (subscribers) {
            for (Subscriber subscriber : subscribers) {
                subscriber.publish(item);
            }
        }
    }

    private String consume() throws InterruptedException {
        while(queue.size() == 0 && shouldRun) {
            synchronized (queue) {
                queue.wait();
            }
        }

        synchronized (queue) {
            queue.notifyAll();

            return queue.peek();
        }
    }

    void stopRunning() {
        shouldRun = false;
    }
}
