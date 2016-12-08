package com.example.misaya.imool.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.misaya.imool.Activity.LoginActivity;
import com.example.misaya.imool.R;
public class MineFragment extends Fragment {

    private ImageView userpic;
    private TextView username,introduce;
    private RelativeLayout updata,history;
    private FrameLayout unLogin;
    private String userType,userName;
    private boolean is_Regist,is_Login,login_State;

    View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.activity_mine,container,false);
        }
        this.init();
        this.start();

        userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改头像
            }
        });

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_State) {
                    //修改用户名
                } else {
                    //登录
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改简介
            }
        });

        updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改信息页面
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //历史记录页面
            }
        });

        unLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("USER_TYPE", Context.MODE_PRIVATE);
                preferences.edit().putBoolean("IS_LOGIN",false).apply();
                unlogin_state();
            }
        });
        return mView;
    }

    private void init() {
        userpic = (ImageView) mView.findViewById(R.id.img_userpic);
        username = (TextView) mView.findViewById(R.id.mine_tv_username);
        introduce = (TextView) mView.findViewById(R.id.mine_tv_introduce);
        updata = (RelativeLayout) mView.findViewById(R.id.mine_layout_updatainfo);
        history = (RelativeLayout) mView.findViewById(R.id.mine_layout_history);
        unLogin = (FrameLayout) mView.findViewById(R.id.mine_layout_unlogin);

        SharedPreferences preferences_1 = getActivity().getSharedPreferences("USER_TYPE", Context.MODE_PRIVATE);
        userType = preferences_1.getString("TYPE", "");
        is_Regist = preferences_1.getBoolean("IS_REGIST",false);
        is_Login = preferences_1.getBoolean("IS_LOGIN",false);
        String keyName = userType + "_INFO";
        SharedPreferences preferences_2 = getActivity().getSharedPreferences(keyName,Context.MODE_PRIVATE);
        userName = preferences_2.getString("USERNAME", "");
    }

    private void start() {
        if(is_Login||is_Regist) {
            login_State = true;
            login_state();
        } else {
            login_State = false;
            unlogin_state();
        }
    }

    private void login_state() {        //登入状态
        username.setText(userName);
    }

    private void unlogin_state() {      //登出状态
        String str = "请登录...";
        username.setText(str);
        userpic.setImageResource(R.drawable.pet);       //默认图片
        unLogin.setVisibility(View.GONE);
        introduce.setVisibility(View.GONE);
    }
}