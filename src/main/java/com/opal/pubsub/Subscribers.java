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
        long startTime = System.nanoTime();

        synchronized (subscribers) {
            for (Subscriber subscriber : subscribers) {
                // TODO: should remove closed connections from list
                subscriber.publish(item);
            }
        }

        long elapsedTime = (System.nanoTime() - startTime) / 1000;

        System.out.println("*******************************************************");
        System.out.println(String.format("Publish executed in %d ms",
                TimeUnit.NANOSECONDS.toMillis(elapsedTime)));
        System.out.println(String.format("Queue length is %d", queue.size()));
        System.out.println("*******************************************************");
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
