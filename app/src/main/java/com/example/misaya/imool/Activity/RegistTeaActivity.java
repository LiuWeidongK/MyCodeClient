package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.misaya.imool.R;
import com.example.misaya.imool.Tool.CheckUtil;

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
                if (checkUtil.checkUser(user.getText().toString()) && checkUtil.checkPass(pass_1.getText().toString()) && checkUtil.checkAgainPass(pass_1.getText().toString(), pass_2.getText().toString())) {
                    /*
                        注册成功 ※ 还需要添加本地事件 以及数据库信息
                     */
                    Intent intent = new Intent(RegistTeaActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setMessage("Please update errors and do it again!");
                    builder.setTitle("Prompt");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistTeaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpAlert();
            }
        });
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
