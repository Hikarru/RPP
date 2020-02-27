package com.example.rinat.lab2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {
    // Declare Variables
    ListView listview;
    ListViewAdapter adapter;
    Serializable downloadArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.listview_main);

        downloadArray = getIntent().getSerializableExtra("Downloaded");

        // Locate the listview in listview_main.xml
        listview = (ListView) findViewById(R.id.listview);
        // Pass the results into ListViewAdapter.java
        adapter = new ListViewAdapter(MainActivity.this, (ArrayList<HashMap<String, String>>) downloadArray);
        // Set the adapter to the ListView
        listview.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("position", listview.getFirstVisiblePosition());
        savedInstanceState.putSerializable("array", downloadArray);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        downloadArray = savedInstanceState.getSerializable("array");
        listview.setSelection(savedInstanceState.getInt("position"));
    }
}