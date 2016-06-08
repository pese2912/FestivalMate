package com.festival.tacademy.festivalmate.MateMatching;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-17.
 */

public class MateMatchingLineUpArtistViewHolder extends RecyclerView.ViewHolder {

    CheckBox nameView;
    Artist artist;

    public MateMatchingLineUpArtistViewHolder(View itemView) {
        super(itemView);
        nameView = (CheckBox)itemView.findViewById(R.id.text_artist_name);
    }

    public void setFestibalLineUpArtist(Artist item){

        this.artist = item;
        nameView.setText(item.getName());
        nameView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    artist.setCheck(1);
                    nameView.setTextColor(nameView.getResources().getColor(R.color.color_toolbar));
                }
                else {
                    artist.setCheck(0);
                    //    Toast.makeText(MyApplication.getContext(), "checked : "+ artist.getName()+ " "+artist.isCheck()+" "`+artist.getArtist_date(),Toast.LENGTH_SHORT).show();
                    nameView.setTextColor(nameView.getResources().getColor(R.color.text0));
                }
            }
        });

//        Log.i("name" , artist.getName());
    }
}
