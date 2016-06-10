package com.festival.tacademy.festivalmate.FestivalInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;


/**
 * Created by J.K.Lee on 2016-05-18.
 */

public class MapViewHolder extends RecyclerView.ViewHolder {

    ImageView locationView;
    TextView textLocation;
    Festival festival;

    public interface OnItemClickListner {
        public void onItemClick();
    }

    OnItemClickListner mListener;
    public void setOnItemClickListener(OnItemClickListner listener) {
        mListener = listener;
    }

    public MapViewHolder(View itemView) {

        super(itemView);
        locationView = (ImageView)itemView.findViewById(R.id.image_location);
        textLocation = (TextView)itemView.findViewById(R.id.text_location);
        locationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick();
            }
        });

    }

    public void setLocation(Festival festival) {
        this.festival = festival;
        Glide.with(locationView.getContext()).load( NetworkManager.MY_SERVER+"/"+festival.getFestival_location_img()).into(locationView);
        textLocation.setText(festival.getFestival_location());
    }
}
