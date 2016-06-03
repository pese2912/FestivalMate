package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-31.
 */
public class MateTalkRoomSingleViewHolder extends RecyclerView.ViewHolder {

    TextView nameView;
    ImageView photoView;
    TextView contentView;
    TextView dateView;
    TextView unReadView;

    MateTalkRoom talkRoom;
    public interface OnItemClickListener {
        public void onItemClick(View view, MateTalkRoom artist);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public MateTalkRoomSingleViewHolder(View itemView) {
        super(itemView);
        nameView = (TextView)itemView.findViewById(R.id.room_name);
        photoView = (ImageView)itemView.findViewById(R.id.room_image);
        contentView = (TextView)itemView.findViewById(R.id.room_content);
        dateView = (TextView)itemView.findViewById(R.id.room_date);
      //  unReadView = (TextView)itemView.findViewById(R.id.room_unread);
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if( mListener!=null ) {
                    mListener.onItemClick(v, talkRoom);
                }
            }
        });
    }

    public void setMateTalkRoom(MateTalkRoom room){
        this.talkRoom = room;
        nameView.setText(room.getChatroom_host());
        contentView.setText(room.getChatroom_new_chat_content());
        Glide.with(photoView.getContext()).load(NetworkManager.MY_SERVER+"/"+room.getChatroom_img()).into(photoView);
        dateView.setText(room.getChatroom_new_chat_date());
      //  unReadView.setText(room.getChatroom_new_count()+"");
    }
}
