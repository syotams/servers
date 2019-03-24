package com.opal.cache.server;

import com.opal.server.ConnectionHandlerInterface;
import com.opal.server.ServerProtocolInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements ConnectionHandlerInterface {

    private ServerProtocolInterface protocol;


    public ConnectionHandler(ServerProtocolInterface protocol) {
        this.protocol = protocol;
    }

    public void onConnection(Socket socket) {
        String clientInput;

        try(
            BufferedReader in   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out     = new PrintWriter(socket.getOutputStream(), true)
        ) {
            while (null != (clientInput = in.readLine())) {
                // if client closes connection, we do not want to use null reference
                if(clientInput.length() == 0) {
                    break;
                }

                System.out.println(clientInput);
                out.println(protocol.process(clientInput));
            }

            this.close(out);
        } catch (IOException e) {
            protocol.reset();
            // we set catch here if socket is closed by client, so server won't be closed
            System.out.println("Connection closed by client");
        }
    }

    private void close(PrintWriter write) {
        write.println("Bye!");
        protocol.reset();
    }
}
