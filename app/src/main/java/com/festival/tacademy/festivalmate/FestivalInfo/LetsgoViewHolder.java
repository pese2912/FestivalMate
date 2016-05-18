package com.festival.tacademy.festivalmate.FestivalInfo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-16.
 */
public class LetsgoViewHolder extends RecyclerView.ViewHolder {

    RecyclerView rv_list;
    UserAdapter mAdapter;
    LinearLayoutManager mManager;

    public LetsgoViewHolder(View itemView) {
        super(itemView);
        rv_list = (RecyclerView)itemView;
    }

    public void setList(List<User> user) {

        mAdapter = new UserAdapter(MyApplication.getContext());
        rv_list.setAdapter(mAdapter);
        mManager = new LinearLayoutManager(MyApplication.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_list.setLayoutManager(mManager);

        mAdapter.addAll(user);
    }
}
