package com.example.lab6;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class StorefrontActivity extends AppCompatActivity implements PageFragment.OnFragmentDataListener{

    public ViewPager viewPager;
    public static ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storefront);
        viewPager = findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), new DataBaseHelper(getApplicationContext()).getArrayList());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentDataListener(){adapter.notifyDataSetChanged();}

}
