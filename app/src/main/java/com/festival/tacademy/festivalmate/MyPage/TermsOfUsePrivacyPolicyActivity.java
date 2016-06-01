package com.festival.tacademy.festivalmate.MyPage;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.festival.tacademy.festivalmate.FestivalInfo.FestivalInfoFragment;
import com.festival.tacademy.festivalmate.MateTalk.MateTalkFragment;
import com.festival.tacademy.festivalmate.R;

public class TermsOfUsePrivacyPolicyActivity extends AppCompatActivity {

    Toolbar toolbar;
    FragmentTabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use_privacy_policy);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tabHost = (FragmentTabHost)findViewById(R.id.tabHost);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.terms_use)), TermsOfUseFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.privacy_policy)), PrivacyFragment.class, null);

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
