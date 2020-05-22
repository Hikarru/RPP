package com.example.lab5;

import android.os.Bundle;

import com.example.lab5.PagerAdapter;
import com.example.lab5.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabs;
    private ViewPager viewPager;
    private TabItem tab1, tab2;
    private PagerAdapter pagerAdapter;
    public final static String USER_ID ="user4358497563948544";
    public final static String URL = "https://api.thecatapi.com/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabs = (TabLayout) findViewById(R.id.tabs);
        tab1 = (TabItem)findViewById(R.id.search);
        tab2 = (TabItem)findViewById(R.id.likes);
        viewPager = (ViewPager) findViewById(R.id.view);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabs.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }
}