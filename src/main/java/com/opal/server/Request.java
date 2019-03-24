package com.opal.server;

import java.net.Socket;

public class Request {

    private Socket socket;
    private String data;


    public Request(Socket socket) {
        this.socket = socket;
    }

    void process() {

    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
