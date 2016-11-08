package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
    private static Boolean SIGN = false;
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

        this.BlueToothOn();

        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver,mFilter);
        mFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver,mFilter);

        randnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SIGN){
                    bluetoothAdapter.startDiscovery();
                    //Toast.makeText(getApplicationContext(),"Check me!",Toast.LENGTH_SHORT).show();
                    submit.setEnabled(false);
                }
                SIGN = true;
            }
        });

        while(true){
            if(!bluetoothAdapter.isDiscovering()){
                submit.setEnabled(true);
                break;
            } else {
                continue;
            }
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bluetoothAdapter.isDiscovering()){
                    Toast.makeText(getApplicationContext(),"Searching.Please Wait!",Toast.LENGTH_SHORT);
                }else {
                    sinfo_macs = new StudentInfo_Macs(randnum.getText().toString(),id.getText().toString() , mac_list);
                    Toast.makeText(getApplicationContext(),mac_list.toString(),Toast.LENGTH_LONG).show();

                    HttpUtil httpUtil = new HttpUtil("studentServ",JsonUtil.ObjectToJson(sinfo_macs));
                    new Thread(httpUtil).start();
                    /*if(httpUtil.getResponse().equals("true")){
                        Toast.makeText(getApplicationContext(),"Sucess",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                    }*/
                    //Toast.makeText(getApplicationContext(),httpUtil.getResponse(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arround.append(device.getAddress());
                mac_list.add(device.getAddress());
            }else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                setProgressBarIndeterminateVisibility(false);
                setTitle("Search");
            }
        }
    };

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
