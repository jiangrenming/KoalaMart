package com.koalafield.cmart.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 *
 * @author jiangrenming
 * @date 2018/5/11
 */

public class OrderViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mList;

    public OrderViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public OrderViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
