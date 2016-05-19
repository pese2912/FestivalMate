package com.festival.tacademy.festivalmate.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.MyPage.TermsOfUsePrivacyPolicyActivity;
import com.festival.tacademy.festivalmate.Preference.PreferenceActivity;
import com.festival.tacademy.festivalmate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    ImageView profileView; // 프로필 이미지 뷰
    EditText nameView; // 이름 입력
    EditText emailView; // 이메일 입력
    EditText passwordView; // 비밀번호 입력
    EditText repasswordView; // 비밀번호 재입력
    CheckBox checkBoxAgree; // 이용약관 동의 체크
    TextView agreeMent;  //이용약관 동의 멘트


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);

        profileView = (ImageView)view.findViewById(R.id.image_profile);
        nameView = (EditText)view.findViewById(R.id.edit_name);
        emailView = (EditText)view.findViewById(R.id.edit_email);
        passwordView = (EditText)view.findViewById(R.id.edit_password);
        repasswordView  =(EditText)view.findViewById(R.id.edit_re_password);
        checkBoxAgree = (CheckBox)view.findViewById(R.id.checkBox_agree);
        agreeMent = (TextView)view.findViewById(R.id.text_agree);

        agreeMent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TermsOfUsePrivacyPolicyActivity.class));
            }
        });

        Button btn = (Button)view.findViewById(R.id.btn_gallery);
        btn.setOnClickListener(new View.OnClickListener() { // 갤러리 버튼 클릭 시
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "갤러리 사진 가져오기",Toast.LENGTH_SHORT).show();

            }
        });

        btn = (Button)view.findViewById(R.id.btn_signup);
        btn.setOnClickListener(new View.OnClickListener() { // 가입하기 버튼 클릭시
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "가입하기",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), PreferenceActivity.class));  // 선호도 조사 액티비티로 이동
               getActivity().finish();

            }
        });
        return view;
    }

}
