package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misaya.imool.DAO.ResultInfo;
import com.example.misaya.imool.DAO.TeacherInfo;
import com.example.misaya.imool.R;
import com.example.misaya.imool.Tool.CreatRandn;
import com.example.misaya.imool.Tool.HttpUtil;
import com.example.misaya.imool.Tool.JsonUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TeacherSolveActivity extends Activity{

    TeacherInfo tinfo;
    CreatRandn cRand = new CreatRandn();
    Timer timer = new Timer();
    private BluetoothAdapter bluetoothAdapter;
    private TextView timer_text,rand_text,res_text;
    private Button start,stop;
    private String cName,section_1,section_2;
    private int recLen = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachersolve);

        timer_text = (TextView) findViewById(R.id.timer);
        rand_text = (TextView) findViewById(R.id.tv_randnum);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.BlueToothOn();         //open bluetooth

        ArrayList<String> infolist = getIntent().getStringArrayListExtra("infolist");
        cName = infolist.get(0);
        section_1 = infolist.get(1);
        section_2 = infolist.get(2);

        start = (Button) findViewById(R.id.btn_start);      //init
        stop = (Button) findViewById(R.id.btn_stop);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MAC = bluetoothAdapter.getAddress();
                tinfo = new TeacherInfo(cRand.created(), cName, MAC, section_1, section_2);

                bluetoothAdapter.setName(cName);

                rand_text.setText(tinfo.getRandNum());

                HttpUtil httpUtil = new HttpUtil("teacherServ", JsonUtil.ObjectToJson(tinfo));
                httpUtil.start();

                timer.schedule(task, 0, 1000);

                start.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.VISIBLE);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                   //点击停止之后 询问确定是否要停止
                stopAlert();
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
                    if(recLen < 0){                        //时间结束之后 询问是否要查看结果
                        timeoutAlert();
                    }
                }
            });
        }
    };

    private void timeoutAlert(){
        TimeOut();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Time up!Whether you want to display results?");
        builder.setTitle("Prompt");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getResult();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    private void stopAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Aer you sure to stop?");
        builder.setTitle("Prompt");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TimeOut();
                getResult();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void getResult(){
        HttpUtil httpUtil_c = new HttpUtil("ResultServ",tinfo.getRandNum());
        httpUtil_c.start();

        String result = httpUtil_c.getResponse();       //返回一个Json串 其中包含多组数据
        ResultInfo resultInfo = JsonUtil.JsonToObject(result,ResultInfo.class);         //@保存Json在本地

        res_text = (TextView) findViewById(R.id.tv_result);
        res_text.append("ID" + "\t" + "NAME" + "\n");
        if(resultInfo != null){
            ArrayList<String> list_ids = resultInfo.getIds();
            ArrayList<String> list_names = resultInfo.getNames();
            int length  = list_ids.size();
            for(int i=0;i<length;i++){
                res_text.append(list_ids.get(i) + "\t" + list_names.get(i) + "\n");
            }
        }
    }

    private void TimeOut(){
        timer.cancel();
        timer_text.setVisibility(View.GONE);
        stop.setVisibility(View.GONE);
        rand_text.setVisibility(View.GONE);
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
