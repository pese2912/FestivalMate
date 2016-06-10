package com.festival.tacademy.festivalmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.festival.tacademy.festivalmate.Data.NavigationItem;
import com.festival.tacademy.festivalmate.Data.ShowMiniProfileResult;
import com.festival.tacademy.festivalmate.FestivalInfo.FestivalInfoFragment;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MateTalk.MateTalkFragment;
import com.festival.tacademy.festivalmate.MyPage.JoinWaitListActivity;
import com.festival.tacademy.festivalmate.MyPage.LetsGoListActivity;
import com.festival.tacademy.festivalmate.MyPage.LogoutView;
import com.festival.tacademy.festivalmate.MyPage.NavigationAdapter;
import com.festival.tacademy.festivalmate.MyPage.ProfileUpdateActivity;
import com.festival.tacademy.festivalmate.MyPage.SettingsActivity;
import com.festival.tacademy.festivalmate.Preference.PreferenceActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;

import okhttp3.Request;

public class HomeActivity extends AppCompatActivity {

    FragmentTabHost tabHost;
    Toolbar toolbar;
    TextView closeTab;
    TextView profileUpdate;
    TextView nameView;
    RoundedImageView profileView;
    NavigationView navigationView;
    ListView listView;
    NavigationAdapter mAdapter;
    LogoutView logoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabHost = (FragmentTabHost)findViewById(R.id.tabHost);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        logoutView = new LogoutView(this);
        logoutView = (LogoutView)findViewById(R.id.view_logout);
        listView = (ListView)findViewById(R.id.list_view);
        mAdapter = new NavigationAdapter();
        mAdapter.setOnMenuClickListener(new NavigationAdapter.OnMenuClickListener() {
            @Override
            public void onMenuClick(NavigationAdapter adapter, NavigationItem item) {
                if(item.getId() == 0) {
                    startActivity(new Intent(HomeActivity.this, LetsGoListActivity.class));
                }
                else if (item.getId() == 1) {
                    startActivity(new Intent(HomeActivity.this, JoinWaitListActivity.class));
                }
                else if (item.getId() == 2) {
                    startActivity(new Intent(HomeActivity.this, PreferenceActivity.class));
                }
                else if(item.getId() == 3){
                    startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                }
            }
        });
        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyManager.getInstance().setLogin(false);
                PropertyManager.getInstance().setUser(null);
                PropertyManager.getInstance().setEmail("");
                PropertyManager.getInstance().setFacebookId("");
                PropertyManager.getInstance().setNo(0);
                PropertyManager.getInstance().setPassword("");
                AccessToken token = AccessToken.getCurrentAccessToken();
                if (token != null) {
                    LoginManager loginManager = LoginManager.getInstance();
                    loginManager.logOut();
                }
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        listView.setAdapter(mAdapter);

        setSupportActionBar(toolbar);

        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.festival_info)), FestivalInfoFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.mate_talk)), MateTalkFragment.class, null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_navigation_menu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        nameView = (TextView)headerView.findViewById(R.id.profile_id);
        profileView = (RoundedImageView)headerView.findViewById(R.id.profile_image);

        closeTab = (TextView)headerView.findViewById(R.id.btn_close);
        closeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });

        profileUpdate= (TextView)headerView.findViewById(R.id.profile_update);
        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileUpdateActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setMyInfo();
    }

    private void setMyInfo() {

        int memNo = PropertyManager.getInstance().getNo();

        if( memNo == 0 ) {
            profileUpdate.setVisibility(View.GONE);
            logoutView.setVisibility(View.GONE);
            nameView.setText(getResources().getString(R.string.join_festival_member));
            nameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                    finish();
                }
            });
            profileView.setImageResource(R.drawable.img_non_mem);
        }

        else {
            mAdapter.clear();
            mAdapter.add(new NavigationItem(0, R.drawable.menu_going, getResources().getString(R.string.lets_go_list)));
            mAdapter.add(new NavigationItem(1, R.drawable.menu_wait_list, getResources().getString(R.string.join_wait_list)));
            mAdapter.add(new NavigationItem(2, R.drawable.menu_update_artist, getResources().getString(R.string.update_prefer_artist)));
            mAdapter.add(new NavigationItem(3, R.drawable.menu_setting, getResources().getString(R.string.settings)));
            NetworkManager.getInstance().show_mini_profile(HomeActivity.this, memNo, new NetworkManager.OnResultListener<ShowMiniProfileResult>() {
                @Override
                public void onSuccess(Request request, ShowMiniProfileResult result) {
                    nameView.setText(result.result.getName());
                 //   Toast.makeText(HomeActivity.this,result.result.getName(),Toast.LENGTH_SHORT).show();
                    Glide.with(HomeActivity.this).load(NetworkManager.MY_SERVER+"/"+result.result.getPhoto()).asBitmap().into(profileView);
                }

                @Override
                public void onFail(Request request, IOException exception) {

                }
            });
        }
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
}
