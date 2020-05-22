package com.example.lab6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onStartStorefrontClick(View view) {
        Intent intent = new Intent(this, StorefrontActivity.class);
        startActivity(intent);
    }
    public void onStartBackendClick(View view) {
        Intent intent = new Intent(this, BackendActivity.class);
        startActivity(intent);
    }
}
