package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by J.K.Lee on 2016-05-31.
 */
public class PreferArtistViewHolder extends RecyclerView.ViewHolder {

    ImageView image_artist_photo;
    TextView text_artist_name;

    public PreferArtistViewHolder(View itemView) {
        super(itemView);

        image_artist_photo = (ImageView)itemView.findViewById(R.id.image_photo);
        text_artist_name = (TextView)itemView.findViewById(R.id.text_id);
    }

    public void setData(Artist item) {
        Glide.with(image_artist_photo.getContext()).load(item.getPhoto()).into(image_artist_photo);
        //image_artist_photo.setImageResource(R.mipmap.ic_launcher);
        text_artist_name.setText(item.getName());
    }
}
