package com.festival.tacademy.festivalmate.MateMatching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_mate_talk);

        roomNameView = (EditText)findViewById(R.id.edit_room_name);
        maxSizeView = (EditText)findViewById(R.id.edit_max_size);
        locationView = (Spinner)findViewById(R.id.spinner_location);
        ageView = (Spinner)findViewById(R.id.spinner_age);


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


                NetworkManager.getInstance().create_new_chatroom(MakeMateTalkActivity.this, memNo, fesNo, chatroom_name, chatroom_maxSize, 1, 1, "", selectedArtist.result, new NetworkManager.OnResultListener<CreateNewChatroomResult>() {
                    @Override
                    public void onSuccess(Request request, CreateNewChatroomResult result) {
                        Toast.makeText(MakeMateTalkActivity.this,"성공",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MakeMateTalkActivity.this, ChattingActivity.class);
                        intent.putExtra("chatroomNo",result.result.getChatroom_no());
                        startActivity(intent);
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
