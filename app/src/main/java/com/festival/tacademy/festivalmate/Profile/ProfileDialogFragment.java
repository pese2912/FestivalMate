package com.festival.tacademy.festivalmate.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.CreateNewChatroomResult;
import com.festival.tacademy.festivalmate.Data.HateResult;
import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MateTalk.ChattingActivity;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.MyPage.ReportActivity;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;

import okhttp3.Request;


/**
 * Created by J.K.Lee on 2016-05-19.
 */
public class ProfileDialogFragment extends DialogFragment {
    public ProfileDialogFragment() {
    }

    User user;
    TextView user_state, user_name, user_email;
    ImageView user_image;
    Button btn_matetalk;
    RecyclerView rv_list1, rv_list2;
    ProfileLetsgoAdapter mAdapter1;
    ProfilePreferArtistAdapter mAdapter2;
    LinearLayoutManager mLayoutManager1;
    LinearLayoutManager mLayoutManager2;


    public void setUser(User user) {

//        mAdapter1.addAll(user.getLetsgo());
//        mAdapter2.addAll(user.getArtist());

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.MyDIalogTheme);

        mAdapter1 = new ProfileLetsgoAdapter();
        mAdapter2 = new ProfilePreferArtistAdapter();
        mLayoutManager1 = new LinearLayoutManager(MyApplication.getContext(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager2 = new LinearLayoutManager(MyApplication.getContext(), LinearLayoutManager.HORIZONTAL, false);

        Bundle bundle = getArguments();
        user = (User)bundle.getSerializable("user");
        mAdapter1.addAll(user.getMem_going());
        mAdapter2.addAll(user.getArtist());


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_dialog, container, false);
        TextView btn = (TextView)view.findViewById(R.id.btn_exit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn =(TextView)view.findViewById(R.id.btn_report); // 신고하기
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NetworkManager.getInstance().hate(getContext(), user.getMem_no(), new NetworkManager.OnResultListener<HateResult>() {
//                    @Override
//                    public void onSuccess(Request request, HateResult result) {
//
//                        Toast.makeText(getContext(), "성공",Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFail(Request request, IOException exception) {
//
//                        Toast.makeText(getContext(), "실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                });

                startActivity(new Intent(getActivity(), ReportActivity.class));
            }
        });

         btn_matetalk = (Button)view.findViewById(R.id.btn_maketalk);
        if(PropertyManager.getInstance().getNo() == 0){
            btn_matetalk.setVisibility(View.GONE);
        }
        btn_matetalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int memNo = PropertyManager.getInstance().getNo();
                int anoNo = user.getMem_no();

                NetworkManager.getInstance().create_new_private_chatroom(MyApplication.getContext(), memNo, anoNo, new NetworkManager.OnResultListener<CreateNewChatroomResult>() {
                    @Override
                    public void onSuccess(Request request, CreateNewChatroomResult result) {
                      //  Toast.makeText(getContext(), "성공",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), ChattingActivity.class);
                        MateTalkRoom room = new MateTalkRoom();
                        room.setChatroom_no(result.result.getChatroom_no());
                        room.setChatroom_style(1);
                        //room.setFestival_name(result.result.getFestival_name());
                        room.setChatroom_name(user.getName());
                        intent.putExtra("chatting",room);
                        startActivity(intent);
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                     //   Toast.makeText(getContext(), "실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        
        user_state = (TextView)view.findViewById(R.id.text_user_state);
        user_image= (ImageView)view.findViewById(R.id.profile_photo);
        user_name = (TextView)view.findViewById(R.id.text_user_name);
        user_email = (TextView)view.findViewById(R.id.text_user_email);
        rv_list1 = (RecyclerView)view.findViewById(R.id.rv_list1);
        rv_list2 = (RecyclerView)view.findViewById(R.id.rv_list2);

        rv_list1.setAdapter(mAdapter1);
        rv_list1.setLayoutManager(mLayoutManager1);
        rv_list2.setAdapter(mAdapter2);
        rv_list2.setLayoutManager(mLayoutManager2);
//
//        Bundle bundle = getArguments();
//        bundle.getSerializable("user");
//        mAdapter1.addAll(user.getMem_going());
//        mAdapter2.addAll(user.getArtist());

        user_state.setText(user.getMem_state_msg());
        user_name.setText(user.getName()+" 님");
        user_email.setText("("+user.getMem_id()+")");
        Glide.with(user_image.getContext()).load(NetworkManager.MY_SERVER+"/"+user.getPhoto()).into(user_image);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_height);
        getDialog().getWindow().setLayout(width, height);
    }
}
