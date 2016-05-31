package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-31.
 */
public class ChatPreferArtistViewHolder extends RecyclerView.ViewHolder {

    TextView text_user_num;
    RecyclerView rv_list;
    PreferArtistAdapter mAdapter;

    public ChatPreferArtistViewHolder(View itemView) {
        super(itemView);

        text_user_num = (TextView)itemView.findViewById(R.id.text_user_num);
        rv_list = (RecyclerView)itemView.findViewById(R.id.rv_list3);
    }

    public void setData(List<Artist> items) {
        text_user_num.setText(items.size() + "명");

        mAdapter = new PreferArtistAdapter();
        rv_list.setAdapter(mAdapter);
        rv_list.setLayoutManager(new LinearLayoutManager(MyApplication.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter.addAll(items);
    }
}
