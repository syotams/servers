package com.opal.pubsub;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Subscriber {
    private PrintWriter printer;

    public Subscriber(Socket socket) throws IOException {
        printer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void publish(String item) {
        printer.println(item);
    }

    public void disconnect() {
        printer.close();
    }
}
