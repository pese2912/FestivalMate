package com.festival.tacademy.festivalmate.MyPage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.ModifyProfileResult;
import com.festival.tacademy.festivalmate.Data.ShowMyProfileResult;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MateTalk.PreferArtistAdapter;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.Preference.PreferenceActivity;
import com.festival.tacademy.festivalmate.Preference.Preference_profile_Activity;
import com.festival.tacademy.festivalmate.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Request;

public class ProfileUpdateActivity extends AppCompatActivity {

    String[] city = {"무관","서울","부산","대구","인천","광주"
            ,"대전","울산","세종","경기","강원","충북"
            ,"충남","전북","전남","경북","경남","제주"};

    ImageView photoView;
    Toolbar toolbar;
    RecyclerView rv_list;
    EditText nameView;
    EditText messageView;
    Spinner locationView;
    String imgUrl;
    int location=0;

    PreferArtistAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        rv_list = (RecyclerView)findViewById(R.id.rv_list3);
        mAdapter = new PreferArtistAdapter();
        rv_list.setAdapter(mAdapter);
        rv_list.setLayoutManager(new LinearLayoutManager(MyApplication.getContext(), LinearLayoutManager.HORIZONTAL, false));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_navigation_back);
        nameView= (EditText)findViewById(R.id.edit_name);
        messageView = (EditText)findViewById(R.id.edit_state);
        locationView= (Spinner)findViewById(R.id.spinner_location);
        photoView = (ImageView)findViewById(R.id.image_profile);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,city); //스피너와 안의 내용을 합치는것


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationView.setAdapter(adapter);
        setData();
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


        Button btn = (Button)findViewById(R.id.btn_update);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int memNo = PropertyManager.getInstance().getNo();
                String name = nameView.getText().toString();
                String message = messageView.getText().toString();
//                int birthday = Integer.parseInt(birthView.getText().toString());

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(ProfileUpdateActivity.this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                NetworkManager.getInstance().modify_profile(ProfileUpdateActivity.this, memNo, mUploadFile, name, message,location, new NetworkManager.OnResultListener<ModifyProfileResult>() {
                    @Override
                    public void onSuccess(Request request, ModifyProfileResult result) {
                    //    Toast.makeText(ProfileUpdateActivity.this,"성공",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                     //   Toast.makeText(ProfileUpdateActivity.this,"실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn = (Button)findViewById(R.id.btn_gallery);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getImageFromGallery();
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

        btn = (Button)findViewById(R.id.btn_prefer_update);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileUpdateActivity.this, Preference_profile_Activity.class));
            }
        });
    }

    private void setData(){

        int memNo = PropertyManager.getInstance().getNo();

        NetworkManager.getInstance().show_my_profile(ProfileUpdateActivity.this, memNo, new NetworkManager.OnResultListener<ShowMyProfileResult>() {
            @Override
            public void onSuccess(Request request, ShowMyProfileResult result) {

                nameView.setText(result.result.getName());
                messageView.setText(result.result.getMem_state_msg());
                Glide.with(photoView.getContext()).load(NetworkManager.MY_SERVER+"/"+result.result.mem_img).into(photoView);
                imgUrl=result.result.mem_img;
                mAdapter.addAll(result.result.getArtist());
              //  Toast.makeText(MyApplication.getContext(), NetworkManager.MY_SERVER+"/"+result.result.mem_img,Toast.LENGTH_SHORT).show();
               // Log.i("photo",NetworkManager.MY_SERVER+"/"+result.result.mem_img);

            }
            @Override
            public void onFail(Request request, IOException exception) {

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
                    photoView.setImageBitmap(bm);
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
