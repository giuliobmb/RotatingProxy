package com.giuliobmb;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestsAnalizer {
    public static void main(String[] args) {
        ServerSocket listener = null;
        BufferedReader in;
        try {
            listener = new ServerSocket(8888);

            while(true){
                Socket clientSocket = listener.accept();
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String s = in.readLine();
                do{
                    System.out.println(s);
                    s = in.readLine();
                }while(s != null);
            }
        }catch(IOException e){}



    }
}
