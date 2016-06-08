package com.festival.tacademy.festivalmate.MateTalk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
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
import com.festival.tacademy.festivalmate.Data.RequestNewChatResult;
import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.GCM.MyGcmListenerService;
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
    Button btn_join_list;

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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_navigation_back);

//        listView = (ListView)findViewById(R.id.listView);
//        mAdapter = new ChatCursorAdapter(this);
//        listView.setAdapter(mAdapter);
        inputView = (EditText)findViewById(R.id.edit_input);
        btn_join_list = (Button)findViewById(R.id.btn_join_list);



        recyclerView = (RecyclerView)findViewById(R.id.listView);

        mAdapter = new ChattingAdapter();
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChattingActivity.this, LinearLayoutManager.VERTICAL, false);
        // GridLayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        mateTalkRoom = (MateTalkRoom) intent.getExtras().getSerializable("chatting");

        if(mateTalkRoom.getChatroom_style()==1){
            btn_join_list.setVisibility(View.GONE);
        }

      //  String chatroomNo = intent.getStringExtra("chatroomNo");
        user = (User)getIntent().getSerializableExtra(EXTRA_USER);
        toolbarTitle.setText(mateTalkRoom.getChatroom_name());

        Button btn = (Button)findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int memNo = PropertyManager.getInstance().getNo();
            //    Toast.makeText(MyApplication.getContext(), ""+mateTalkRoom.getChatroom_no(),Toast.LENGTH_SHORT).show();
                final String message = inputView.getText().toString();
                if (!TextUtils.isEmpty(message)) {

                    NetworkManager.getInstance().chatroom_send_msg(ChattingActivity.this, memNo, mateTalkRoom.getChatroom_no(), message, memNo, new NetworkManager.OnResultListener<ChatroomSendMsgResult>() {
                        @Override
                        public void onSuccess(Request request, ChatroomSendMsgResult result) {
                            Toast.makeText(MyApplication.getContext(), "성공",Toast.LENGTH_SHORT).show();
                                //mAdapter.add();
                            Send send = new Send();
                            send.message= message;
                          //  send.date= "2016-07-24";
                            mAdapter.add(send);
                            inputView.setText("");
                        }

                        @Override
                        public void onFail(Request request, IOException exception) {
                            Toast.makeText(MyApplication.getContext(), "실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        btn_join_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChattingActivity.this, ChatJoinListActivity.class);
                 intent.putExtra("chatting",mateTalkRoom);
                  startActivity(intent);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

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


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
       // initData();
        LocalBroadcastManager.getInstance(this).registerReceiver(mChatReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mChatReceiver);
    }

    IntentFilter filter = new IntentFilter(MyGcmListenerService.ACTION_CHAT);

    RequestNewChatResult newChatResult = new RequestNewChatResult();
    BroadcastReceiver mChatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            newChatResult = (RequestNewChatResult) intent.getExtras().getSerializable(MyGcmListenerService.EXTRA_SENDER_ID);
          //  long sid = intent.getLongExtra(MyGcmListenerService.EXTRA_SENDER_ID, 0);
            Toast.makeText(ChattingActivity.this, "2313",Toast.LENGTH_SHORT).show();
                runOnUiThread(initRunnable);
                intent.putExtra(MyGcmListenerService.EXTRA_RESULT, true);
                return;

        }
    };

    Runnable initRunnable = new Runnable() {
        @Override
        public void run() {
            initData();
        }
    };

    private void initData() {

        Toast.makeText(ChattingActivity.this, "dasd",Toast.LENGTH_SHORT).show();
        for(Receive r : newChatResult.result) {
            if(r.chat_sender_no != PropertyManager.getInstance().getNo())
                 mAdapter.add(r);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
