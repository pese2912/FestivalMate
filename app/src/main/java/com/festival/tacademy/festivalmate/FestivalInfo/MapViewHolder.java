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
        public void onItemClick(String str);
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
        //        Toast.makeText(MyApplication.getContext(),festival.getFestival_location_url()+"",Toast.LENGTH_SHORT).show();
//                if(festival.getFestival_location_url() != null)
//                     mListener.onItemClick(festival.getFestival_location_url());
            }
        });

    }

    public void setLocation(Festival festival) {

        this.festival = festival;
      //  locationView.setImageResource(festival.getPhoto_location());
       Glide.with(locationView.getContext()).load( NetworkManager.MY_SERVER+"/"+festival.getFestival_location_img()).into(locationView);
       // Toast.makeText(MyApplication.getContext(), NetworkManager.MY_SERVER+"/"+festival.getFestival_location_img(),Toast.LENGTH_SHORT).show();
        textLocation.setText(festival.getFestival_location());
    }
}
