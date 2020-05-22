package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.lab5.LikeFragment;
import com.example.lab5.SearchFregment;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    //Фрагмент поиска или фрагмент лайков
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SearchFregment();
            case 1:
                return new LikeFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}