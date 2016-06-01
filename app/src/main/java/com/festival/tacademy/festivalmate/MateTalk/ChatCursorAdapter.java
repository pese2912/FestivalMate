package com.festival.tacademy.festivalmate.MateTalk;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Manager.DataConstant;
import com.festival.tacademy.festivalmate.R;


/**
 * Created by dongja94 on 2016-05-17.
 */
public class ChatCursorAdapter extends CursorAdapter {
    public ChatCursorAdapter(Context context) {
        super(context, null, true);
    }

    private static final int VIEW_TYPE_COUNT = 2;
    private static final int TYPE_LEFT = 0;
    private static final int TYPE_RIGHT = 1;

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        Cursor c = (Cursor)getItem(position);
        int type = c.getInt(c.getColumnIndex(DataConstant.ChatTable.COLUMN_TYPE));
        if (type == DataConstant.ChatTable.TYPE_SEND) {
            return TYPE_RIGHT;
        } else {
            return TYPE_LEFT;
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int type = cursor.getInt(cursor.getColumnIndex(DataConstant.ChatTable.COLUMN_TYPE));
        if (type == DataConstant.ChatTable.TYPE_SEND) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_send, null);
            return view;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_receive, null);
            return view;
        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String message = cursor.getString(cursor.getColumnIndex(DataConstant.ChatTable.COLUMN_MESSAGE));
        ((TextView)view.findViewById(R.id.text_message)).setText(message);
    }
}
