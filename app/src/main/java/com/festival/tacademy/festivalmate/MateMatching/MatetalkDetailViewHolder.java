package com.festival.tacademy.festivalmate.MateMatching;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.FestivalInfo.UserViewHolder;
import com.festival.tacademy.festivalmate.R;


/**
 * Created by J.K.Lee on 2016-05-18.
 */
public class MatetalkDetailViewHolder extends RecyclerView.ViewHolder {

    MatetalkDetailView detailView;
    MatetalkInfoView infoView;

    public MatetalkDetailViewHolder(View itemView) {
        super(itemView);
        detailView = (MatetalkDetailView)itemView.findViewById(R.id.detail_view);
        infoView = (MatetalkInfoView)itemView.findViewById(R.id.info_view);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v);
                }
            }
        });
    }

    public interface OnItemClickListener {
        public void onItemClick(View view);
    }

    OnItemClickListener mListener;


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;

    }

    public void setMateTalkRoom(MateTalkRoom chatinfo, UserViewHolder.OnItemClickListener listener) {
        detailView.text_title.setText(chatinfo.getFestival_name());
        detailView.text_artist_num.setText(chatinfo.getMatching_artists().size() + "명");

        detailView.text_artists.setText(chatinfo.getMatching_artists().get(0).getName());
        for (int j = 1; j < chatinfo.getMatching_artists().size(); j++) {
            detailView.text_artists.append(", " + chatinfo.getMatching_artists().get(j).getName());
        }

        detailView.text_region.setText(chatinfo.getPrefer_location()+"");
        detailView.text_age.setText(chatinfo.getAge()+"");

        detailView.text_mem_user.setText(chatinfo.getMember().size()+"명");

        detailView.listView.setAdapter(detailView.mAdapter);
        detailView.listView.setLayoutManager(detailView.mLayoutManager);

        detailView.mAdapter.addAll(chatinfo.getMember());
        detailView.mAdapter.setOnItemClickListener(listener);

        infoView.photoView.setImageResource(chatinfo.getPhoto());
        infoView.textView.setText(chatinfo.getTitle());
    }
}
