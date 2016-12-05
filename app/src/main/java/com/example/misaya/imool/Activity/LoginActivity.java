package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misaya.imool.DAO.LoginInfo;
import com.example.misaya.imool.R;
import com.example.misaya.imool.Tool.HttpUtil;
import com.example.misaya.imool.Tool.JsonUtil;

public class LoginActivity extends Activity{
    private EditText user,pass;
    private TextView regist,error_login;
    private CheckBox remeberpass;       //记住密码操作
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.init();
        SharedPreferences preferences = getSharedPreferences("REMEMBER", Context.MODE_PRIVATE);
        if(preferences.getBoolean("ISCHECKED", false)) {
            remeberpass.setChecked(true);
            user.setText(preferences.getString("USERNAME", ""));
            pass.setText(preferences.getString("PASSWORD", ""));
        }

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            }
        });

        error_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    无法登入操作
                 */
            }
        });

        /*remeberpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(remeberpass.isChecked())
                    preferences.edit().putBoolean("ISCHECKED",true).apply();
                else
                    preferences.edit().putBoolean("ISCHECKED",false).apply();
            }
        });*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().equals("")||pass.getText().toString().equals("")){
                    Toast.makeText(getApplication(),"用户名或密码为空",Toast.LENGTH_LONG).show();
                    return;
                }
                SharedPreferences preferences = getSharedPreferences("USER_TYPE", Context.MODE_PRIVATE);
                String type = preferences.getString("TYPE","");
                LoginInfo login = new LoginInfo(user.getText().toString(),pass.getText().toString(),type);
                HttpUtil httpUtil = new HttpUtil("loginServ", JsonUtil.ObjectToJson(login));
                httpUtil.start();
                try {
                    httpUtil.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                switch (httpUtil.getResponse()) {
                    case "TRUE":                                    //登入成功
                        Toast.makeText(getApplication(),"Login successful!",Toast.LENGTH_LONG).show();
                        if(remeberpass.isChecked()) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("USERNAME",user.getText().toString());
                            editor.putString("PASSWORD",pass.getText().toString());
                            editor.putBoolean("ISCHECKED",remeberpass.isChecked());
                            editor.apply();
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case "FALSE":
                        Toast.makeText(getApplication(),"Login failure!",Toast.LENGTH_LONG).show();
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
            }
        });
    }

    private void init(){
        user = (EditText) findViewById(R.id.et_log_user);
        pass = (EditText) findViewById(R.id.et_log_pass);
        regist = (TextView) findViewById(R.id.tv_log_regist);
        error_login = (TextView) findViewById(R.id.tv_log_cantlogin);
        remeberpass = (CheckBox) findViewById(R.id.cb_log_remeberpass);
        login = (Button) findViewById(R.id.btn_log_login);
    }
}
