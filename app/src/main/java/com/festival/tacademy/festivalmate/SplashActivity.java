package com.festival.tacademy.festivalmate;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.MySignInResult;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.login.LoginActivity;

import java.io.IOException;

import okhttp3.Request;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startSplash();

    }


    private void startSplash() {
//        String email = PropertyManager.getInstance().getEmail();
//        if (!TextUtils.isEmpty(email)) {
//            String password = PropertyManager.getInstance().getPassword();
//            NetworkManager.getInstance().signin(SplashActivity.this, email, password, new NetworkManager.OnResultListener<MySignInResult>() {
//                @Override
//                public void onSuccess(Request request, MySignInResult result) {
//                    if(result.success==1)
//                    {
//                        Toast.makeText(SplashActivity.this,"자동로그인성공",Toast.LENGTH_SHORT).show();
//                        PropertyManager.getInstance().setLogin(true);
//                        PropertyManager.getInstance().setUser(result.result);
//                        goHomeActivity();
//                    }
//                }
//                @Override
//                public void onFail(Request request, IOException exception) {
//                    Toast.makeText(SplashActivity.this, "자동로그인 실패 : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
//                    goMainActivity();
//                }
//            });
//        }else {
//            goMainActivity();
//        }

        goMainActivity();
    }


    private void goHomeActivity() { // 홈으로 이동
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }

    private void goMainActivity() { //로그인 페이지로 이동
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
