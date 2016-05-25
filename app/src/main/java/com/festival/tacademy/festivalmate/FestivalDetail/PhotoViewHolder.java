package com.festival.tacademy.festivalmate.FestivalDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.R;


/**
 * Created by J.K.Lee on 2016-05-16.
 */
public class PhotoViewHolder extends RecyclerView.ViewHolder {
    ImageView festival_photo;
    Festival festival;

    public PhotoViewHolder(View itemView) {
        super(itemView);

        festival_photo = (ImageView)itemView.findViewById(R.id.image_festival_photo);
    }

    public void setPhoto(Festival festival) {
        this.festival = festival;
        Glide.with(festival_photo.getContext()).load(festival.getFestival_img()).into(festival_photo);

    }
}
