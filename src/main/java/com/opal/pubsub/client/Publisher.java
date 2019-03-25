package com.opal.pubsub.client;

import com.opal.helpers.Console;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Publisher implements Runnable {

    private String host;

    private int port;

    private int counter;

    private long timeElapsed;


    public Publisher(String host, int port, int counter) {
        this.host = host;
        this.port = port;
        this.counter = counter;
    }

    public static void main(String args[]) {
        String host = Console.getArgument(args, 0, "localhost");
        int port    = Console.getInt(args, 1, "4000");
        int total   = Console.getInt(args, 2, "1000");

        List<Publisher> publishers = new ArrayList<>();

        for(int i=0; i<total; i++) {
            publishers.add(new Publisher(host, port, i));
        }

        ExecutorService executor = Executors.newFixedThreadPool(total);

        long startTime = System.nanoTime();

        for (Publisher publisher : publishers) {
            executor.execute(publisher);
        }

        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {}

        long elapsedTime = System.nanoTime() - startTime;

        long eachElapsedTime = sumOfTimeElapsed(publishers);

        System.out.println("*******************************************************");
        System.out.println(String.format("Publish total execution in %d ms",
                TimeUnit.NANOSECONDS.toMillis(elapsedTime)));
        System.out.println(String.format("Publish total measured execution is %d ms",
                TimeUnit.NANOSECONDS.toMillis(eachElapsedTime)));
        System.out.println(String.format("Publish average execution is %d ms",
                TimeUnit.NANOSECONDS.toMillis(eachElapsedTime/total)));
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();

        try (
                Socket socket = new Socket(host, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("PUBLISH default event-" + counter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        timeElapsed = System.nanoTime() - startTime;
    }

    private static long sumOfTimeElapsed(List<Publisher> publishers) {
        long eachElapsedTime = 0;

        for (Publisher publisher : publishers) {
            eachElapsedTime += publisher.timeElapsed;
        }

        return eachElapsedTime;
    }
}
