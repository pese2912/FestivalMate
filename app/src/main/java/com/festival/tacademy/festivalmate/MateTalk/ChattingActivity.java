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
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.ChatroomSendMsgResult;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;

import okhttp3.Request;

public class ChattingActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "user";
    Toolbar toolbar;
    TextView toolbarTitle;
    ListView listView;
   // ChatCursorAdapter mAdapter;

    RecyclerView recyclerView;
    ChattingAdapter mAdapter;

    EditText inputView;
    MateTalkRoom mateTalkRoom;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        listView = (ListView)findViewById(R.id.listView);
//        mAdapter = new ChatCursorAdapter(this);
//        listView.setAdapter(mAdapter);
        inputView = (EditText)findViewById(R.id.edit_input);



        recyclerView = (RecyclerView)findViewById(R.id.listView);

        mAdapter = new ChattingAdapter();
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChattingActivity.this, LinearLayoutManager.VERTICAL, false);
        // GridLayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        mateTalkRoom = (MateTalkRoom) intent.getExtras().getSerializable("chatting");
      //  String chatroomNo = intent.getStringExtra("chatroomNo");
        user = (User)getIntent().getSerializableExtra(EXTRA_USER);
        toolbarTitle.setText(mateTalkRoom.getChatroom_name());

        Button btn = (Button)findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int memNo = PropertyManager.getInstance().getNo();
                Toast.makeText(MyApplication.getContext(), ""+mateTalkRoom.getChatroom_no(),Toast.LENGTH_SHORT).show();
                final String message = inputView.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    NetworkManager.getInstance().chatroom_send_msg(ChattingActivity.this, memNo, mateTalkRoom.getChatroom_no(), message, memNo, new NetworkManager.OnResultListener<ChatroomSendMsgResult>() {
                        @Override
                        public void onSuccess(Request request, ChatroomSendMsgResult result) {
                            Toast.makeText(MyApplication.getContext(), "성공",Toast.LENGTH_SHORT).show();
                                //mAdapter.add();
                            Send send = new Send();
                            send.message= message;
                            mAdapter.add(send);
                        }

                        @Override
                        public void onFail(Request request, IOException exception) {
                            Toast.makeText(MyApplication.getContext(), "실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
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
