package com.giuliobmb;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionHandler implements Runnable{

    Socket client;

    protected BufferedReader in;
    protected PrintWriter out;


    ConnectionHandler(Socket client){
        this.client = client;
        try {
            this.out = new PrintWriter(client.getOutputStream(), true);
            InputStream inputStream = client.getInputStream();
            this.in = new BufferedReader(new InputStreamReader(inputStream));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

    }
}
