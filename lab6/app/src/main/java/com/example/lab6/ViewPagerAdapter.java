package com.example.lab6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Product> products;
    public ViewPagerAdapter(FragmentManager f, ArrayList<Product> list){
        super(f);
        products = list;
    }
    @Override
    public Fragment getItem(int position){
        return(PageFragment.newInstance(position, products));
    }
    @Override
    public int getItemPosition(@NonNull Object object){
        return(PagerAdapter.POSITION_NONE);
    }
    @Override
    public int getCount(){
        return products.size();
    }
}
