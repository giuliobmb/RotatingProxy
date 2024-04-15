package com.giuliobmb;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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
            raw.add(in.readLine();
            while(content.get(i) != null){
                //System.out.println(content.get(i));
                i++;
                raw.add(in.readLine());
            }
        } catch (IOException e) {
            System.out.println("An error occured while fetching the request content");
        }


        // MAKING REQUEST TO THE SERVER

        content = decode(raw);

        System.out.println(content.toString());
        /*
        StringBuilder sb;
        try {
            Socket proxiedRequest = new Socket(decoded.get("address"), Integer.parseInt(decoded.get("port")));
            PrintWriter out = new PrintWriter(proxiedRequest.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(proxiedRequest.getInputStream()));

            for (int j = 0; j < content.size(); j++) {
                out.println(content.get(i));
            }
            sb = new StringBuilder();

            BufferedReader bufRead = new BufferedReader(new InputStreamReader(proxiedRequest.getInputStream()));
            String outStr;

            //Prints each line of the response
            while((outStr = bufRead.readLine()) != null){
                System.out.println(outStr);
            }


            //Closes out buffer and writer
            bufRead.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */
        //RESPONSE TO THE ORIGINAL SERVER

    }


    private HashMap<String, String> decode(String first_row){
        //TODO DECODING METHOD
    }


}
