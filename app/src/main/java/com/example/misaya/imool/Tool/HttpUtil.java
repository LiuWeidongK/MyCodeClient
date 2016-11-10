package com.example.misaya.imool.Tool;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil extends Thread{
    private String servletName;
    private String jsonStr;
    private String response = null;

    public HttpUtil(String servletName, String jsonStr) {
        this.servletName = servletName;
        this.jsonStr = jsonStr;
    }

    public void run() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try{
            URL url = new URL("http://192.168.23.3:8080/" + servletName);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Charset", "UTF-8");

            OutputStream outputStream = connection.getOutputStream();

            String content = "json=" + jsonStr;
            outputStream.write(content.getBytes());

            if(connection.getResponseCode() == 200){
                String readline = null;
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                while(((readline = reader.readLine()) != null)){
                    response += readline;
                }
            }
            Log.i("response:",response);
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
