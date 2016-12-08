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
    private SharedPreferences preferences_rem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.init();
        preferences_rem = getSharedPreferences("REMEMBER", Context.MODE_PRIVATE);
        if(preferences_rem.getBoolean("ISCHECKED", false)) {
            remeberpass.setChecked(true);
            user.setText(preferences_rem.getString("USERNAME", ""));
            pass.setText(preferences_rem.getString("PASSWORD", ""));
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
                        preferences.edit().putBoolean("IS_LOGIN",true).apply();         //此处是两个不同的preferences
                        if(remeberpass.isChecked()) {
                            SharedPreferences.Editor editor = preferences_rem.edit();
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
