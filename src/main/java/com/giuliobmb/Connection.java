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
    protected String first_row;
    protected ArrayList<String> content;


    Connection(Socket client){
        this.client = client;
        content = new ArrayList<String>();
    }
    @Override
    public void run() {

        // CONNECTION FETCHING
        int i = 0;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            this.first_row = in.readLine();
            content.add(in.readLine());
            while(content.get(i) != null){
                //System.out.println(content.get(i));
                i++;
                System.out.println(content.add(in.readLine()));
                content.add(in.readLine());
            }
        } catch (IOException e) {
            System.out.println("An error occured while fetching the request content");
        }


        // MAKING REQUEST TO THE SERVER

        HashMap<String, String> decoded = decode_first(first_row);

        log_first(decoded);
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


    private HashMap<String, String> decode_first(String first_row){
        HashMap<String, String> decoded = new HashMap<String, String>();
        String[] parts = first_row.split(" ");

        try {
            String[] socket = parts[1].split(":");
            decoded.put("method", parts[0]);
            decoded.put("address", socket[0]);
            decoded.put("port", socket[1]);
            decoded.put("version", parts[2]);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
        return decoded;
    }

    private void log_first(HashMap<String, String> decoded){
        System.out.println(decoded.get("method"));
        System.out.println(decoded.get("address"));
        System.out.println(decoded.get("port"));
        System.out.println(decoded.get("version"));
    }

}
