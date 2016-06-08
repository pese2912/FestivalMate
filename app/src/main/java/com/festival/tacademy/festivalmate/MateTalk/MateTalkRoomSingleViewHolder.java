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
        String date = room.getChatroom_new_chat_date();
        nameView.setText(room.getChatroom_name());
        contentView.setText(room.getChatroom_new_chat_content());
        Glide.with(photoView.getContext()).load(NetworkManager.MY_SERVER+"/"+room.getChatroom_img()).into(photoView);
        if(date != null) {
            dateView.setText(date.substring(5, 7) + "월 " + date.substring(8, 10) + "일");
        }
      //  unReadView.setText(room.getChatroom_new_count()+"");
    }
}
