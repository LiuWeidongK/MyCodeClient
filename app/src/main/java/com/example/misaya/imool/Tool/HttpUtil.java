package com.example.misaya.imool.Tool;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil extends Thread{
    private String servletName;
    private String jsonStr;
    private String response;
    private Handler handler;

    public HttpUtil(String servletName, String jsonStr, Handler handler) {
        this.servletName = servletName;
        this.jsonStr = jsonStr;
        this.handler = handler;
    }

    public void run() {
        HttpURLConnection connection = null;
        try{
            Message msg = new Message();
            msg.what = 0x456;
            handler.sendMessage(msg);

            URL url = new URL("http://192.168.23.3:8080/" + servletName);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Charset", "UTF-8");

            OutputStream outputStream = connection.getOutputStream();

            String content = jsonStr;
            outputStream.write(content.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            if(connection.getResponseCode() == 200){
                String readline;
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                while(((readline = reader.readLine()) != null)){
                    stringBuilder.append(readline);
                }
                response = stringBuilder.toString();
            } else {
                response = "SERVER_ERROR";
            }
        }catch (IOException e){
            response = "TIME_OUT";
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
