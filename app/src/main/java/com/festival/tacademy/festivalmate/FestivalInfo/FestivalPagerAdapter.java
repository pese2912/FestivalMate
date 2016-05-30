package com.festival.tacademy.festivalmate.FestivalInfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.festival.tacademy.festivalmate.Data.Festival;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class FestivalPagerAdapter extends FragmentPagerAdapter {
    List<Festival> items = new ArrayList<Festival>();

    public void add(Festival item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<Festival> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }
    public void clear()
    {
        this.items.clear();
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
