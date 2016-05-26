package com.festival.tacademy.festivalmate.MateTalk;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.chatroom_member;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class ChatJoinerViewHolder extends RecyclerView.ViewHolder {

    ImageView photoView;
    TextView nameView;
    chatroom_member member;
    Button btn;
    public ChatJoinerViewHolder(View itemView) {
        super(itemView);
        photoView = (ImageView)itemView.findViewById(R.id.image_photo);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
         btn = (Button)itemView.findViewById(R.id.btn_exile);

    }

    public void setChatJoin(final chatroom_member member){
        this.member = member;
        Glide.with(photoView.getContext()).load(member.getMem_img()).into(photoView);

        nameView.setText(member.getMem_name());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyApplication.getContext(), "강퇴 : "+member.getMem_name(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
