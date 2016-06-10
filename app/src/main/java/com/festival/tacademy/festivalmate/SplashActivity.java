package com.festival.tacademy.festivalmate;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.festival.tacademy.festivalmate.Data.MySignInResult;
import com.festival.tacademy.festivalmate.GCM.RegistrationIntentService;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.login.LoginActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.IOException;

import okhttp3.Request;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler(Looper.getMainLooper());
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                doRealStart();
            }
        };
        setUpIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
    }
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST &&
                resultCode == Activity.RESULT_OK) {
            setUpIfNeeded();
        }
    }

    private void setUpIfNeeded() {
        if (checkPlayServices()) {
            String regId = PropertyManager.getInstance().getRegistrationToken();
            if (!regId.equals("")) {
            //    Toast.makeText(SplashActivity.this,regId,Toast.LENGTH_SHORT).show();
              //  System.out.print("regId"+regId);
                doRealStart();
            } else {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    private void doRealStart() {
        startSplash();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Dialog dialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                dialog.show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }


    private void startSplash() {

        String email = PropertyManager.getInstance().getEmail();
        if (!TextUtils.isEmpty(email)) {
            String password = PropertyManager.getInstance().getPassword();
            String regId = PropertyManager.getInstance().getRegistrationToken();
            NetworkManager.getInstance().signin(SplashActivity.this, email, password, regId,new NetworkManager.OnResultListener<MySignInResult>() {
                @Override
                public void onSuccess(Request request, MySignInResult result) {
                    if(result.success==1)
                    {
                      //  Toast.makeText(SplashActivity.this,"자동로그인성공",Toast.LENGTH_SHORT).show();
                        PropertyManager.getInstance().setLogin(true);
                        PropertyManager.getInstance().setUser(result.result);
                        goHomeActivity();
                    }
                }
                @Override
                public void onFail(Request request, IOException exception) {
                    //Toast.makeText(SplashActivity.this, "자동로그인 실패 : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    goMainActivity();
                }
            });
        }else {
            String facebookId = PropertyManager.getInstance().getFacebookId();
            if (!TextUtils.isEmpty(facebookId)) {
                AccessToken token = AccessToken.getCurrentAccessToken();
                if (token == null) {
                    PropertyManager.getInstance().setFacebookId("");
                    goMainActivity();
                } else {
                    if (facebookId.equals(token.getUserId())) {

                        NetworkManager.getInstance().login_fb(this, token.getToken(), PropertyManager.getInstance().getRegistrationToken(), new NetworkManager.OnResultListener<MySignInResult>() {
                            @Override
                            public void onSuccess(Request request, MySignInResult result) {

                            }

                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });

                    } else {
                        PropertyManager.getInstance().setFacebookId("");
                        LoginManager.getInstance().logOut();
                        goMainActivity();
                    }
                }
            } else {
                goMainActivity();
            }
        }


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
