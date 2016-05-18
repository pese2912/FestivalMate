package com.festival.tacademy.festivalmate.MateMatching;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-17.
 */

public class MateMatchingLineUpArtistViewHolder extends RecyclerView.ViewHolder {

    TextView nameView;
    Artist artist;

    public MateMatchingLineUpArtistViewHolder(View itemView) {
        super(itemView);
        nameView = (TextView)itemView.findViewById(R.id.text_artist_name);
    }

    public void setFestibalLineUpArtist(Artist artist){

        this.artist = artist;
        nameView.setText(artist.getName());
//        Log.i("name" , artist.getName());
    }

}
