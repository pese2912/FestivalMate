package com.festival.tacademy.festivalmate.Preference;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.Data.PreferenceArtist;
import com.festival.tacademy.festivalmate.Data.ShowArtistSurveyResult;
import com.festival.tacademy.festivalmate.HomeActivity;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

public class PreferenceActivity extends AppCompatActivity {

    RecyclerView listView;
    PreferenceAdapter mAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        mAdapter = new PreferenceAdapter();

        mAdapter.setOnItemClickListener(new PreferenceViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Artist artist) {
                Toast.makeText(PreferenceActivity.this, artist.isCheck() + artist.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        listView = (RecyclerView)findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new GridLayoutManager(this,3));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prefer, menu);
        return true;
    }

    private void setData() {
        int memNo = PropertyManager.getInstance().getNo();

        NetworkManager.getInstance().showArtistSurvey(PreferenceActivity.this, memNo, new NetworkManager.OnResultListener<ShowArtistSurveyResult>() {
            @Override
            public void onSuccess(Request request, ShowArtistSurveyResult result) {
                Toast.makeText(PreferenceActivity.this,"성공",Toast.LENGTH_SHORT).show();
                mAdapter.clear();
                mAdapter.addAll(result.result);
            }

            @Override
            public void onFail(Request request, IOException exception) {

                Toast.makeText(PreferenceActivity.this,"실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

//           for (int i = 0; i < 20; i++) {
//                Artist artist = new Artist("name " + i,"http://sitehomebos.kocca.kr/knowledge/abroad/deep/__icsFiles/artimage/2012/03/26/2_1.jpg");
//               artist.setName("name " + i);
//            //  artist.setImage(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
//               artist.setPhoto("http://sitehomebos.kocca.kr/knowledge/abroad/deep/__icsFiles/artimage/2012/03/26/2_1.jpg");
//             mAdapter.add(artist); // 값 추가
//         }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.jump) {
            Intent intent = new Intent(PreferenceActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
