package com.festival.tacademy.festivalmate.MateMatching;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.FestibalLineUp;
import com.festival.tacademy.festivalmate.Data.Lineup;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-17.
 */
public class MateMatchingLineUpViewHolder extends RecyclerView.ViewHolder {

    TextView dateView;
    RecyclerView listView;
    Lineup festibalLineUp;
    MateMatchingLineUpArtistAdapter mAdapter;
    LinearLayoutManager mManager;

    public MateMatchingLineUpViewHolder(View itemView) {

        super(itemView);
        dateView = (TextView)itemView.findViewById(R.id.text_date);
        listView = (RecyclerView)itemView.findViewById(R.id.rv_list);
    }

    public void setFestibalLineUp(Lineup lineUp){
        this.festibalLineUp = lineUp;
        dateView.setText(lineUp.getDate());
        mAdapter = new MateMatchingLineUpArtistAdapter();
        listView.setAdapter(mAdapter);
       // mManager = new LinearLayoutManager(MyApplication.getContext());
        //mManager = new LinearLayoutManager(MyApplication.getContext(), LinearLayoutManager.HORIZONTAL,false);
        mManager = new GridLayoutManager(MyApplication.getContext(),3);
        listView.setLayoutManager(mManager);
        if(lineUp.getLineup()!=null) {

            for(int i=0; i < lineUp.getLineup().size(); i++){
                lineUp.getLineup().get(i).setArtist_date(lineUp.getDate());
            }

            mAdapter.addAll(lineUp.getLineup());
        }
    }
}
