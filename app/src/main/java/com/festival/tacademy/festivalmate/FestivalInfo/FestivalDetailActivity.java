package com.festival.tacademy.festivalmate.FestivalInfo;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.FestivalDetailResult;
import com.festival.tacademy.festivalmate.Data.ShowMemProfileResult;
import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MateMatching.MateMatchingStartActivity;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.Profile.ProfileDialogFragment;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;

import okhttp3.Request;

public class FestivalDetailActivity extends AppCompatActivity {

    TextView toolbarTitle;
    Toolbar toolbar;
    RecyclerView listView;
    FestivalDetailAdapter mAdapter;
    LinearLayoutManager mManager;
    Festival festival;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival_detail);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        //  fab = (FloatingActionButton)findViewById(R.id.btn_matching_start);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_navigation_back);

        listView = (RecyclerView)findViewById(R.id.rv_list);
        mAdapter = new FestivalDetailAdapter();
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mAdapter.setOnItemClickListener(new UserViewHolder.OnItemClickListener() { //?꾨줈??議고쉶
            @Override
            public void onItemClick(View view, User user) {
                NetworkManager.getInstance().show_mem_profile(FestivalDetailActivity.this, user.getMem_no(), new NetworkManager.OnResultListener<ShowMemProfileResult>() {
                    @Override
                    public void onSuccess(Request request, ShowMemProfileResult result) {
                        //Toast.makeText(FestivalDetailActivity.this, "?깃났",Toast.LENGTH_SHORT).show();
                        ProfileDialogFragment f = new ProfileDialogFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", result.result);
                        f.setArguments(bundle);
                        f.show(getSupportFragmentManager(), "aaaa");
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        //      Toast.makeText(FestivalDetailActivity.this, "?ㅽ뙣"+exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mAdapter.setOnItemClickListener2(new TicketLinkHolder.OnItemClickListner() { // ?곗폆留곹겕
            @Override
            public void onItemClick(String str) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(str);
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onItemClick2(String str) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(str);
                intent.setData(uri);
                startActivity(intent);
            }


            @Override
            public void onItemClick3(String str) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(str);
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onItemClick4(String str) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(str);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        mAdapter.setOnItemClickListener(new MapViewHolder.OnItemClickListner() {
            @Override
            public void onItemClick() {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(festival.getFestival_location_url());
                intent.setData(uri);
                startActivity(intent);
            }
        });


        listView.setAdapter(mAdapter);
        listView.setLayoutManager(mManager);

        Intent intent = getIntent();
        festival = (Festival)intent.getExtras().getSerializable("festival");
        toolbarTitle.setText(festival.getFestival_name());

        setData();

        Button btn = (Button)findViewById(R.id.btn_matching);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FestivalDetailActivity.this, MateMatchingStartActivity.class);
                intent.putExtra("festival", festival);
                startActivity(intent);
            }
        });
        if(PropertyManager.getInstance().getNo() == 0) {
            btn.setVisibility(View.GONE);
        }
    }

    private void setData(){
        NetworkManager.getInstance().show_festival_detail(this, festival.getFestival_no(), 1,
                new NetworkManager.OnResultListener<FestivalDetailResult>() {
                    @Override
                    public void onSuccess(Request request, FestivalDetailResult result) {
                        if(result.success==1) {
                            mAdapter.setFestval(result.result);
                            festival = result.result;
                        }
                    }
                    @Override
                    public void onFail(Request request, IOException exception) {
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
