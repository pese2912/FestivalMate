package com.festival.tacademy.festivalmate.MateMatching;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.FestibalLineUp;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-17.
 */
public class MateMatchingLineUpViewHolder extends RecyclerView.ViewHolder {

    TextView dateView;
    RecyclerView listView;
    FestibalLineUp festibalLineUp;
    MateMatchingLineUpArtistAdapter mAdapter;
    LinearLayoutManager mManager;

    public MateMatchingLineUpViewHolder(View itemView) {

        super(itemView);
        dateView = (TextView)itemView.findViewById(R.id.text_date);
        listView = (RecyclerView)itemView.findViewById(R.id.rv_list);

    }

    public void setFestibalLineUp(FestibalLineUp lineUp){
        this.festibalLineUp = lineUp;
        dateView.setText(lineUp.getData());
        mAdapter = new MateMatchingLineUpArtistAdapter();
        listView.setAdapter(mAdapter);
       // mManager = new LinearLayoutManager(MyApplication.getContext());
        //mManager = new LinearLayoutManager(MyApplication.getContext(), LinearLayoutManager.HORIZONTAL,false);
        mManager = new GridLayoutManager(MyApplication.getContext(),3);
        listView.setLayoutManager(mManager);
        mAdapter.addAll(lineUp.getArtist());
    }
}
