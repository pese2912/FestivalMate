package com.festival.tacademy.festivalmate.Profile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.R;


/**
 * Created by J.K.Lee on 2016-05-19.
 */
public class ProfilePreferArtistViewHolder extends RecyclerView.ViewHolder {

    ImageView photo_view;
    TextView name_view;

    public ProfilePreferArtistViewHolder(View itemView) {
        super(itemView);
        photo_view = (ImageView)itemView.findViewById(R.id.image_photo);
        name_view = (TextView)itemView.findViewById(R.id.text_id);
    }

    public void setArtist(Artist artist) {
        Glide.with(photo_view.getContext()).load(artist.getPhoto()).into(photo_view);
       // photo_view.setImageResource(artist.getPhoto());
        name_view.setText(artist.getName());
    }
}
