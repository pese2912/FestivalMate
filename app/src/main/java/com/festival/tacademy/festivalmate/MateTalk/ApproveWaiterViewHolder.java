package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.chatroom_waiting;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.R;

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
    public interface OnItemClickListener2 {
        public void onItemClick2(View view, chatroom_waiting waiting);
        public void onItemClick3(View view, chatroom_waiting waiting);
    }

    OnItemClickListener mListener;
    OnItemClickListener2 mListener2;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnItemClickListener2(OnItemClickListener2 listener) {
        mListener2 = listener;
    }

    public ApproveWaiterViewHolder(View itemView) {
        super(itemView);
        photoView = (ImageView)itemView.findViewById(R.id.image_photo);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mListener2!=null ) {
                    mListener2.onItemClick3(v,waiting);
                }
            }
        });


        nameView  = (TextView)itemView.findViewById(R.id.text_name);
        btnReject = (Button)itemView.findViewById(R.id.btn_exile);
        btnApproved = (Button)itemView.findViewById(R.id.btn_approved);
        btnReject.setOnClickListener(new View.OnClickListener() { //거절
            @Override
            public void onClick(View v) {
                if( mListener!=null ) {
                    mListener.onItemClick(v,waiting);
                }
            }
        });
      btnApproved.setOnClickListener(new View.OnClickListener() { // 승인
          @Override
          public void onClick(View v) {
              if( mListener2!=null ) {
                  mListener2.onItemClick2(v,waiting);

              }
          }
      });

    }

    public void setApproveWaiter( chatroom_waiting waiter, int chatNo, int host){
        this.waiting =waiter;
        Glide.with(photoView.getContext()).load(NetworkManager.MY_SERVER+"/"+waiter.getMem_img()).into(photoView);
        nameView.setText(waiter.getMem_name());

        if(host == 0){
            btnReject.setVisibility(View.GONE);
            btnApproved.setVisibility(View.GONE);
        }
//        btnReject.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MyApplication.getContext(), "거절 : "+waiting.getChatroom_waiting_no(), Toast.LENGTH_SHORT).show();
//
//              //  Toast.makeText(MyApplication.getContext(), "거절 : "+waiting.getMem_name(), Toast.LENGTH_SHORT).show();
//                NetworkManager.getInstance().chatroom_disapprove(MyApplication.getContext(), waiter.getChatroom_waiting_no(), chatNo, new NetworkManager.OnResultListener<ChatroomDisapproveResult>() {
//                    @Override
//                    public void onSuccess(Request request, ChatroomDisapproveResult result) {
//                        Toast.makeText(MyApplication.getContext(), "거절 : "+waiting.getChatroom_waiting_no(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFail(Request request, IOException exception) {
//                        Toast.makeText(MyApplication.getContext(), "실패 : "+exception.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

//        btnApproved.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MyApplication.getContext(), "승인 : "+waiting.getChatroom_waiting_no(), Toast.LENGTH_SHORT).show();
//                NetworkManager.getInstance().chatroom_approve(MyApplication.getContext(), waiter.getChatroom_waiting_no(), chatNo, new NetworkManager.OnResultListener<ChatroomApproveResult>() {
//                    @Override
//                    public void onSuccess(Request request, ChatroomApproveResult result) {
//                        Toast.makeText(MyApplication.getContext(), "승인 : "+waiting.getChatroom_waiting_no(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFail(Request request, IOException exception) {
//                        Toast.makeText(MyApplication.getContext(), "실패 : "+exception.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
    }
}
