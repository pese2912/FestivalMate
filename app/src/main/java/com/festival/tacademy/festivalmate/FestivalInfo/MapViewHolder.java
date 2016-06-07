package com.festival.tacademy.festivalmate.FestivalInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.R;


/**
 * Created by J.K.Lee on 2016-05-18.
 */
public class MapViewHolder extends RecyclerView.ViewHolder {

    ImageView locationView;
    TextView textLocation;


    public MapViewHolder(View itemView) {
        super(itemView);
        locationView = (ImageView)itemView.findViewById(R.id.image_location);
        textLocation = (TextView)itemView.findViewById(R.id.text_location);
    }

    public void setLocation(Festival festival) {
      //  locationView.setImageResource(festival.getPhoto_location());
        Glide.with(locationView.getContext()).load(festival.getFestival_img()).into(locationView);
        textLocation.setText(festival.getFestival_location());
    }


}
