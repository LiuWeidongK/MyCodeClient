package com.example.misaya.imool.Tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil extends Thread{
    private String servletName;
    private String jsonStr;
    private String response;

    public HttpUtil(String servletName, String jsonStr) {
        this.servletName = servletName;
        this.jsonStr = jsonStr;
    }

    public void run() {
        HttpURLConnection connection = null;
        try{
            URL url = new URL("http://192.168.23.3:8080/" + servletName);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Charset", "UTF-8");

            OutputStream outputStream = connection.getOutputStream();

            String content = "json=" + jsonStr;
            outputStream.write(content.getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;

            while((str = reader.readLine()) != null){
                response += str;
            }

            //System.out.println(response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(connection!=null)
                connection.disconnect();
        }
    }

    public String getResponse() {
        return response;
    }
}
