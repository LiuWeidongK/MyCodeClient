package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.misaya.imool.R;

import java.util.ArrayList;

public class TeacherActivity extends Activity{

    private EditText cName,section_1,section_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        this.bottomMuem();

        cName = (EditText) findViewById(R.id.ed_course);
        section_1 = (EditText) findViewById(R.id.et_section1);
        section_2 = (EditText) findViewById(R.id.et_section2);
        Button submit = (Button) findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                list.add(0,cName.getText().toString());
                list.add(1,section_1.getText().toString());
                list.add(2,section_2.getText().toString());

                Intent intent = new Intent(TeacherActivity.this,SolveActivity.class);
                intent.putStringArrayListExtra("infolist",list);

                startActivity(intent);
            }
        });
    }

    private void bottomMuem(){
        Button home = (Button) findViewById(R.id.btn_tea_home);
        Button discovery = (Button) findViewById(R.id.btn_tea_discover);
        ImageView middle = (ImageView) findViewById(R.id.iv_tea_middle);
        Button _class = (Button) findViewById(R.id.btn_tea_class);
        Button mine = (Button) findViewById(R.id.btn_tea_mine);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this, TeacherActivity.class);
                startActivity(intent);
            }
        });

        discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this, DiscoveryActivity.class);
                startActivity(intent);
            }
        });

        middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences_1 = getSharedPreferences("USE_COUNTS", Context.MODE_PRIVATE);
                SharedPreferences preferences_2 = getSharedPreferences("USER_TYPE", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_1 = preferences_1.edit();
                editor_1.clear();
                editor_1.commit();
                SharedPreferences.Editor editor_2 = preferences_2.edit();
                editor_2.clear();
                editor_2.commit();
                Toast.makeText(getApplication(),"SharedPreferences has cleaned!",Toast.LENGTH_LONG).show();
            }
        });

        _class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this, ClassActivity.class);
                startActivity(intent);
            }
        });

        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this, MineActivity.class);
                startActivity(intent);
            }
        });
    }
}
