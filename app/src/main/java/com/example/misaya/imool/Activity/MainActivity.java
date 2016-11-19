package com.example.misaya.imool.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.misaya.imool.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        this.isGuide();

        SharedPreferences preferences = getSharedPreferences("USER_TYPE", Context.MODE_PRIVATE);
        String type = preferences.getString("TYPE","");
        if(type.equals("STUDENT")){
            Intent intent = new Intent(MainActivity.this,StudentActivity.class);
            startActivity(intent);
        } else if(type.equals("TEACHER")){
            Intent intent = new Intent(MainActivity.this, TeacherActivity.class);
            startActivity(intent);
        }
    }

    private void isGuide(){
        SharedPreferences preferences = getSharedPreferences("USE_COUNTS", Context.MODE_PRIVATE);
        int count = preferences.getInt("count", 0);
        if(count == 0){
            Intent intent = new Intent(MainActivity.this, GuideActivity.class);
            startActivity(intent);
            this.finish();
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("count",++count);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
