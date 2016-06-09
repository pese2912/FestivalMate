package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.Data.PreferenceArtist;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class MateTalkRoomViewHolder extends RecyclerView.ViewHolder {

    TextView festivalView;
    TextView nameView;
    ImageView photoView;
    TextView contentView;
    TextView numberView;
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

    public MateTalkRoomViewHolder(View itemView) {
        super(itemView);
        festivalView = (TextView)itemView.findViewById(R.id.room_festival);
        nameView = (TextView)itemView.findViewById(R.id.room_name);
        photoView = (ImageView)itemView.findViewById(R.id.room_image);
        contentView = (TextView)itemView.findViewById(R.id.room_content);
        numberView = (TextView)itemView.findViewById(R.id.room_num);
        dateView = (TextView)itemView.findViewById(R.id.room_date);
       // unReadView = (TextView)itemView.findViewById(R.id.room_unread);
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
        festivalView.setText(room.getFestival_name());
        nameView.setText("- "+room.getChatroom_name());
        contentView.setText(room.getChatroom_new_chat_content());
      //  Toast.makeText(MyApplication.getContext(),room.getChatroom_img(),Toast.LENGTH_SHORT).show();
        Glide.with(photoView.getContext()).load(NetworkManager.MY_SERVER+"/"+room.getChatroom_img()).into(photoView);
       // Log.i("photo",NetworkManager.MY_SERVER+"/"+room.getChatroom_img());
        numberView.setText(room.getChatroom_size()+"명");
        if(date != null) {
            dateView.setText(date.substring(5, 7) + "월 " + date.substring(8, 10) + "일");
        }




 //       dateView.setText(date);
//        unReadView.setText(room.getChatroom_new_count()+"");
    }
}
