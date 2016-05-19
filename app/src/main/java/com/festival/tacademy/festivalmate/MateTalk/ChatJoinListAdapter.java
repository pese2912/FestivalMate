package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.festival.tacademy.festivalmate.Data.MateTalkWaitJoinList;
import com.festival.tacademy.festivalmate.Data.MateTalkWaitList;
import com.festival.tacademy.festivalmate.FestivalInfo.LetsgoViewHolder;
import com.festival.tacademy.festivalmate.FestivalInfo.LineupViewHolder;
import com.festival.tacademy.festivalmate.FestivalInfo.PhotoViewHolder;
import com.festival.tacademy.festivalmate.FestivalInfo.TitleViewHolder;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class ChatJoinListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_HEADER_WAIT = 1;
    public static final int VIEW_TYPE_HEADER_JOIN  = 2;
    public static final int VIEW_TYPE_APPROVE_WAITER = 3;
    public static final int VIEW_TYPE_CHAT_JOINER = 4;
    MateTalkWaitJoinList list =  new MateTalkWaitJoinList();;

    public void setMateTalkWaitJoinList(MateTalkWaitJoinList list){
        this.list = list;
        Log.i("list" ,list.getName());
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if( position == 0 ) {
            return VIEW_TYPE_HEADER_WAIT;
        }
        position--;
        if ( list.getChatroom_waitings().size() > 0 ) {
            if( position < list.getChatroom_waitings().size() ) {
                return VIEW_TYPE_APPROVE_WAITER;
            }
            position -= list.getChatroom_waitings().size();
            if( position == 0 ) {
                return VIEW_TYPE_HEADER_JOIN;
            }
            position--;
        }
        position--;
        if(list.getChatroom_members().size() > 0) {
            if (position < list.getChatroom_members().size()) {
                return VIEW_TYPE_CHAT_JOINER;
            }
            position -= list.getChatroom_members().size();
        }

        throw new IllegalArgumentException("Invalid Position");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER_WAIT: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_header_wait, null);
                return new HeaderWaitViewHolder(view);
            }
            case VIEW_TYPE_HEADER_JOIN: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_header_join, null);
                return new HeaderJoinViewHolder(view);
            }
            case VIEW_TYPE_APPROVE_WAITER: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_waiter_list, null);
                return new ApproveWaiterViewHolder(view);
            }
            case VIEW_TYPE_CHAT_JOINER: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_joiner_list, null);
                return new ChatJoinerViewHolder(view);
            }

        }
        throw new IllegalArgumentException("Invalid position");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case VIEW_TYPE_HEADER_WAIT: {
                HeaderWaitViewHolder h = (HeaderWaitViewHolder)holder;
                return;
            }
            case VIEW_TYPE_HEADER_JOIN: {
                HeaderJoinViewHolder h = (HeaderJoinViewHolder)holder;
                return;
            }
            case VIEW_TYPE_APPROVE_WAITER: {


                ApproveWaiterViewHolder h = (ApproveWaiterViewHolder)holder;
                h.setApproveWaiter(list.getChatroom_waitings().get(position));
                return;
            }

            case VIEW_TYPE_CHAT_JOINER: {

                ChatJoinerViewHolder h = (ChatJoinerViewHolder)holder;
                h.setChatJoin(list.getChatroom_members().get(position));
                return;
            }
        }
        throw new IllegalArgumentException("Invalid position");
    }

    @Override
    public int getItemCount() {
        if(list != null)
            return 2+list.getChatroom_waitings().size()+list.getChatroom_members().size();
        return 0;
    }
}
