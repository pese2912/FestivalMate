package com.festival.tacademy.festivalmate.FestivalInfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class FestivalPagerAdapter extends FragmentPagerAdapter {
    List<Integer> items = new ArrayList<Integer>();
    public void add(int item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<Integer> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public FestivalPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FestivalPageFragment.newInstance(items.get(position));
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
