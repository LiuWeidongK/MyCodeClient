package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.misaya.imool.R;

public class ClassActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        
        this.bottomMuem();
    }

    private void bottomMuem(){
        Button home = (Button) findViewById(R.id.btn_cla_home);
        Button discovery = (Button) findViewById(R.id.btn_cla_discover);
        ImageView middle = (ImageView) findViewById(R.id.iv_cla_middle);
        Button _class = (Button) findViewById(R.id.btn_cla_class);
        Button mine = (Button) findViewById(R.id.btn_cla_mine);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassActivity.this, ClassActivity.class);
                startActivity(intent);
            }
        });

        discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassActivity.this, DiscoveryActivity.class);
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
                Toast.makeText(getApplication(), "SharedPreferences has cleaned!", Toast.LENGTH_LONG).show();
            }
        });

        _class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassActivity.this, ClassActivity.class);
                startActivity(intent);
            }
        });

        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassActivity.this, MineActivity.class);
                startActivity(intent);
            }
        });
    }
}
