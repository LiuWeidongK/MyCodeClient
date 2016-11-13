package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misaya.imool.DAO.StudentInfo_Macs;
import com.example.misaya.imool.R;
import com.example.misaya.imool.Tool.HttpUtil;
import com.example.misaya.imool.Tool.JsonUtil;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class StudentActivity extends Activity{

    private EditText randnum,id;
    private TextView arround;
    private Button submit;
    private BluetoothAdapter bluetoothAdapter;
    private ProgressDialog dialog;
    private ProgressDialog dialog2;
    Handler handler;
    StudentInfo_Macs sinfo_macs;
    ArrayList<String> mac_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        randnum = (EditText) findViewById(R.id.ed_stu_rand);
        id = (EditText) findViewById(R.id.ed_stu_id);
        arround = (TextView) findViewById(R.id.arround);
        submit = (Button) findViewById(R.id.btn_stu_sub);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        this.BlueToothOn();                                                  //1.open

        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver,mFilter);
        mFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, mFilter);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0x456)
                    dialog2 = ProgressDialog.show(StudentActivity.this, "Title", "Searching...Please wait.");
            }
        };

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sinfo_macs = new StudentInfo_Macs(randnum.getText().toString(),id.getText().toString() , mac_list);

                HttpUtil httpUtil = new HttpUtil("studentServ",JsonUtil.ObjectToJson(sinfo_macs),handler);

                new Thread(httpUtil).start();
                try {
                    httpUtil.join();
                    //dialog2.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*if(httpUtil.getResponse().equals("true")){
                    Toast.makeText(getApplicationContext(),"Sucess",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                }*/
                //Log.i("response3",httpUtil.getResponse());
                Toast.makeText(getApplicationContext(),httpUtil.getResponse(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){          //获取返回值
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0x123){                                    //3.get code
            if(resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(),"You Have Allowed This Operation.",Toast.LENGTH_LONG).show();

                dialog = ProgressDialog.show(StudentActivity.this, "Title", "Searching...Please wait.");    //4.show dialog

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        bluetoothAdapter.startDiscovery();          //5.start search
                    }
                }).start();
            } else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"You Have Banned This Operation.",Toast.LENGTH_LONG).show();
            }
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {                     //广播类
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND)){                //6.searching
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arround.append(device.getAddress());
                mac_list.add(device.getAddress());      //delete
            }else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                dialog.dismiss();                                           //7.search finished
            }
        }
    };

    private void BlueToothOn(){
        if(bluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "Local Bluetooth Can't Used", Toast.LENGTH_LONG).show();
            finish();
        }
        Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(turnOn, 0x123);                                        //2.send intent
    }

    private void BlueToothOff(){
        if(bluetoothAdapter.isEnabled()){
            bluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(),"Bluetooth Turned Off",Toast.LENGTH_LONG).show();
        }
    }
}
