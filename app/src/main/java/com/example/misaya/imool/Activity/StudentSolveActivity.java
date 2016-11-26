package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.misaya.imool.DAO.StudentInfo_Macs;
import com.example.misaya.imool.R;
import com.example.misaya.imool.Tool.HttpUtil;
import com.example.misaya.imool.Tool.JsonUtil;

import java.util.ArrayList;

public class StudentSolveActivity extends Activity {

    private String randnum,id,name;
    private BluetoothAdapter bluetoothAdapter;
    private ProgressDialog dialog;
    StudentInfo_Macs sinfo_macs;
    ArrayList<String> mac_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsolve);

        ArrayList<String> infolist = getIntent().getStringArrayListExtra("infolist");
        randnum = infolist.get(0);
        id = infolist.get(1);
        name = infolist.get(2);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        this.BlueToothOn();

        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, mFilter);
        mFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){          //获取返回值
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0x123){                                    //3.get code
            if(resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "You Have Allowed This Operation.", Toast.LENGTH_LONG).show();

                dialog = ProgressDialog.show(StudentSolveActivity.this, "Title", "Searching...Please wait.");    //4.show dialog

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        bluetoothAdapter.startDiscovery();          //5.start search 12s
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
                mac_list.add(device.getAddress());                          //delete
            }else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                dialog.dismiss();                                           //7.search finished
                sendInfo();
            }
        }
    };

    private void showAlert(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg + "Do you want to do it again?");
        builder.setTitle("Prompt");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BlueToothOn();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BlueToothOff();
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    private void sendInfo(){
        sinfo_macs = new StudentInfo_Macs(randnum,id,mac_list);         //需要学生姓名
        HttpUtil httpUtil = new HttpUtil("studentServ", JsonUtil.ObjectToJson(sinfo_macs));
        httpUtil.start();

        try {
            httpUtil.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i("@@httpGetResponse@@", httpUtil.getResponse());

        if(httpUtil.getResponse().equals("TRUE")){
            Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_LONG).show();
            BlueToothOff();
            finish();
        } else if (httpUtil.getResponse().equals("FALSE")){
            showAlert("Fail to search!");                    //匹配失败 询问是否重新
        } else if(httpUtil.getResponse().equals("TIME_OUT")){
            showAlert("Network connections error!");
        } else if(httpUtil.getResponse().equals("SERVER_ERROR")){
            showAlert("Server error!");
        }
    }

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
