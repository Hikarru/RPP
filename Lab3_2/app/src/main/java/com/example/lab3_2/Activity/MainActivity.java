package com.example.lab3_2.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lab3_2.DB.DBHelper;
import com.example.lab3_2.R;

public class MainActivity extends AppCompatActivity {

    private boolean created = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("onCreate", "MainActivity\n-------------------------------------------------------\n");
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getInt("DBVersion") != 0 && savedInstanceState.getInt("DBVersion") != -1)
            DBHelper.newVersion = savedInstanceState.getInt("DBVersion");
        if (savedInstanceState != null)
            created = savedInstanceState.getBoolean("created");
        setContentView(R.layout.activity_main);
        Button showButton = findViewById(R.id.describeButton), showButtonOld = findViewById(R.id.describeButtonOld), changeVersionOfDB = findViewById(R.id.changeDBVersion);
        if (!created)
            deleteDatabase("StudentsDB");
        final DBHelper dbHelper = new DBHelper(getApplicationContext());
        if (savedInstanceState != null)
            created = savedInstanceState.getBoolean("created");
        if (!created) {
            dbHelper.addFiveStudentsOld();
            created = true;
        }
        showButtonOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DBHelper.newVersion == 1)
                    startShowActivityOld();
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Необходимо сменить версию БД на 1, чтобы использовать эту кнопку", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DBHelper.newVersion == 2)
                    startShowActivity();
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Необходимо сменить версию БД на 2, чтобы использовать эту кнопку", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbhelper = new DBHelper(getApplicationContext());
                if (DBHelper.newVersion == 2)
                    dbhelper.addStudent();
                else
                    dbhelper.addStudentOld();
            }
        });
        Button changeButton = findViewById(R.id.changeLastButton);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbhelper = new DBHelper(getApplicationContext());
                if (DBHelper.newVersion == 2)
                    dbhelper.changeStudent();
                else
                    dbhelper.changeStudentOld();
            }
        });
        changeVersionOfDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dbHelper.getNewVersion() == 2)
                    dbHelper.setNewVersion(1);
                else
                    dbHelper.setNewVersion(2);
                DBHelper dbhelper = new DBHelper(getApplicationContext(), dbHelper.getNewVersion());
                Toast toast = Toast.makeText(getApplicationContext(), "Была установлена БД версии " + Integer.toString(DBHelper.newVersion), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("onRestoreInstanceState", "MainActivity");
        super.onRestoreInstanceState(savedInstanceState);
        created = savedInstanceState.getBoolean("created");
        DBHelper.newVersion = savedInstanceState.getInt("DBVersion");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("onSaveInstanceState", "MainActivity");
        super.onSaveInstanceState(outState);
        outState.putBoolean("created", created);
        outState.putInt("DBVersion", DBHelper.newVersion);
    }

    public void startShowActivityOld(){
        Intent intent = new Intent(MainActivity.this, ShowStudentActivityOld.class);
        startActivity(intent);
    }

    public void startShowActivity(){
        Intent intent = new Intent(MainActivity.this, ShowStudentActivity.class);
        startActivity(intent);
    }
}
