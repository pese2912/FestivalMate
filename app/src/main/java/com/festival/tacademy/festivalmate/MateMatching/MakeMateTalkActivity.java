package com.festival.tacademy.festivalmate.MateMatching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.festival.tacademy.festivalmate.HomeActivity;
import com.festival.tacademy.festivalmate.R;

public class MakeMateTalkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_mate_talk);
        Button btn = (Button)findViewById(R.id.btn_maketalk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MakeMateTalkActivity.this, HomeActivity.class));
            }
        });
    }
}
