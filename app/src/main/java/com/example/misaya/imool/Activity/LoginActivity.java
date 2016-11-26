package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.misaya.imool.DAO.LoginInfo;
import com.example.misaya.imool.R;
import com.example.misaya.imool.Tool.HttpUtil;
import com.example.misaya.imool.Tool.JsonUtil;

public class LoginActivity extends Activity{
    private EditText user,pass;
    private TextView regist,error_login;
    private CheckBox remeberpass;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.init();

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, GuideActivity.class);
                startActivity(intent);
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
                LoginInfo login = new LoginInfo(user.getText().toString(),pass.getText().toString());
                HttpUtil httpUtil = new HttpUtil("loginServ", JsonUtil.ObjectToJson(login));
                httpUtil.start();
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
