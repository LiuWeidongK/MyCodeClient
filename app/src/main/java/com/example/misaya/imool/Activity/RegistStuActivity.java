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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misaya.imool.DAO.RegistStudentInfo;
import com.example.misaya.imool.R;
import com.example.misaya.imool.Tool.CheckUtil;
import com.example.misaya.imool.Tool.HttpUtil;
import com.example.misaya.imool.Tool.JsonUtil;

public class RegistStuActivity extends Activity{
    private EditText name,_class,id,user,pass_1,pass_2;
    private TextView jump,toLogin;
    private TextView c_name,c_class,c_id,c_user,c_pass1,c_pass2;
    private RadioGroup radioGroup;
    private String gender;
    private Button regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_student);

        this.init();

        final CheckUtil checkUtil = new CheckUtil();

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(checkUtil.checkName(name.getText().toString())) {
                        setTextTrue(c_name);
                    } else {
                        setTextFalse(c_name);
                    }
                }
            }
        });

        _class.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(checkUtil.checkClass(_class.getText().toString())) {
                        setTextTrue(c_class);
                    } else {
                        setTextFalse(c_class);
                    }
                }
            }
        });

        id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(checkUtil.checkId(id.getText().toString())) {
                        setTextTrue(c_id);
                    } else {
                        setTextFalse(c_id);
                    }
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_reg_stu_male:
                        gender = "male";
                        break;
                    case R.id.rb_reg_stu_female:
                        gender = "female";
                        break;
                    default:
                        break;
                }
            }
        });

        user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (checkUtil.checkUser(user.getText().toString())) {
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
                if (!hasFocus) {
                    if (checkUtil.checkPass(pass_1.getText().toString())) {
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
                if (!hasFocus) {
                    if (checkUtil.checkAgainPass(pass_1.getText().toString(), pass_2.getText().toString())) {
                        setTextTrue(c_pass2);
                    } else {
                        setTextFalse(c_pass2);
                    }
                }
            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistStuActivity.this, LoginActivity.class);
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

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUtil.checkName(name.getText().toString())
                        && checkUtil.checkClass(_class.getText().toString())
                        && checkUtil.checkId(id.getText().toString())
                        && checkUtil.checkUser(user.getText().toString())
                        && checkUtil.checkPass(pass_1.getText().toString())
                        && checkUtil.checkAgainPass(pass_1.getText().toString(), pass_2.getText().toString())) {
                    /*
                        注册成功 ※ 还需要添加本地事件 以及数据库信息
                     */
                    addPreferences();

                    RegistStudentInfo sInfo = new RegistStudentInfo(
                            name.getText().toString(),
                            _class.getText().toString(),
                            id.getText().toString(),
                            gender,
                            user.getText().toString(),
                            pass_1.getText().toString());
                    HttpUtil httpUtil = new HttpUtil("studentRegServ", JsonUtil.ObjectToJson(sInfo)); //servlet name
                    httpUtil.start();

                    try {
                        httpUtil.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplication(),httpUtil.getResponse(),Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegistStuActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    registAlert();
                }
            }
        });
    }

    private void addPreferences(){
        SharedPreferences preferences = getSharedPreferences("STUDENT_INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("NAME", name.getText().toString());
        editor.putString("CLASS", _class.getText().toString());
        editor.putString("ID", id.getText().toString());
        editor.putString("GENDER", gender);
        editor.putString("USERNAME",user.getText().toString());
        editor.putString("PASSWORD", pass_1.getText().toString());
        editor.apply();
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
                Intent intent = new Intent(RegistStuActivity.this, MainActivity.class);
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
        view.setTextColor(Color.rgb(255, 0, 0));
    }

    private void init(){
        name = (EditText) findViewById(R.id.et_reg_stu_name);
        _class = (EditText) findViewById(R.id.et_reg_stu_class);
        id = (EditText) findViewById(R.id.et_reg_stu_id);
        user = (EditText) findViewById(R.id.et_reg_stu_user);
        pass_1 = (EditText) findViewById(R.id.et_reg_stu_pass1);
        pass_2 = (EditText) findViewById(R.id.et_reg_stu_pass2);
        jump = (TextView) findViewById(R.id.tv_reg_stu_jump);
        toLogin = (TextView) findViewById(R.id.tv_reg_stu_tologin);
        regist = (Button) findViewById(R.id.btn_reg_stu_regist);
        c_name = (TextView) findViewById(R.id.tv_reg_stu_cname);
        c_class = (TextView) findViewById(R.id.tv_reg_stu_cclass);
        c_id = (TextView) findViewById(R.id.tv_reg_stu_cid);
        c_user = (TextView) findViewById(R.id.tv_reg_stu_cuser);
        c_pass1 = (TextView) findViewById(R.id.tv_reg_stu_cpass1);
        c_pass2 = (TextView) findViewById(R.id.tv_reg_stu_cpass2);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
    }
}
