package com.festival.tacademy.festivalmate.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.HomeActivity;
import com.festival.tacademy.festivalmate.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }




    }

    public void changeSignUp(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SignUpFragment())
                .addToBackStack(null)
                .commit();
    }
}
