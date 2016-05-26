package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.ChatroomApproveResult;
import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.Data.chatroom_waiting;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class ApproveWaiterViewHolder extends RecyclerView.ViewHolder {

    ImageView photoView;
    TextView nameView;
    Button btnReject;
    Button btnApproved;
    chatroom_waiting waiting;

    public interface OnItemClickListener {
        public void onItemClick(View view, chatroom_waiting waiting);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ApproveWaiterViewHolder(View itemView) {
        super(itemView);
        photoView = (ImageView)itemView.findViewById(R.id.image_photo);
        nameView  = (TextView)itemView.findViewById(R.id.text_name);
        btnReject = (Button)itemView.findViewById(R.id.btn_reject);
        btnApproved = (Button)itemView.findViewById(R.id.btn_approved);
    }

    public void setApproveWaiter(final chatroom_waiting waiter,final int chatNo){
        this.waiting =waiter;
        Glide.with(photoView.getContext()).load(waiter.getMem_img()).into(photoView);
        nameView.setText(waiter.getMem_name());
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MyApplication.getContext(), "거절 : "+waiting.getMem_name(), Toast.LENGTH_SHORT).show();
            }
        });
        btnApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkManager.getInstance().chatroom_approve(MyApplication.getContext(), waiter.getChatroom_waiting_no(), chatNo, new NetworkManager.OnResultListener<ChatroomApproveResult>() {
                    @Override
                    public void onSuccess(Request request, ChatroomApproveResult result) {
                        Toast.makeText(MyApplication.getContext(), "승인 : "+waiting.getMem_name(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        Toast.makeText(MyApplication.getContext(), "실패 : "+exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
