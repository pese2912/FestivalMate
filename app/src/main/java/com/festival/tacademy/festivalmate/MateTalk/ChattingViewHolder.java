package com.festival.tacademy.festivalmate.MateTalk;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Profile.ProfileDialogFragment;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Duedapi on 2016-04-30.
 */
public class ChattingViewHolder extends RecyclerView.ViewHolder  {

    private static final int TYPE_SEND = 0;
    private static final int TYPE_RECEIVE = 1;
    private static final int TYPE_DATE = 2;

    Receive receive;
    //These are the general elements in the RecyclerView
    public TextView date_message;

    public ImageView receive_icon;
    public TextView receive_name;
    public TextView receive_message;
    public TextView receive_date;


    public TextView send_message;
    public TextView send_date;

    public interface OnItemClickListener {
        public void onItemClick(View view, Receive receive);
    }

    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ChattingViewHolder(View itemView, int viewType) {
        super(itemView);
       switch (viewType){

           case TYPE_SEND:
               send_message = (TextView)itemView.findViewById(R.id.text_message);
               send_date = (TextView)itemView.findViewById(R.id.text_date);
           case TYPE_RECEIVE:

               receive_message = (TextView)itemView.findViewById(R.id.text_message);
               receive_name = (TextView)itemView.findViewById(R.id.text_name);
               receive_icon = (ImageView)itemView.findViewById(R.id.image_photo);


               receive_date = (TextView)itemView.findViewById(R.id.text_date);

           case TYPE_DATE:
               date_message = (TextView)itemView.findViewById(R.id.text_message);
       }
    }

    public void setDate(Date d) {
        date_message.setText(d.message);

    }

    public void setReceive(Receive r) {
        this.receive = r;

        receive_message.setText(r.chat_content);
        Glide.with(receive_icon.getContext()).load(NetworkManager.MY_SERVER+"/"+r.mem_img).into(receive_icon);
        receive_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, receive);
                }
            }
        });
        receive_name.setText(r.mem_name);
       // receive_icon.setImageDrawable(r.icon);
       // receive_date.setText(r.date);
    }

    public void setSend(Send s) {
        send_message.setText(s.message);
       // send_date.setText(s.date);
    }
}
