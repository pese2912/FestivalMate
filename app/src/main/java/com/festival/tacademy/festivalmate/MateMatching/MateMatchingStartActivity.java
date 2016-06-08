package com.festival.tacademy.festivalmate.MateMatching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.Data.FestibalLineUp;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.Lineup;
import com.festival.tacademy.festivalmate.Data.SelectedArtist;
import com.festival.tacademy.festivalmate.Data.ShowFestivalLineups;
import com.festival.tacademy.festivalmate.Data.ShowMatchingResult;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MateMatchingStartActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView listView;
    MateMatchingLineUpAdapter mAdapter;
    Festival festival;
    ShowMatchingResult result;
    List<Lineup> selectedLineup;
    List<Artist> selectedArtist;
    SelectedArtist selectedArtistResult;
    Spinner festival_spinner;
    ArrayAdapter<String> adapter;
    List<Festival> items;
    int num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mate_matching_start);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_navigation_back);

        festival_spinner = (Spinner)findViewById(R.id.spinner_festival);

        festival_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MateMatchingStartActivity.this, items.get(position).getFestival_no() + items.get(position).getFestival_name(), Toast.LENGTH_SHORT).show();
                num = items.get(position).getFestival_no();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView = (RecyclerView)findViewById(R.id.rv_list);
        mAdapter = new MateMatchingLineUpAdapter();

        selectedArtistResult= new SelectedArtist();
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        festival = (Festival)intent.getExtras().getSerializable("festival");

        selectedLineup = new ArrayList<>();
        selectedArtist= new ArrayList<>();
        result = new ShowMatchingResult();

        Button btn = (Button)findViewById(R.id.btn_match);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<selectedLineup.size(); i++){
                    for(int j =0; j<selectedLineup.get(i).getLineup().size(); j++){
                        if(selectedLineup.get(i).getLineup().get(j).isCheck() == 1){
                            Toast.makeText(MateMatchingStartActivity.this,""+selectedLineup.get(i).getLineup().get(j).getName(),Toast.LENGTH_SHORT).show();
                            selectedArtist.add(selectedLineup.get(i).getLineup().get(j));
                        }
                    }
                }

                //  Toast.makeText(MateMatchingStartActivity.this,""+selectedArtist.size(),Toast.LENGTH_SHORT).show();
                int memNo = PropertyManager.getInstance().getNo();
                int fesNo = festival.getFestival_no();

                NetworkManager.getInstance().show_matching_result(MateMatchingStartActivity.this, memNo, fesNo, selectedArtist, new NetworkManager.OnResultListener<ShowMatchingResult>() {
                    //매칭 결과
                    @Override
                    public void onSuccess(Request request, ShowMatchingResult result) {

               //         Toast.makeText(MateMatchingStartActivity.this,"성공"+ result.result.get(0).getMatched_artist_number(),Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MateMatchingStartActivity.this, MateMatchingActivity.class);
                        intent.putExtra("ShowMatchingResult",result);
                        intent.putExtra("festival",festival);
                        selectedArtistResult.result = selectedArtist;
                        intent.putExtra("selectedArtist",selectedArtistResult);
                        startActivity(intent);

                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        Toast.makeText(MateMatchingStartActivity.this,"실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

//                Intent intent = new Intent(MateMatchingStartActivity.this, MateMatchingActivity.class);
//                        intent.putExtra("ShowMatchingResult",result);
//                        startActivity(intent);
            }
        });

        btn = (Button)findViewById(R.id.btn_search);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(MateMatchingStartActivity.this, "dddddd: " + num, Toast.LENGTH_SHORT).show();
                mAdapter.clear();
                setData(num);
            }
        });

        setData(festival.getFestival_no());
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        selectedArtist.clear();
//        selectedLineup.clear();
//        initData();
//    }

    private void setData(int no){
     //   Toast.makeText(MateMatchingStartActivity.this,"Init()()(): "+no,Toast.LENGTH_SHORT).show();
        NetworkManager.getInstance().show_festival_lineups(MateMatchingStartActivity.this, no, new NetworkManager.OnResultListener<ShowFestivalLineups>() {
            @Override
            public void onSuccess(Request request, ShowFestivalLineups result) {
                Toast.makeText(MateMatchingStartActivity.this,"성공",Toast.LENGTH_SHORT).show();

                mAdapter.clear();
                mAdapter.addAll(result.result.getFestival_lineups());
                selectedLineup = result.result.getFestival_lineups();

                String[] festivals = new String[result.festival_list.size()];

                items = result.festival_list;
                for(int i=0; i<result.festival_list.size(); i++) {
                    festivals[i] = result.festival_list.get(i).getFestival_name();
                }
                adapter = new ArrayAdapter<>(MateMatchingStartActivity.this, R.layout.spinner_item, festivals);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                festival_spinner.setAdapter(adapter);
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(MateMatchingStartActivity.this,"실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();
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
