/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.adapter;

import android.view.View;

import com.magnaconnect.R;
import com.magnaconnect.view.fragment.TFragment;
import com.magnaconnect.view.model.DashResponse;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    List<List<DashResponse.Tertiary>> data;
    View rootView;
    FragmentManager fm;
    AppCompatActivity activity;
    PagerAdapter adapterViewPager;

    public PagerAdapter(AppCompatActivity activity, FragmentManager fm, int NumofTabs, List<List<DashResponse.Tertiary>> data) {
        super(fm);
        mNumOfTabs = NumofTabs;
        this.fm = fm;
        this.activity = activity;
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
       /* ProductFragment pf = ProductFragment.newInstance(data.get(position),position);
        return pf;*/
        ViewPager vpPager = (ViewPager) rootView.findViewById(R.id.vpPager);
//        adapterViewPager = new TFragment(activity.getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        return TFragment.newInstance(data.get(position), position);
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}