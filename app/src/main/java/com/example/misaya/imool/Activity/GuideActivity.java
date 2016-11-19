package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.misaya.imool.R;

public class GuideActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidechoose);

        Button student = (Button) findViewById(R.id.btn_guide_stu);
        Button teacher = (Button) findViewById(R.id.btn_guide_tea);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("USER_TYPE", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("TYPE","STUDENT");
                editor.commit();
                Intent intent = new Intent(GuideActivity.this, RegistStuActivity.class);
                startActivity(intent);
            }
        });

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("USER_TYPE", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("TYPE","TEACHER");
                editor.commit();
                Intent intent = new Intent(GuideActivity.this, RegistTeaActivity.class);
                startActivity(intent);
            }
        });
    }
}
