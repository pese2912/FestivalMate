package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.Data.PreferenceArtist;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class MateTalkRoomViewHolder extends RecyclerView.ViewHolder {

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
        nameView = (TextView)itemView.findViewById(R.id.room_name);
        photoView = (ImageView)itemView.findViewById(R.id.room_image);
        contentView = (TextView)itemView.findViewById(R.id.room_content);
        numberView = (TextView)itemView.findViewById(R.id.room_num);
        dateView = (TextView)itemView.findViewById(R.id.room_date);
        unReadView = (TextView)itemView.findViewById(R.id.room_unread);
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
        nameView.setText(room.getFestival_name());
        contentView.setText(room.getContent());
        photoView.setImageResource(room.getPhoto());
        numberView.setText(room.getNumber());
        dateView.setText(room.getDate());
        unReadView.setText(room.getUnRead());
    }
}
