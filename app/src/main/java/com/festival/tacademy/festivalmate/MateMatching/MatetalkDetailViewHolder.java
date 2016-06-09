package com.festival.tacademy.festivalmate.MateMatching;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.FestivalInfo.UserViewHolder;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.R;


/**
 * Created by J.K.Lee on 2016-05-18.
 */
public class MatetalkDetailViewHolder extends RecyclerView.ViewHolder {

    MatetalkDetailView detailView;
    MatetalkInfoView infoView;
    MateTalkRoom mateTalkRoom;
    String[] city = {"무관","서울","부산","대구","인천","광주"
            ,"대전","울산","세종","경기","강원","충북"
            ,"충남","전북","전남","경북","경남","제주"};

    String[] old = {"무관","10대","20대","30대","40대","40대 이상"};


    public MatetalkDetailViewHolder(View itemView) {
        super(itemView);
        detailView = (MatetalkDetailView)itemView.findViewById(R.id.detail_view);
        infoView = (MatetalkInfoView)itemView.findViewById(R.id.info_view);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, mateTalkRoom);
                }
            }
        });
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, MateTalkRoom mateTalkRoom);
    }

    OnItemClickListener mListener;


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setMateTalkRoom(MateTalkRoom chatinfo, ChatUserViewHolder.OnItemClickListener listener) {

        mateTalkRoom = chatinfo;
        detailView.text_title.setText(chatinfo.getChatroom_festival_name());
        detailView.text_artist_num.setText(chatinfo.getMatched_artist_number() + "명");

        if(chatinfo.getMatched_artist().size()!=0) {
            detailView.text_artists.setText(chatinfo.getMatched_artist().get(0).getMatched_artist_name());
            for (int j = 1; j < chatinfo.getMatched_artist().size(); j++) {
                detailView.text_artists.append(", " + chatinfo.getMatched_artist().get(j).getMatched_artist_name());
            }
        }

        detailView.text_region.setText(city[chatinfo.getChatroom_location()]); //지역
        detailView.text_age.setText(old[chatinfo.getChatroom_age()]);//나이

        detailView.text_mem_user.setText(chatinfo.getChatroom_size()+"명");

        detailView.listView.setAdapter(detailView.mAdapter);
        detailView.listView.setLayoutManager(detailView.mLayoutManager);

        if(chatinfo.getMem_chatroom_state()==0){
            detailView.btn.setText("메이트톡 시작");
        }


        else if(chatinfo.getMem_chatroom_state()==1){
            detailView.btn.setText("신청 완료! 방장 승인을 기다려 주세요.");
        }

        else if(chatinfo.getMem_chatroom_state()==2){
            detailView.btn.setText("참여중");
        }

        if(chatinfo.getChatroom_mems()!=null) {
            detailView.mAdapter.clear();
            detailView.mAdapter.addAll(chatinfo.getChatroom_mems());
            detailView.mAdapter.setOnItemClickListener(listener);
        }

        Glide.with(infoView.photoView.getContext()).load(NetworkManager.MY_SERVER+"/"+chatinfo.getChatroom_img()).into(infoView.photoView);
        infoView.titleView.setText(chatinfo.getChatroom_name());
        infoView.joinView.setText(chatinfo.getChatroom_size()+"명 참여중");
        infoView.matchrateView.setText("  "+chatinfo.getMatched_artist_number()+"명 일치");
        infoView.hostNameView.setText(chatinfo.getChatroom_host_name()+" 님");
    }

}
