package com.opal.pubsub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        String item;

        while (shouldRun) {
            try {
                item = consume();

                if (null != item) {
                    publish(item);
                    System.out.println(item);
                    queue.dequeue();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void publish(String item) {
        synchronized (subscribers) {
            long startTime = System.nanoTime();

            for (Subscriber subscriber : subscribers) {
                // TODO: this is not good pattern, try in for loop isn't efficient
                subscriber.publish(item);
            }

            long elapsedTime = (System.nanoTime() - startTime) / 1000;

            System.out.println("*******************************************************");
            System.out.println(String.format("Publish executed in %d ms", elapsedTime));
            System.out.println(String.format("Queue length is %d", queue.size()));
            System.out.println("*******************************************************");
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
