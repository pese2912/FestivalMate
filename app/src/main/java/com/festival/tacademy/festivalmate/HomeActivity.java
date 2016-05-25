package com.festival.tacademy.festivalmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.ShowMiniProfileResult;
import com.festival.tacademy.festivalmate.FestivalInfo.FestivalInfoFragment;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MateTalk.MateTalkFragment;
import com.festival.tacademy.festivalmate.MyPage.JoinWaitListActivity;
import com.festival.tacademy.festivalmate.MyPage.LetsGoListActivity;
import com.festival.tacademy.festivalmate.MyPage.ProfileUpdateActivity;
import com.festival.tacademy.festivalmate.MyPage.SettingsActivity;

import java.io.IOException;

import okhttp3.Request;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTabHost tabHost;
    Toolbar toolbar;
    TextView closeTab;
    TextView profileUpdate;
    TextView nameView;
    ImageView profileView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabHost = (FragmentTabHost)findViewById(R.id.tabHost);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.festival_info)), FestivalInfoFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.mate_talk)), MateTalkFragment.class, null);
       // tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(getResources().getString(R.string.mate_matching)), MateMatchingFragment.class,null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        nameView = (TextView)headerView.findViewById(R.id.profile_id);
        profileView = (ImageView)headerView.findViewById(R.id.profile_image);

        closeTab = (TextView)headerView.findViewById(R.id.btn_close);
        closeTab.setOnClickListener(new View.OnClickListener() { // 햄버거바 닫기
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });

        profileUpdate= (TextView)headerView.findViewById(R.id.profile_update);
        profileUpdate.setOnClickListener(new View.OnClickListener() { // 프로필 수정 클릭 시
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileUpdateActivity.class));
            }
        });

        setMyInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMyInfo();
    }

    private void setMyInfo(){

        int memNo = PropertyManager.getInstance().getNo();
        NetworkManager.getInstance().show_mini_profile(HomeActivity.this, memNo, new NetworkManager.OnResultListener<ShowMiniProfileResult>() {
            @Override
            public void onSuccess(Request request, ShowMiniProfileResult result) {
                nameView.setText(result.result.getName());
                Glide.with(HomeActivity.this).load(result.result.getPhoto()).into(profileView);
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      //  if (id == R.id.festibal_search) {

       //     return true;
      //  }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) { //햄버거바 아이템 클릭시
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.lets_go_list) { //갈꺼야 한 목록 클릭 시
            startActivity(new Intent(HomeActivity.this, LetsGoListActivity.class));

        } else if (id == R.id.wait_list) { // 참여대기 목록 리스트 클릭 시

            startActivity(new Intent(HomeActivity.this, JoinWaitListActivity.class));

        } else if (id == R.id.settings) { // 설정 클릭 시
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
