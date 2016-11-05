package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misaya.imool.DTO.TeacherInfo;
import com.example.misaya.imool.R;
import com.example.misaya.imool.Tool.CreatRandn;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SolveActivity extends Activity{

    private TeacherInfo info;
    private BluetoothAdapter bluetoothAdapter;
    private CreatRandn cRand = new CreatRandn();
    private String cName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        //final TextView text = (TextView) findViewById(R.id.textView);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "Local Bluetooth Can't Used", Toast.LENGTH_LONG).show();
            finish();
        }

        if(!bluetoothAdapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(),"Bluetooth Turned On",Toast.LENGTH_LONG).show();
        }

        Intent intent = getIntent();
        cName = intent.getStringExtra("cName");

        final Button start = (Button) findViewById(R.id.btn_start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MAC = bluetoothAdapter.getAddress();

                info = new TeacherInfo(cRand.created(), cName, MAC);

                bluetoothAdapter.setName(cName);

                /*text.append("Course_Name :" + info.getcName() + "\n\n");
                text.append("MAC_Address :" + info.getMAC() + "\n\n");
                text.append("RandNumber :" + info.getRandNum() + "\n\n");*/

                new Thread(thread).start();

                start.setVisibility(View.INVISIBLE);
            }
        });
    }

    private Thread thread = new Thread(){
        @Override
        public void run() {
            HttpURLConnection connection = null;
            try{
                URL url = new URL("http://192.168.23.3:8080/teacherServ");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(5000);
                connection.setRequestProperty("Charset", "UTF-8");

                OutputStream outputStream = connection.getOutputStream();
                Gson gson = new Gson();

                String content = "json=" + gson.toJson(info);
                outputStream.write(content.getBytes());

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String str;

                while((str = reader.readLine()) != null){
                    stringBuffer.append(str);
                }

                System.out.println(stringBuffer.toString());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(connection!=null)
                    connection.disconnect();
            }
        }
    };
}
