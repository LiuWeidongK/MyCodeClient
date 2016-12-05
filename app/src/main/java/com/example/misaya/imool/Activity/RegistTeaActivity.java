package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misaya.imool.DAO.RegistTeacherInfo;
import com.example.misaya.imool.R;
import com.example.misaya.imool.Tool.CheckUtil;
import com.example.misaya.imool.Tool.HttpUtil;
import com.example.misaya.imool.Tool.JsonUtil;
import com.example.misaya.imool.Tool.MD5Util;

public class RegistTeaActivity extends Activity{
    private EditText user,pass_1,pass_2;
    private TextView jump,toLogin;
    private TextView c_user,c_pass1,c_pass2;
    private Button regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_teacher);

        this.init();

        final CheckUtil checkUtil = new CheckUtil();

        user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(checkUtil.checkUser(user.getText().toString())) {
                        setTextTrue(c_user);
                    } else {
                        setTextFalse(c_user);
                    }
                }
            }
        });

        pass_1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(checkUtil.checkPass(pass_1.getText().toString())) {
                        setTextTrue(c_pass1);
                    } else {
                        setTextFalse(c_pass1);
                    }
                }
            }
        });

        pass_2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(checkUtil.checkAgainPass(pass_1.getText().toString(), pass_2.getText().toString())) {
                        setTextTrue(c_pass2);
                    } else {
                        setTextFalse(c_pass2);
                    }
                }
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUtil.checkUser(user.getText().toString())
                        && checkUtil.checkPass(pass_1.getText().toString())
                        && checkUtil.checkAgainPass(pass_1.getText().toString(), pass_2.getText().toString())) {
                    /*
                        注册成功 ※ 还需要添加本地事件 以及数据库信息
                     */
                    SharedPreferences preferences = getSharedPreferences("TEACHER_INFO", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("USERNAME", user.getText().toString());
                    //editor.putString("PASSWORD", pass_1.getText().toString());
                    editor.apply();

                    RegistTeacherInfo tInfo = new RegistTeacherInfo(user.getText().toString(), MD5Util.getMD5(pass_1.getText().toString()));
                    HttpUtil httpUtil = new HttpUtil("teacherRegServ", JsonUtil.ObjectToJson(tInfo));     //servlet name
                    httpUtil.start();
                    try {
                        httpUtil.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    switch (httpUtil.getResponse()) {
                        case "TRUE":
                            Toast.makeText(getApplication(),"Regist successful!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegistTeaActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case "FALSE":
                            Toast.makeText(getApplication(),"Regist failure!",Toast.LENGTH_LONG).show();
                            break;
                        case "TIME_OUT":
                            Toast.makeText(getApplication(),"Time out!",Toast.LENGTH_LONG).show();
                            break;
                        case "SERVER_ERROR":
                            Toast.makeText(getApplication(),"Server error!",Toast.LENGTH_LONG).show();
                            break;
                        default:
                            break;
                    }
                } else {
                    registAlert();
                }
            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistTeaActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpAlert();
            }
        });
    }

    private void registAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please update errors and do it again!");
        builder.setTitle("Prompt");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void jumpAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to give up the register?");
        builder.setTitle("Prompt");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RegistTeaActivity.this, MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
                finish();
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

    private void setTextTrue(TextView view) {
        view.setText("√");
        view.setTextColor(Color.rgb(0, 255, 0));
    }

    private void setTextFalse(TextView view) {
        view.setText("×");
        view.setTextColor(Color.rgb(255,0,0));
    }

    private void init(){
        user = (EditText) findViewById(R.id.et_reg_tea_user);
        pass_1 = (EditText) findViewById(R.id.et_reg_tea_pass1);
        pass_2 = (EditText) findViewById(R.id.et_reg_tea_pass2);
        jump = (TextView) findViewById(R.id.tv_reg_tea_jump);
        toLogin = (TextView) findViewById(R.id.tv_reg_tea_tologin);
        regist = (Button) findViewById(R.id.btn_reg_tea_regist);
        c_user = (TextView) findViewById(R.id.tv_reg_tea_checkuser);
        c_pass1 = (TextView) findViewById(R.id.tv_reg_tea_checkpass1);
        c_pass2 = (TextView) findViewById(R.id.tv_reg_tea_checkpass2);
     }
}
