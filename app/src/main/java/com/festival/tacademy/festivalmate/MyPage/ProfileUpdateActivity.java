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
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.ShowMiniProfileResult;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Request;

public class ProfileUpdateActivity extends AppCompatActivity {

    ImageView photoView;
    Toolbar toolbar;

    EditText nameView;
    EditText messageView;
    Spinner locationView;
    EditText birthView;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        photoView = (ImageView)findViewById(R.id.image_profile);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nameView= (EditText)findViewById(R.id.edit_name);
        messageView = (EditText)findViewById(R.id.edit_state);
        locationView= (Spinner)findViewById(R.id.spinner_location);
        birthView  = (EditText)findViewById(R.id.edit_birthday);


        Button btn = (Button)findViewById(R.id.btn_update);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int memNo = PropertyManager.getInstance().getNo();
                String name = nameView.getText().toString();
                String message = messageView.getText().toString();
//                int birthday = Integer.parseInt(birthView.getText().toString());

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(message)) {
                    Toast.makeText(ProfileUpdateActivity.this, "invalid value", Toast.LENGTH_SHORT).show();
                    return;
                }

                NetworkManager.getInstance().modify_profile(ProfileUpdateActivity.this, memNo, "", name, message, new NetworkManager.OnResultListener<ShowMiniProfileResult>() {
                    @Override
                    public void onSuccess(Request request, ShowMiniProfileResult result) {
                        Toast.makeText(ProfileUpdateActivity.this,"성공",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        Toast.makeText(ProfileUpdateActivity.this,"실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();
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
