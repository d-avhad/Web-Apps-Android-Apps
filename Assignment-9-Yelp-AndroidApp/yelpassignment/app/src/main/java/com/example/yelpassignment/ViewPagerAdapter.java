package com.example.yelpassignment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

       // Bundle data = getIntent().getExtras();
        switch (position) {
            case 0:
                return  new page1();
            case 1:
               // return  new page2();
                return  new MapsFragment();
            default:
                return  new page3();
        }
    }
    @Override
    public int getItemCount() {return 3; }
}