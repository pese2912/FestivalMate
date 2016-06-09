package com.festival.tacademy.festivalmate.MateTalk;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.ChatroomKickResult;
import com.festival.tacademy.festivalmate.Data.chatroom_member;
import com.festival.tacademy.festivalmate.Data.chatroom_waiting;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class ChatJoinerViewHolder extends RecyclerView.ViewHolder {

    ImageView photoView;
    TextView nameView;
    chatroom_member member;
    Button btn;


    public interface OnItemClickListener {
        public void onItemClick(View view, chatroom_member member);
        public void onItemClick2(View view, chatroom_member member);
    }

    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ChatJoinerViewHolder(View itemView) {
        super(itemView);
        photoView = (ImageView)itemView.findViewById(R.id.image_photo);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mListener!=null ) {
                    mListener.onItemClick2(v,member);
                }
            }
        });
        nameView = (TextView)itemView.findViewById(R.id.text_name);
         btn = (Button)itemView.findViewById(R.id.btn_exile);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mListener!=null ) {
                    mListener.onItemClick(v,member);
                }
            }
        });
    }

    public void setChatJoin(chatroom_member member, int chatNo, int host){
        this.member = member;

        Glide.with(photoView.getContext()).load(NetworkManager.MY_SERVER+"/"+member.getMem_img()).into(photoView);
//        Toast.makeText(MyApplication.getContext(), member.getMem_no()+" "+ member.getMem_name()+" "+member.getMem_img(), Toast.LENGTH_SHORT).show();
        nameView.setText(member.getMem_name());

        if(host==1){
            if(PropertyManager.getInstance().getNo() == member.getMem_no()){
                btn.setVisibility(View.GONE);
            }
            else{
                btn.setVisibility(View.VISIBLE);
            }
        }
        else{
            btn.setVisibility(View.GONE);
        }

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MyApplication.getContext(), "강퇴 : "+member.getChatroom_mems_no(), Toast.LENGTH_SHORT).show();
//                NetworkManager.getInstance().chatroom_kick(MyApplication.getContext(), member.getChatroom_mems_no(), chatNo, new NetworkManager.OnResultListener<ChatroomKickResult>() {
//                    @Override
//                    public void onSuccess(Request request, ChatroomKickResult result) {
//                        Toast.makeText(MyApplication.getContext(), "강퇴 : "+member.getChatroom_mems_no(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFail(Request request, IOException exception) {
//                        Toast.makeText(MyApplication.getContext(), "실패 : "+exception.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });
    }
}
