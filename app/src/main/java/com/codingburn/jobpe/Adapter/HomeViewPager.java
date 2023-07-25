package com.codingburn.jobpe.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.codingburn.jobpe.HomeTabUi.FreshersJobFragment;
import com.codingburn.jobpe.HomeTabUi.GovtJobFragment;
import com.codingburn.jobpe.HomeTabUi.InternShipFragment;

public class HomeViewPager extends FragmentPagerAdapter {
    public HomeViewPager(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       if(position == 0){
           return new FreshersJobFragment();
       }else if(position == 1){
           return new InternShipFragment();
       }else{
           return new GovtJobFragment();
       }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Freshers Job";
        }else if(position == 1){
            return  "Internship";
        }else{
            return "Govt Job";
        }
    }
}
