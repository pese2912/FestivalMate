package com.festival.tacademy.festivalmate.MyPage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.festival.tacademy.festivalmate.R;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;
    RelativeLayout layout_quest;
    RelativeLayout layout_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout_quest = (RelativeLayout)findViewById(R.id.layout_quest);
        layout_info = (RelativeLayout)findViewById(R.id.layout_info);

        Button btn = (Button) findViewById(R.id.btn_quest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, QuestionActivity.class));
            }
        });

        layout_quest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, QuestionActivity.class));
            }
        });

        btn = (Button)findViewById(R.id.btn_info);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, TermsOfUsePrivacyPolicyActivity.class));
            }
        });


        layout_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, TermsOfUsePrivacyPolicyActivity.class));
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
