package com.festival.tacademy.festivalmate.MateMatching;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.FestivalInfo.UserViewHolder;
import com.festival.tacademy.festivalmate.R;


/**
 * Created by J.K.Lee on 2016-05-18.
 */
public class MatetalkDetailViewHolder extends RecyclerView.ViewHolder {

    MatetalkDetailView detailView;
    MatetalkInfoView infoView;
    MateTalkRoom mateTalkRoom;

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

        detailView.text_region.setText(chatinfo.getChatroom_location()+"");
        detailView.text_age.setText(chatinfo.getChatroom_age()+"");

        detailView.text_mem_user.setText(chatinfo.getChatroom_size()+"명");

        detailView.listView.setAdapter(detailView.mAdapter);
        detailView.listView.setLayoutManager(detailView.mLayoutManager);

        if(chatinfo.getChatroom_mems()!=null) {
            detailView.mAdapter.addAll(chatinfo.getChatroom_mems());
            detailView.mAdapter.setOnItemClickListener(listener);
        }

        Glide.with(infoView.photoView.getContext()).load(chatinfo.getChatroom_img()).into(infoView.photoView);
        infoView.titleView.setText(chatinfo.getChatroom_name());
        infoView.joinView.setText(chatinfo.getChatroom_size()+"명 참여중");
        infoView.matchrateView.setText(chatinfo.getMatched_artist_number()+"명 일치");
        infoView.hostNameView.setText(chatinfo.getChatroom_host_name()+" 님");

    }

}
