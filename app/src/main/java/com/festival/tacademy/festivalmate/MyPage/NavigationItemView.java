package com.festival.tacademy.festivalmate.MyPage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.NavigationItem;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by J.K.Lee on 2016-06-07.
 */
public class NavigationItemView extends RelativeLayout {

    ImageView iconView;
    TextView titleView;
    NavigationItem menu;

    public interface OnItemClickListener {
        public void onItemClick(NavigationItem menu);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setMenu(NavigationItem menu) {
        this.menu = menu;
        iconView.setImageResource(menu.getIcon_res());
        titleView.setText(menu.getMenu());
    }

    public NavigationItemView(Context context) {
        this(context, null);
    }

    public NavigationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_navigation_item, this);

        iconView = (ImageView)findViewById(R.id.img_icon);
        titleView = (TextView)findViewById(R.id.text_menu);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(menu);
            }
        });
    }
}
