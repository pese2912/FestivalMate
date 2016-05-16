package com.festival.tacademy.festivalmate.MyPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.R;

public class QuestionActivity extends AppCompatActivity {

    EditText emailView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        emailView = (EditText)findViewById(R.id.text_send);
        Button btn = (Button)findViewById(R.id.btn_quest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuestionActivity.this, "문의완료",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
