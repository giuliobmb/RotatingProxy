package com.giuliobmb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Proxy {
    ServerSocket listener;
    int port;
    Proxy(int port){
        this.port = port;
    }
    public Proxy(){
        this.port = 8888;
    }

    public void start() throws IOException {
        listener = new ServerSocket(8888);

        while(true){
            Socket clientSocket = listener.accept();
            Thread conn = new Thread(new ConnectionHandler(clientSocket));
            conn.start();
        }
    }

}
