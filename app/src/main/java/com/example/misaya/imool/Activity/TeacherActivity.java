package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.misaya.imool.R;

public class TeacherActivity extends Activity{

    private EditText cName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        cName = (EditText) findViewById(R.id.ed_course);
        Button submit = (Button) findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeacherActivity.this,SolveActivity.class);

                intent.putExtra("cName",cName.getText().toString());

                startActivity(intent);
            }
        });
    }
}
