package com.example.lab3_1.Activity;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lab3_1.DB.DBHelper;
import com.example.lab3_1.R;

public class MainActivity extends AppCompatActivity {

    private boolean created = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("onCreate", "MainActivity\n-------------------------------------------------------\n");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button showButton = findViewById(R.id.describeButton);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        if (savedInstanceState != null)
            created = savedInstanceState.getBoolean("created");
        if (!created) {
            created = true;
            dbHelper.addFiveStudents();
        }
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShowActivity();
            }
        });
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbhelper = new DBHelper(getApplicationContext());
                dbhelper.addStudent();
            }
        });
        Button changeButton = findViewById(R.id.changeLastButton);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbhelper = new DBHelper(getApplicationContext());
                dbhelper.changeStudent();
            }
        });
    }

    @Override
    protected void onStart() {
        Log.i("onStart", "MainActivity");
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("onRestoreInstanceState", "MainActivity");
        super.onRestoreInstanceState(savedInstanceState);
        created = savedInstanceState.getBoolean("created");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("onSaveInstanceState", "MainActivity");
        super.onSaveInstanceState(outState);
        outState.putBoolean("created", created);
    }

    public void startShowActivity(){
        Intent intent = new Intent(MainActivity.this, ShowStudentActivity.class);
        startActivity(intent);
    }
}
