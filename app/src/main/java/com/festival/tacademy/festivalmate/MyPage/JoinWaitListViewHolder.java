package com.festival.tacademy.festivalmate.MyPage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.MateTalkWaitList;
import com.festival.tacademy.festivalmate.Data.PreferenceArtist;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class JoinWaitListViewHolder extends RecyclerView.ViewHolder {

    ImageView photoView;
    TextView nameView;
    TextView titleView;
    TextView peopleView;
    TextView waitPeopleView;
    TextView cancelView;


    MateTalkWaitList mateTalkWaitList;

    public interface OnItemClickListener {
        public void onItemClick(View view, MateTalkWaitList mateTalkWaitList);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public JoinWaitListViewHolder(View itemView) {
        super(itemView);
        photoView  = (ImageView)itemView.findViewById(R.id.image_photo);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        titleView = (TextView)itemView.findViewById(R.id.text_title);
        peopleView = (TextView)itemView.findViewById(R.id.text_people);
        cancelView = (TextView)itemView.findViewById(R.id.btn_cancel);

        waitPeopleView = (TextView)itemView.findViewById(R.id.text_wait_people);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mListener!=null ) {
                    mListener.onItemClick(v, mateTalkWaitList);
                }
            }
        });
    }

    public void setMateTalkWaitList(MateTalkWaitList mateTalkWaitList){
        this.mateTalkWaitList = mateTalkWaitList;
        Glide.with(photoView.getContext()).load(NetworkManager.MY_SERVER+"/"+mateTalkWaitList.getChatroom_img()).into(photoView);
        nameView.setText(mateTalkWaitList.getFestival_name());
        titleView.setText(mateTalkWaitList.getChatroom_name());
        peopleView.setText(mateTalkWaitList.getChatroom_size()+"명");
        waitPeopleView.setText(mateTalkWaitList.getChatroom_wait_num()+"명");

    }
}
