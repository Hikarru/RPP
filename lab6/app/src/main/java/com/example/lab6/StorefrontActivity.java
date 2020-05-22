package com.example.lab6;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class StorefrontActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<Product> productList = new ArrayList<>();
    private DataBaseHelper databaseHelper;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storefront);

        databaseHelper = new DataBaseHelper(getApplicationContext());
        fillList();

        viewPager = findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), productList);
        viewPager.setAdapter(adapter);
    }

    public void onBuyClick(View view) {
        int pos = viewPager.getCurrentItem();

        String name = productList.get(pos).getName();
        String price = productList.get(pos).getPrice();
        int count = productList.get(pos).getCount();

        int newCount = databaseHelper.decrementCount(name, price, count);
        productList.get(pos).setCount(newCount);
        if (newCount == 0) {
            productList.remove(pos);
        }
        adapter.notifyDataSetChanged();
    }

    private void fillList() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DataBaseHelper.TABLE_NAME,null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(1);
                String price = cursor.getString(2);
                int count = cursor.getInt(3);

                if (count > 0) {
                    productList.add(new Product(name, price, count));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
    }
}
