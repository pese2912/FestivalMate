package com.festival.tacademy.festivalmate.MateMatching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.CreateNewChatroomResult;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.SelectedArtist;
import com.festival.tacademy.festivalmate.Data.ShowMatchingResult;
import com.festival.tacademy.festivalmate.HomeActivity;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MateTalk.ChattingActivity;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;

import okhttp3.Request;

public class MakeMateTalkActivity extends AppCompatActivity {

    Toolbar toolbar;
    Festival festival;
    EditText roomNameView;
    EditText maxSizeView;
    Spinner locationView;
    Spinner ageView;
    SelectedArtist selectedArtist;
    int location=1,age=1;

    String[] city = {"서울","부산","대구","인천","광주"
            ,"대전","울산","세종","경기","강원","충북"
            ,"충남","전북","전남","경북","경남","제주"};

    String[] old = {"10대","20대","30대","40대","40대 이상"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_mate_talk);

        roomNameView = (EditText)findViewById(R.id.edit_room_name);
        maxSizeView = (EditText)findViewById(R.id.edit_max_size);
        locationView = (Spinner)findViewById(R.id.spinner_location);
        ageView = (Spinner)findViewById(R.id.spinner_age);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,city); //스피너와 안의 내용을 합치는것

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,old); //스피너와 안의 내용을 합치는것

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationView.setAdapter(adapter);
        ageView.setAdapter(adapter2);

        locationView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location= position+1;
               // Toast.makeText(MakeMateTalkActivity.this,location+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        locationView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age= position+1;
                //Toast.makeText(MakeMateTalkActivity.this,location+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        Intent intent = getIntent();
        festival = (Festival) intent.getExtras().getSerializable("festival");
        selectedArtist = (SelectedArtist) intent.getExtras().getSerializable("selectedArtist");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button btn = (Button)findViewById(R.id.btn_maketalk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int memNo = PropertyManager.getInstance().getNo();
                int fesNo = festival.getFestival_no();

                String chatroom_name = roomNameView.getText().toString();
                int chatroom_maxSize = Integer.parseInt(maxSizeView.getText().toString());

                NetworkManager.getInstance().create_new_chatroom(MakeMateTalkActivity.this, memNo, fesNo, chatroom_name, chatroom_maxSize,location, age, "", selectedArtist.result, new NetworkManager.OnResultListener<CreateNewChatroomResult>() {
                    @Override
                    public void onSuccess(Request request, CreateNewChatroomResult result) {
                        Toast.makeText(MakeMateTalkActivity.this,"성공",Toast.LENGTH_SHORT).show();
                   //     Intent intent = new Intent(MakeMateTalkActivity.this, ChattingActivity.class);
                     //   intent.putExtra("chatroomNo",result.result.getChatroom_no());
                       // startActivity(intent);
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        Toast.makeText(MakeMateTalkActivity.this,"실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
//                Intent intent = new Intent(MakeMateTalkActivity.this, HomeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
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
