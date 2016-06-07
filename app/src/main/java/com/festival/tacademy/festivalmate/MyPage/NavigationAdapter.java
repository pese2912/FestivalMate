package com.festival.tacademy.festivalmate.MyPage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import com.festival.tacademy.festivalmate.Data.NavigationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.K.Lee on 2016-06-07.
 */
public class NavigationAdapter extends BaseAdapter implements NavigationItemView.OnItemClickListener {

    List<NavigationItem> items = new ArrayList<>();

    public void add(NavigationItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<NavigationItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NavigationItemView view;

        if( convertView == null ) {
            view = new NavigationItemView(parent.getContext());
            view.setOnItemClickListener(this);
        } else {
            view = (NavigationItemView)convertView;
        }

        view.setMenu(items.get(position));

        return view;
    }

    public interface OnMenuClickListener {
        public void onMenuClick(NavigationAdapter adapter, NavigationItem item);
    }

    OnMenuClickListener mListener;

    public void setOnMenuClickListener(OnMenuClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onItemClick(NavigationItem menu) {
        mListener.onMenuClick(this, menu);
    }

}
