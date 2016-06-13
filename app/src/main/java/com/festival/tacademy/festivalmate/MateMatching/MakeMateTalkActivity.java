package com.festival.tacademy.festivalmate.MateMatching;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.CreateNewChatroomResult;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.FestivalDetailResult;
import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.Data.SelectedArtist;
import com.festival.tacademy.festivalmate.Data.ShowMatchingResult;
import com.festival.tacademy.festivalmate.HomeActivity;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MateTalk.ChattingActivity;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

import java.io.File;
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
    int location=0,age=0,bg=1;
    RadioGroup group;

    String[] city = {"무관","서울","부산","대구","인천","광주"
            ,"대전","울산","세종","경기","강원","충북"
            ,"충남","전북","전남","경북","경남","제주"};

    String[] old = {"무관","10대","20대","30대","40대","40대 이상"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_mate_talk);

        roomNameView = (EditText)findViewById(R.id.edit_room_name);
        maxSizeView = (EditText)findViewById(R.id.edit_max_size);
        locationView = (Spinner)findViewById(R.id.spinner_location);
        ageView = (Spinner)findViewById(R.id.spinner_age);
        group = (RadioGroup)findViewById(R.id.group);



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
                location= position;
               // Toast.makeText(MakeMateTalkActivity.this,location+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ageView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age= position;
                //Toast.makeText(MakeMateTalkActivity.this,age+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        Intent intent = getIntent();
        festival = (Festival) intent.getExtras().getSerializable("festival");
        NetworkManager.getInstance().show_festival_detail(MakeMateTalkActivity.this, festival.getFestival_no(), PropertyManager.getInstance().getNo(),
                new NetworkManager.OnResultListener<FestivalDetailResult>() {
                    @Override
                    public void onSuccess(Request request, FestivalDetailResult result) {
                        if(result.success == 1) {
                            festival = result.result;
                        }
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });



        selectedArtist = (SelectedArtist) intent.getExtras().getSerializable("selectedArtist");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_navigation_back);
        Button btn = (Button)findViewById(R.id.btn_maketalk);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int memNo = PropertyManager.getInstance().getNo();
                int fesNo = festival.getFestival_no();

                final String chatroom_name = roomNameView.getText().toString();



                if(TextUtils.isEmpty(chatroom_name) || TextUtils.isEmpty(maxSizeView.getText().toString())   ) {
                    Toast.makeText(MakeMateTalkActivity.this,"잘못된 입력입니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                int chatroom_maxSize=  Integer.parseInt(maxSizeView.getText().toString());
                if(chatroom_maxSize >20){
                    Toast.makeText(MakeMateTalkActivity.this,"최대 인원은 20명입니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
//                for(int i = 0; i <selectedArtist.result.size(); i++){
//
//                    Toast.makeText(MakeMateTalkActivity.this, selectedArtist.result.get(i).getName(),Toast.LENGTH_SHORT).show();
//
//                }


                if(mUploadFile == null){
                    mUploadFile = new File("/default.jpg");
                    mUploadFile.mkdirs();
                }

                NetworkManager.getInstance().create_new_chatroom(MakeMateTalkActivity.this, memNo, fesNo, chatroom_name, chatroom_maxSize,location, age, mUploadFile,bg, selectedArtist.result, new NetworkManager.OnResultListener<CreateNewChatroomResult>() {

                    @Override
                    public void onSuccess(Request request, CreateNewChatroomResult result) {
                       // Toast.makeText(MakeMateTalkActivity.this,"성공",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MakeMateTalkActivity.this, ChattingActivity.class);
                        MateTalkRoom room = new MateTalkRoom();
                        room.setChatroom_no(result.result.getChatroom_no());
                        //room.setFestival_name(result.result.getFestival_name());
                        //Toast.makeText(MyApplication.getContext(),festival.getFestival_no()+"",Toast.LENGTH_SHORT).show();
                        room.setChatroom_name(chatroom_name);
                        intent.putExtra("chatting",room);
                        startActivity(intent);

                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                     //   Toast.makeText(MakeMateTalkActivity.this,"실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
//                Intent intent = new Intent(MakeMateTalkActivity.this, HomeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
            }
        });



        if (savedInstanceState != null) {
            String path = savedInstanceState.getString("uploadFile");
            if (!TextUtils.isEmpty(path)) {
                mUploadFile = new File(path);
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 2;
                Bitmap bm = BitmapFactory.decodeFile(path, opts);
            }
        }


        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radioButton:
                        bg=1;

                        break;
                    case R.id.radioButton2:

                        bg=2;
                        break;
                    case R.id.radioButton3:

                        bg=3;
                        break;
                    case R.id.radioButton4:

                        bg=4;
                        break;
                    case R.id.radioButton5:
                        bg=0;
                        getImageFromGallery();
                        //Toast.makeText(MakeMateTalkActivity.this,mUploadFile.getName()+"",Toast.LENGTH_SHORT).show();
                        break;
                }
             //   Toast.makeText(MakeMateTalkActivity.this,bg+"",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private static final int RC_GALLERY = 1;

    private void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/jpeg");
        startActivityForResult(intent, RC_GALLERY);
    }

    File mUploadFile = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(uri, projection, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    mUploadFile = new File(path);
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 2;
                    Bitmap bm = BitmapFactory.decodeFile(path, opts);
                }
            }
            return;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mUploadFile != null) {
            outState.putString("uploadfile", mUploadFile.getAbsolutePath());
        }
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
