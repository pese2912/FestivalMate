package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.chatroom_waiting;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class ApproveWaiterViewHolder extends RecyclerView.ViewHolder {

    ImageView photoView;
    TextView nameView;
    chatroom_waiting waiting;

    public ApproveWaiterViewHolder(View itemView) {
        super(itemView);
        photoView = (ImageView)itemView.findViewById(R.id.image_photo);
        nameView  = (TextView)itemView.findViewById(R.id.text_name);
    }

    public void setApproveWaiter(chatroom_waiting waiter){
        this.waiting =waiter;
        photoView.setImageDrawable(waiter.getMem_img());
        nameView.setText(waiter.getMem_name());

    }
}
