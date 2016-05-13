package com.festival.tacademy.festivalmate.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.HomeActivity;
import com.festival.tacademy.festivalmate.R;

public class SignUpActivity extends AppCompatActivity {

    ImageView profileView; // 프로필 이미지 뷰
    EditText nameView; // 이름 입력
    EditText emailView; // 이메일 입력
    EditText passwordView; // 비밀번호 입력
    EditText repasswordView; // 비밀번호 재입력
    CheckBox checkBoxAgree; // 이용약관 동의



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        profileView = (ImageView)findViewById(R.id.image_profile);
        nameView = (EditText)findViewById(R.id.edit_name);
        emailView = (EditText)findViewById(R.id.edit_email);
        passwordView = (EditText)findViewById(R.id.edit_password);
        repasswordView  =(EditText)findViewById(R.id.edit_re_password);
        checkBoxAgree = (CheckBox)findViewById(R.id.checkBox_agree);

        Button btn = (Button)findViewById(R.id.btn_gallery);
        btn.setOnClickListener(new View.OnClickListener() { // 갤러리 버튼 클릭 시
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this, "갤러리 사진 가져오기",Toast.LENGTH_SHORT).show();

            }
        });

        btn = (Button)findViewById(R.id.btn_signup);
        btn.setOnClickListener(new View.OnClickListener() { // 가입하기 버튼 클릭시
            @Override
            public void onClick(View v) {

                Toast.makeText(SignUpActivity.this, "가입하기",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));

            }
        });



    }
}
