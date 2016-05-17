package com.festival.tacademy.festivalmate.MateMatching;

import android.support.v7.widget.GridLayoutManager;
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

    public MateMatchingLineUpViewHolder(View itemView) {

        super(itemView);
        dateView = (TextView)itemView.findViewById(R.id.text_date);
        listView = (RecyclerView)itemView.findViewById(R.id.rv_list);
    }

    public void setFestibalLineUp(FestibalLineUp lineUp){
        this.festibalLineUp = lineUp;
        dateView.setText(lineUp.data);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new GridLayoutManager(MyApplication.getContext(),3));

    }
}
