package com.festival.tacademy.festivalmate.MateTalk;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.R;

public class ChattingActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTitle;

    RecyclerView recyclerView;
    ChattingAdapter mAdapter;

    EditText inputView;
    RadioGroup typeView;
    MateTalkRoom mateTalkRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputView = (EditText)findViewById(R.id.edit_input);
        typeView = (RadioGroup)findViewById(R.id.group_type);


        recyclerView = (RecyclerView)findViewById(R.id.list);

        mAdapter = new ChattingAdapter();
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChattingActivity.this, LinearLayoutManager.VERTICAL, false);
        // GridLayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        mateTalkRoom = (MateTalkRoom) intent.getExtras().getSerializable("chatting");
        String chatroomNo = intent.getStringExtra("chatroomNo");

        toolbarTitle.setText(mateTalkRoom.getChatroom_name());

        Button btn = (Button)findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = inputView.getText().toString();
                if (!TextUtils.isEmpty(message)) {

                    switch (typeView.getCheckedRadioButtonId()) {

                        case R.id.radio_s:
                            Send send = new Send();
                            send.message = message;

                            mAdapter.add(send);
                            break;

                        case R.id.radio_r:

                            Receive receive = new Receive();
                            receive.message = message;
                            receive.icon = ContextCompat.getDrawable(ChattingActivity.this, R.mipmap.ic_launcher);

                            mAdapter.add(receive);

                            break;
                        case R.id.radio_d:
                            Date date = new Date();
                            date.message = message;
                            mAdapter.add(date);

                            break;

                    }
                    inputView.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_join_list, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        else if(id == R.id.chat_join_list){
            Intent intent = new Intent(ChattingActivity.this, ChatJoinListActivity.class);
            intent.putExtra("chatting",mateTalkRoom);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
