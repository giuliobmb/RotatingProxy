package com.giuliobmb;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Connection implements Runnable{

    Socket client;
    protected String first_row;
    protected String[] content;


    Connection(Socket client){
        this.client = client;
    }
    @Override
    public void run() {

        // CONNECTION FETCHING
        int i = 0;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            this.first_row = in.readLine();
            this.content[i] = in.readLine();
            while(this.content[i] != null){
                i++;
                System.out.println(this.content[i]);
                content[i] = in.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occured while fetching the request content");
        }


        // MAKING REQUEST TO THE SERVER

        HashMap<String, String> decoded = decode_first(first_row);

        try {
            Socket proxiedRequest = new Socket(decoded.get("address"), Integer.parseInt(decoded.get("port")));
            PrintWriter out = new PrintWriter(proxiedRequest.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(proxiedRequest.getInputStream()));

            for (int j = 0; j < content.length; j++) {
                out.println(content[i]);
            }
            StringBuilder sb = new StringBuilder();
            while (true) {
                if (in.ready()) {
                    i = 0;
                    while (i != -1) {
                        i = in.read();
                        sb.append((char) i);
                    }
                    break;
                }
            }
            System.out.println(sb.toString());
            proxiedRequest.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
}
