package com.festival.tacademy.festivalmate.FestivalInfo;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class FestivalViewHolder extends RecyclerView.ViewHolder {

    ImageView photoView;
    TextView text_title, text_date, text_location;

    Festival festival;

    public interface OnItemClickListener {
        public void onItemClick(View view, Festival festival);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public FestivalViewHolder(View itemView) {
        super(itemView);
        text_title = (TextView)itemView.findViewById(R.id.textView2);
        photoView = (ImageView)itemView.findViewById(R.id.photo_view);
        text_date = (TextView)itemView.findViewById(R.id.text_date);
        text_location = (TextView)itemView.findViewById(R.id.text_location);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mListener!=null ) {
                    mListener.onItemClick(v, festival);
                }
            }
        });
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
        text_title.setText(festival.getName());
        photoView.setImageResource(festival.getPhoto());
        text_date.setText(festival.getDate());
        text_location.setText(festival.getLocation());
    }
}