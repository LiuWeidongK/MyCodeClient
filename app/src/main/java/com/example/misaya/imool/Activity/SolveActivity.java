package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misaya.imool.DAO.TeacherInfo;
import com.example.misaya.imool.R;
import com.example.misaya.imool.Tool.CreatRandn;
import com.example.misaya.imool.Tool.HttpUtil;
import com.example.misaya.imool.Tool.JsonUtil;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class SolveActivity extends Activity{

    TeacherInfo tinfo;
    private BluetoothAdapter bluetoothAdapter;
    private TextView timer_text,rand_text;
    private Button start,stop;
    CreatRandn cRand = new CreatRandn();
    private String cName;
    private int recLen = 20;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        timer_text = (TextView) findViewById(R.id.timer);
        rand_text = (TextView) findViewById(R.id.tv_randnum);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.BlueToothOn();         //open bluetooth

        Intent intent = getIntent();
        cName = intent.getStringExtra("cName");     //get extra

        start = (Button) findViewById(R.id.btn_start);      //init
        stop = (Button) findViewById(R.id.btn_stop);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MAC = bluetoothAdapter.getAddress();
                tinfo = new TeacherInfo(cRand.created(), cName, MAC);

                bluetoothAdapter.setName(cName);

                rand_text.setText(tinfo.getRandNum());

                HttpUtil httpUtil = new HttpUtil("teacherServ",JsonUtil.ObjectToJson(tinfo));
                httpUtil.run();

                //Toast.makeText(getApplication(),JsonUtil.ObjectToJson(tinfo),Toast.LENGTH_LONG).show();
                //httpUtil.run();

                timer.schedule(task, 0, 1000);

                start.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.VISIBLE);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeOut();

                //转到统计页面$
            }
        });
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    timer_text.setText(recLen/60 + ":" + recLen%60);
                    recLen--;
                    if(recLen < 0){
                        TimeOut();
                    }
                }
            });
        }
    };

    private void TimeOut(){

        timer.cancel();
        timer_text.setVisibility(View.GONE);
        BlueToothOff();

    }
    private void BlueToothOn(){
        if(bluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "Local Bluetooth Can't Used", Toast.LENGTH_LONG).show();
            finish();
        }

        if(!bluetoothAdapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(),"Bluetooth Turned On",Toast.LENGTH_LONG).show();
        }
    }

    private void BlueToothOff(){
        if(bluetoothAdapter.isEnabled()){
            bluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(),"Bluetooth Turned Off",Toast.LENGTH_LONG).show();
        }

    }
}
