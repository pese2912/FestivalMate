package com.festival.tacademy.festivalmate.FestivalInfo;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.R;

import org.w3c.dom.Text;

/**
 * Created by J.K.Lee on 2016-05-16.
 */
public class UserViewHolder extends RecyclerView.ViewHolder {

    ImageView photoView;
    TextView idView;
    User user;

    public interface OnItemClickListener {
        public void onItemClick(View view,User user);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public UserViewHolder(View itemView) {
        super(itemView);
        photoView = (ImageView)itemView.findViewById(R.id.image_photo);
        idView = (TextView)itemView.findViewById(R.id.text_id);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v,user);
                }
            }
        });
    }

    public void setUser(User user) {
        this.user = user;
        Glide.with(photoView.getContext()).load(NetworkManager.MY_SERVER+"/"+user.getPhoto()).into(photoView);
        idView.setText(user.getName());
    }
}