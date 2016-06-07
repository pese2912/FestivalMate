package com.festival.tacademy.festivalmate.MyPage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.R;

/**
 * Created by J.K.Lee on 2016-06-07.
 */
public class LogoutView extends RelativeLayout {

    TextView textView;
    ImageView iconView;

    public LogoutView(Context context) {
        super(context, null);
    }

    public LogoutView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_logout, this);
        textView = (TextView)findViewById(R.id.text_logout);
        iconView = (ImageView)findViewById(R.id.img_logout);

        textView.setText("로그아웃");
        iconView.setImageResource(R.drawable.img_logout);
    }
}
