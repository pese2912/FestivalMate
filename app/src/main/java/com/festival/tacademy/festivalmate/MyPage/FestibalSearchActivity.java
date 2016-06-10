package com.festival.tacademy.festivalmate.MyPage;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.Data.CheckGoingResult;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.FestivalResultResult;
import com.festival.tacademy.festivalmate.Data.Lineup;
import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.FestivalInfo.FestivalAdapter;
import com.festival.tacademy.festivalmate.FestivalInfo.FestivalDetailActivity;
import com.festival.tacademy.festivalmate.FestivalInfo.FestivalViewHolder;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class FestibalSearchActivity extends AppCompatActivity {

    RecyclerView listView;
    Toolbar toolbar;
    FestivalAdapter mAdapter;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festibal_search);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_navigation_back);

        mAdapter = new FestivalAdapter();
        listView = (RecyclerView)findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        editText = (EditText)findViewById(R.id.edit_info);

        mAdapter.setOnItemClickListener(new FestivalViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Festival festival) {

                final  Festival festival1 = festival;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // Toast.makeText(getContext(),festival.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FestibalSearchActivity.this, FestivalDetailActivity.class);
                        intent.putExtra("festival", festival1);
                        startActivity(intent);
                    }
                }, 250);
            }

            @Override
            public void onItemCheck(final View view, final Festival festival) {
                int memNo = PropertyManager.getInstance().getNo();
                int fesNo = festival.getFestival_no();
                int goingCheck = festival.getMem_going_check();

                NetworkManager.getInstance().check_going(FestibalSearchActivity.this, memNo, fesNo, goingCheck, new NetworkManager.OnResultListener<CheckGoingResult>() {
                    @Override
                    public void onSuccess(Request request, CheckGoingResult result) {
                        CheckBox checkBox = (CheckBox) view;
                        festival.setMem_going_check(result.result.getMem_going_check());
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });
            }
        });

        Button btn= (Button)findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    private void initData() {
        mAdapter.clear();

        //PropertyManager.getInstance().setUser(new User(11));
        NetworkManager.getInstance().show_festival_result(this, editText.getText().toString(), PropertyManager.getInstance().getNo(),
                new NetworkManager.OnResultListener<FestivalResultResult>() {
                    @Override
                    public void onSuccess(Request request, FestivalResultResult result) {

                        if( result.success == 1 ) {
                          //  Toast.makeText(FestibalSearchActivity.this, "Search Success", Toast.LENGTH_SHORT).show();
                            mAdapter.addAll(result.result);
                        }
                    }
                    @Override
                    public void onFail(Request request, IOException exception) {
                     //   Toast.makeText(FestibalSearchActivity.this, "Search Failed", Toast.LENGTH_SHORT).show();
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
