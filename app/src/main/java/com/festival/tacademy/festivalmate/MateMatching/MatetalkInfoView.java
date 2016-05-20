package com.festival.tacademy.festivalmate.MateMatching;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.R;


/**
 * Created by J.K.Lee on 2016-05-18.
 */
public class MatetalkInfoView extends RelativeLayout {

    public MatetalkInfoView(Context context) {
        super(context);
        init();
    }

    public MatetalkInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    ImageView photoView;
    TextView textView;
    MateTalkRoom chatinfo;

    public MateTalkRoom getChatinfo() {
        return chatinfo;
    }

    public void setChatinfo(MateTalkRoom chatinfo) {
        this.chatinfo = chatinfo;
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_matetalk_info, this);

        photoView = (ImageView)findViewById(R.id.image_view);
        textView = (TextView)findViewById(R.id.title_view);
    }
}
