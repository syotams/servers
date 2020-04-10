package com.opal.server.file.server;

import com.opal.server.AbstractServerProtocol;

public class FileStorageProtocol extends AbstractServerProtocol {

    @Override
    protected String onWaiting(String input) {
        return null;
    }

    @Override
    protected String onConnected(String input) {
        // TODO: handle return file
        return null;
    }

    @Override
    public void reset() {

    }
}
