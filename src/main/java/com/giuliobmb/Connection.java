package com.giuliobmb;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Connection implements Runnable{

    Socket client;

    protected ArrayList<String> raw;
    protected HashMap<String, String> content;


    Connection(Socket client){
        this.client = client;
        content = new HashMap<String, String>();
        raw = new ArrayList<String>();
    }
    @Override
    public void run() {

        // CONNECTION FETCHING
        int i = 0;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

            for (String line = in.readLine(); line != null; line = in.readLine()) {
                raw.add(line);
            }

        } catch (IOException e) {
            System.out.println("An error occured while fetching the request content");
        }

        // MAKING REQUEST TO THE SERVER

        content = decode(raw);
        if(content == null)
            Thread.currentThread().interrupt();

        //if(content != null)
            //System.out.println(content.toString());

        StringBuilder sb;
        try {
            Socket proxiedRequest = new Socket(content.get("host"), Integer.parseInt(content.get("port")));
            PrintWriter out = new PrintWriter(proxiedRequest.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(proxiedRequest.getInputStream()));
            System.out.println(raw.toString());
            for (int j = 0; j < raw.size(); j++) {
                if(!raw.get(i).contains("Proxy"))
                    out.println(raw.get(i));
            }
            out.println("");
            sb = new StringBuilder();

            BufferedReader bufRead = new BufferedReader(new InputStreamReader(proxiedRequest.getInputStream()));

            //Prints each line of the response
            for (String outStr = bufRead.readLine(); outStr != null; outStr = bufRead.readLine()) {
                System.out.println(outStr);
            }


            //Closes out buffer and writer
            bufRead.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //RESPONSE TO THE ORIGINAL SERVER

    }


    private HashMap<String, String> decode(ArrayList<String> raw){
        //TODO DECODING METHOD
        String data = null;
        //System.out.println(raw.toString());
        try {
            data = raw.get(1).toString();
        }catch(IndexOutOfBoundsException e){
            System.out.println("nulla");
            return null;
        }

        String[] splitted = data.split(":");

        String host = splitted[1];
        String port = null;
        try{
            port = splitted[2];
        }catch(IndexOutOfBoundsException e){
            port = "80";
        }


        HashMap<String, String> info = new HashMap<String, String>();

        info.put("host", host.trim());
        info.put("port", port);
        return info;
    }


}
