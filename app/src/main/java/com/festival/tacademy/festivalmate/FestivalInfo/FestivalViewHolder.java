package com.festival.tacademy.festivalmate.FestivalInfo;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.CheckGoingResult;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class FestivalViewHolder extends RecyclerView.ViewHolder {

    ImageView photoView;
    TextView text_title, text_date, text_location;
    CheckBox checkBox;


    Festival festival;

    public interface OnItemClickListener {
        public void onItemClick(View view, Festival festival);
        public void onItemCheck(View view, Festival festival);
    }


    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public FestivalViewHolder(View itemView) {
        super(itemView);
        text_title = (TextView)itemView.findViewById(R.id.text_title);
        photoView = (ImageView)itemView.findViewById(R.id.photo_view);
        text_date = (TextView)itemView.findViewById(R.id.text_date);
        checkBox = (CheckBox)itemView.findViewById(R.id.check_going);
        text_location = (TextView)itemView.findViewById(R.id.text_location);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mListener!=null ) {
                    mListener.onItemClick(v, festival);
                }
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mListener!=null ) {
                    mListener.onItemCheck(v,festival);
                }
            }
        });
    }

    public void setFestival(final Festival festival) {

        int size = festival.getFestival_lineups().size();
        this.festival = festival;
        text_title.setText(festival.getFestival_name());
        Glide.with(photoView.getContext()).load(festival.getFestival_img()).into(photoView);

        if(festival.festival_lineups!=null)
            text_date.setText(festival.getFestival_lineups().get(0).getDate() + "-" + festival.getFestival_lineups().get(size-1).getDate());
        text_location.setText(festival.getFestival_location());

        if(PropertyManager.getInstance().getNo() == 0){
            checkBox.setVisibility(View.GONE);
        }


        if(festival.getMem_going_check() == 1) {
            checkBox.setChecked(true);
         //   Toast.makeText(MyApplication.getContext(),festival.getFestival_name(),Toast.LENGTH_SHORT).show();
        }
        else
            checkBox.setChecked(false);

    }
}