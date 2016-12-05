package com.example.misaya.imool.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misaya.imool.Fragment.StudentFragment;
import com.example.misaya.imool.Fragment.MessageFragment;
import com.example.misaya.imool.Fragment.MineFragment;
import com.example.misaya.imool.Fragment.TeacherFragment;
import com.example.misaya.imool.R;
import com.example.misaya.imool.Tool.ViewPagerFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    TextView titleTextView;
    private String userType;
    public LinearLayout firstLinearLayout;
    public LinearLayout secondLinearLayout;
    public LinearLayout middleLinearLayout;
    public LinearLayout thirdLinearLayout;
    public LinearLayout forthLinearLayout;
    ViewPager mViewPager;
    ViewPagerFragmentAdapter mViewPagerFragmentAdapter;
    FragmentManager mFragmentManager;

    String[] titleName = new String[] {"Teacher","Student","Message","Mine"};
    List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.isGuide();
        SharedPreferences preferences = getSharedPreferences("USER_TYPE", Context.MODE_PRIVATE);
        userType = preferences.getString("TYPE", "NULL");
        mFragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        initFragmetList();
        mViewPagerFragmentAdapter = new ViewPagerFragmentAdapter(mFragmentManager,mFragmentList);
        initView();
        initViewPager();
    }

    private void isGuide(){
        SharedPreferences preferences_1 = getSharedPreferences("USE_COUNTS", Context.MODE_PRIVATE);
        SharedPreferences preferences_2 = getSharedPreferences("USER_TYPE", Context.MODE_PRIVATE);
        int count = preferences_1.getInt("COUNTS", 0);
        String type = preferences_2.getString("TYPE", "NULL");
        if(count == 0 || type.equals("NULL")){                  //防止首次进入之后退出
            Intent intent = new Intent(MainActivity.this, GuideActivity.class);
            startActivity(intent);
            this.finish();
        }
        SharedPreferences.Editor editor = preferences_1.edit();
        editor.putInt("COUNTS", ++count);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initViewPager() {
        mViewPager.addOnPageChangeListener(new ViewPageOnPagerChangedListener());
        mViewPager.setAdapter(mViewPagerFragmentAdapter);
        switch (userType) {
            case "TEACHER":
                mViewPager.setCurrentItem(0);
                titleTextView.setText(titleName[0]);
                updateBottomLinearLayoutSelect(true, false, false, false);
                break;
            case "STUDENT":
                mViewPager.setCurrentItem(1);
                titleTextView.setText(titleName[1]);
                updateBottomLinearLayoutSelect(false, true, false, false);
                break;
            default:
                break;
        }
    }

    private void Alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You have no permission!");
        builder.setTitle("Prompt");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void initFragmetList() {
        Fragment teacher = new TeacherFragment();
        Fragment student = new StudentFragment();
        Fragment message = new MessageFragment();
        Fragment mine = new MineFragment();
        mFragmentList.add(teacher);
        mFragmentList.add(student);
        mFragmentList.add(message);
        mFragmentList.add(mine);
    }

    public void initView() {
        titleTextView = (TextView) findViewById(R.id.ViewTitle);
        mViewPager = (ViewPager) findViewById(R.id.ViewPagerLayout);
        firstLinearLayout = (LinearLayout) findViewById(R.id.firstLinearLayout);
        firstLinearLayout.setOnClickListener(this);
        secondLinearLayout = (LinearLayout) findViewById(R.id.secondLinearLayout);
        secondLinearLayout.setOnClickListener(this);
        middleLinearLayout = (LinearLayout) findViewById(R.id.midLinearLayout);
        middleLinearLayout.setOnClickListener(this);
        thirdLinearLayout = (LinearLayout) findViewById(R.id.thirdLinearLayout);
        thirdLinearLayout.setOnClickListener(this);
        forthLinearLayout = (LinearLayout) findViewById(R.id.forthLinearLayout);
        forthLinearLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.firstLinearLayout:
                if(userType.equals("TEACHER")) {
                    mViewPager.setCurrentItem(0);
                    updateBottomLinearLayoutSelect(true,false,false,false);
                } else {
                    Alert();
                }
                break;
            case R.id.secondLinearLayout:
                if(userType.equals("STUDENT")) {
                    mViewPager.setCurrentItem(1);
                    updateBottomLinearLayoutSelect(false,true,false,false);
                } else {
                    Alert();
                }
                break;
            case R.id.midLinearLayout:
                this.cleanPreferences();
                break;
            case R.id.thirdLinearLayout:
                mViewPager.setCurrentItem(2);
                updateBottomLinearLayoutSelect(false,false,true,false);
                break;
            case R.id.forthLinearLayout:
                mViewPager.setCurrentItem(3);
                updateBottomLinearLayoutSelect(false,false,false,true);
                break;
            default:
                break;
        }
    }

    private void updateBottomLinearLayoutSelect(boolean f, boolean s, boolean t, boolean k) {
        firstLinearLayout.setSelected(f);
        secondLinearLayout.setSelected(s);
        thirdLinearLayout.setSelected(t);
        forthLinearLayout.setSelected(k);
    }

    private void cleanPreferences() {
        SharedPreferences preferences_1 = getSharedPreferences("USE_COUNTS", Context.MODE_PRIVATE);
        SharedPreferences preferences_2 = getSharedPreferences("USER_TYPE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_1 = preferences_1.edit();
        editor_1.clear();
        editor_1.apply();
        SharedPreferences.Editor editor_2 = preferences_2.edit();
        editor_2.clear();
        editor_2.apply();
        Toast.makeText(getApplicationContext(),"Preferences have been cleaned already!",Toast.LENGTH_LONG).show();
    }

    class ViewPageOnPagerChangedListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //Log.d(TAG, "onPageScrooled");
        }
        @Override
        public void onPageSelected(int position) {
            //Log.d(TAG, "onPageSelected");
            boolean[] state = new boolean[titleName.length];
            state[position] = true;
            titleTextView.setText(titleName[position]);
            updateBottomLinearLayoutSelect(state[0],state[1],state[2],state[3]);
        }
        @Override
        public void onPageScrollStateChanged(int state) {
            //Log.d(TAG, "onPageScrollStateChanged");
        }
    }
}
