package com.festival.tacademy.festivalmate.Preference;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.PreferenceArtist;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-13.
 */
public class PreferenceViewHolder  extends RecyclerView.ViewHolder {

    TextView nameView;
    ImageView imageView;
    CheckBox checkBox;

    PreferenceArtist artist;


    public PreferenceViewHolder(View itemView) {
        super(itemView);

        nameView = (TextView)itemView.findViewById(R.id.name_artist);
        imageView = (ImageView)itemView.findViewById(R.id.image_artist);
        checkBox = (CheckBox)itemView.findViewById(R.id.checkBox_artist);
    }

    public void setPreferenceArtist(PreferenceArtist artist){
        this.artist = artist;
        nameView.setText(artist.getName());
        imageView.setImageDrawable(artist.getImage());


    }
}
