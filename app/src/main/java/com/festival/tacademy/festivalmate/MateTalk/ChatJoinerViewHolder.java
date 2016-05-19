package com.festival.tacademy.festivalmate.MateTalk;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.chatroom_member;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class ChatJoinerViewHolder extends RecyclerView.ViewHolder {

    ImageView photoView;
    TextView nameView;
    chatroom_member member;

    public ChatJoinerViewHolder(View itemView) {
        super(itemView);
        photoView = (ImageView)itemView.findViewById(R.id.image_photo);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
    }

    public void setChatJoin(chatroom_member member){
        this.member = member;
        photoView.setImageDrawable(member.getMem_img());
        nameView.setText(member.getMem_name());
    }
}
