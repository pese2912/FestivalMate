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

import com.festival.tacademy.festivalmate.FestivalInfo.FestivalInfoFragment;
import com.festival.tacademy.festivalmate.MateMatching.MateMatchingFragment;
import com.festival.tacademy.festivalmate.MateTalk.MateTalkFragment;
import com.festival.tacademy.festivalmate.MyPage.JoinWaitListActivity;
import com.festival.tacademy.festivalmate.MyPage.LetsGoListActivity;
import com.festival.tacademy.festivalmate.MyPage.SettingsActivity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTabHost tabHost;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabHost = (FragmentTabHost)findViewById(R.id.tabHost);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("공연정보"), FestivalInfoFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("메이트톡"), MateTalkFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("메이트매칭"), MateMatchingFragment.class,null);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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
