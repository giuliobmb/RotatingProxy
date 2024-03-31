package com.giuliobmb;
import java.net.Socket;

public class Connection implements Runnable{

    Socket client;
    protected String headers;
    protected String payload;
    Connection(Socket client){
        this.client = client;
    }
    @Override
    public void run() {

    }
}
