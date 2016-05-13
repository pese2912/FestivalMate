package com.festival.tacademy.festivalmate.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.R;

public class LoginActivity extends AppCompatActivity {

    EditText emailView; // 이메일 입력
    EditText passwordView; // 패스워드 입력

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailView = (EditText)findViewById(R.id.edit_email);
        passwordView = (EditText)findViewById(R.id.edit_password);

        Button btn = (Button)findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() { //로그인 하기 버튼 클릭시
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "로그인",Toast.LENGTH_SHORT).show();

            }
        });

        btn = (Button)findViewById(R.id.btn_facebook_login);
        btn.setOnClickListener(new View.OnClickListener() { // 페이스북 로그인 버튼 클릭시
            @Override
            public void onClick(View v) {

                Toast.makeText(LoginActivity.this, "페이스북 로그인",Toast.LENGTH_SHORT).show();
            }
        });

        btn = (Button)findViewById(R.id.btn_kaokaotalk_login);
        btn.setOnClickListener(new View.OnClickListener() { // 카카오톡 로그인 버튼 클릭시
            @Override
            public void onClick(View v) {

                Toast.makeText(LoginActivity.this, "카카오톡 로그인",Toast.LENGTH_SHORT).show();
            }
        });

        btn= (Button)findViewById(R.id.btn_email_signup);
        btn.setOnClickListener(new View.OnClickListener() { // 이메일가입하기 버튼 클릭시
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignUpActivity.class)); // 회원가입 액티비티로 이동

            }
        });


    }
}
