package com.opal.pubsub.client;

import com.opal.server.helpers.Console;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Publisher implements Callable<Long> {

    private String host, channel;

    private int port;

    private int counter;

    private long timeElapsed;


    public Publisher(String host, int port, int counter, String channel) {
        this.host = host;
        this.port = port;
        this.counter = counter;
        this.channel = channel;
    }

    public static void main(String[] args) throws InterruptedException {
        String host = Console.getArgument(args, 0, "localhost");
        int port    = Console.getInt(args, 1, "4000");
        int total   = Console.getInt(args, 2, "9000");
        String channel  = Console.getArgument(args, 3, "default");

        List<Publisher> publishers = new ArrayList<>();

        for(int i=0; i<total; i++) {
            publishers.add(new Publisher(host, port, i, channel));
        }

        ExecutorService executor = Executors.newFixedThreadPool(total);

        long startTime = System.nanoTime();

        executor.invokeAll(publishers);
        executor.shutdown();
        // Wait until all threads are finished
        while (!executor.isTerminated()) {}

        long elapsedTime = System.nanoTime() - startTime;

        long eachElapsedTime = sumOfTimeElapsed(publishers);

        System.out.println("*******************************************************");
        System.out.println(String.format("Publish of %d events in %d ms",
                total,
                TimeUnit.NANOSECONDS.toMillis(elapsedTime)));
        System.out.println(String.format("Publish total all measured execution is %d ms",
                TimeUnit.NANOSECONDS.toMillis(eachElapsedTime)));
        System.out.println(String.format("Publish average execution is %d ms",
                TimeUnit.NANOSECONDS.toMillis(eachElapsedTime/total)));
    }

    @Override
    public Long call() {
        long startTime = System.nanoTime();

        try (
                Socket socket = new Socket(host, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println(String.format("PUBLISH %s %s-%d", channel, channel, counter));
        } catch (IOException e) {
            e.printStackTrace();
        }

        timeElapsed = System.nanoTime() - startTime;

        return timeElapsed;
    }

    private static long sumOfTimeElapsed(List<Publisher> publishers) {
        long eachElapsedTime = 0;

        for (Publisher publisher : publishers) {
            eachElapsedTime += publisher.timeElapsed;
        }

        return eachElapsedTime;
    }
}
