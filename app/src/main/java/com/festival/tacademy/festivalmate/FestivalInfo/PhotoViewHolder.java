package com.festival.tacademy.festivalmate.FestivalInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by J.K.Lee on 2016-05-16.
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder {
    ImageView festival_photo;
    TextView titleView;
    TextView dateView;

    Festival festival;

    public PhotoViewHolder(View itemView) {
        super(itemView);

        festival_photo = (ImageView)itemView.findViewById(R.id.image_festival_photo);
        titleView = (TextView)itemView.findViewById(R.id.text_festival_title);
        dateView = (TextView)itemView.findViewById(R.id.text_festival_date);

    }

    public void setPhoto(Festival festival) {
        this.festival = festival;
        Glide.with(festival_photo.getContext()).load(festival.getFestival_img()).into(festival_photo);
        titleView.setText(festival.getFestival_name());
        dateView.setText(festival.getFestival_lineups().get(0).getDate()+" ~ "+ festival.getFestival_lineups().get(festival.getFestival_lineups().size()-1).getDate());
    }
}
