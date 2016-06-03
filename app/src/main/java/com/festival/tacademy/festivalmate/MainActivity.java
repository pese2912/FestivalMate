package com.festival.tacademy.festivalmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.login.LoginActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PropertyManager.getInstance().setNo(0);
        PropertyManager.getInstance().setEmail("");
        PropertyManager.getInstance().setPassword("");
        PropertyManager.getInstance().setUser(null);


        TextView btn = (TextView)findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() { // 로그인 버튼 눌렀을 경우
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);   // 로그인 액티비티로 이동
            }
        });

        btn = (TextView)findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() { // 시작하기 버튼 눌렀을 경우
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent); //일단 홈 액티비티로 이동, 원래는 선호도조사 액티비티로 이동

            }
        });

    }
}
