package com.festival.tacademy.festivalmate.login;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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

import com.festival.tacademy.festivalmate.Data.MySignInResult;
import com.festival.tacademy.festivalmate.Data.MySignUpResult;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MyPage.TermsOfUsePrivacyPolicyActivity;
import com.festival.tacademy.festivalmate.Preference.PreferenceActivity;
import com.festival.tacademy.festivalmate.R;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Request;

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

    TextView mentView;

    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Matcher matcher;
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

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
        mentView = (TextView)view.findViewById(R.id.text_ment);

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
                getImageFromGallery();
            }
        });

        btn = (Button)view.findViewById(R.id.btn_signup);
        btn.setOnClickListener(new View.OnClickListener() { // 가입하기 버튼 클릭시

            @Override
            public void onClick(View v) {

                String name = nameView.getText().toString();
                final String email = emailView.getText().toString();
                final String password = passwordView.getText().toString();
                String repassword = repasswordView.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || !validateEmail(email)
                        || TextUtils.isEmpty(password) || password.length() < 8 || TextUtils.isEmpty(repassword) || !password.equals(repassword) || !checkBoxAgree.isChecked()) {
                    Toast.makeText(getContext(), "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }


                NetworkManager.getInstance().signup(getContext(), name, email, password,mUploadFile,PropertyManager.getInstance().getRegistrationToken(), new NetworkManager.OnResultListener<MySignUpResult>() { // 회원가입
                    @Override
                    public void onSuccess(Request request, MySignUpResult result) {

                     //  Toast.makeText(getContext(), "가입성공 : " + result.message,Toast.LENGTH_SHORT).show();
                        final String regId = PropertyManager.getInstance().getRegistrationToken();

                        NetworkManager.getInstance().signin(getContext(), email, password,regId, new NetworkManager.OnResultListener<MySignInResult>() { //가입 성공하면 바로 로그인 요청

                            @Override
                            public void onSuccess(Request request, MySignInResult result) {  // 로그인 성공
                            //    Toast.makeText(getContext(), "로그인 성공 : " + result.message,Toast.LENGTH_SHORT).show();
                                PropertyManager.getInstance().setLogin(true);
                                PropertyManager.getInstance().setUser(result.result);
                                PropertyManager.getInstance().setEmail(email);
                                PropertyManager.getInstance().setPassword(password);
                                PropertyManager.getInstance().setNo(result.result.mem_no);
                                startActivity(new Intent(getContext(), PreferenceActivity.class));  // 선호도 조사 액티비티로 이동
                                getActivity().finish();
                            }

                            @Override
                            public void onFail(Request request, IOException exception) {
                                Toast.makeText(getContext(), "로그인 실패 : " + exception,Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        Toast.makeText(getContext(), "가입실패 : "+exception,Toast.LENGTH_SHORT).show();
                     //   Log.i("onFail: ",exception.getMessage());
                    }
                });
//

            }
        });

        if (savedInstanceState != null) {
            String path = savedInstanceState.getString("uploadFile");
            if (!TextUtils.isEmpty(path)) {
                mUploadFile = new File(path);
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 2;
                Bitmap bm = BitmapFactory.decodeFile(path, opts);
            }
        }

        return view;
    }

    private static final int RC_GALLERY = 1;

    private void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/jpeg");
        startActivityForResult(intent, RC_GALLERY);
    }

    File mUploadFile = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(uri, projection, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    mUploadFile = new File(path);
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 2;
                    Bitmap bm = BitmapFactory.decodeFile(path, opts);
                   // Toast.makeText(getContext(), mUploadFile.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                    mentView.setVisibility(View.GONE);
                    profileView.setImageBitmap(bm);
                }
            }

            return;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mUploadFile != null) {
            outState.putString("uploadfile", mUploadFile.getAbsolutePath());
        }
    }

    public boolean validateEmail(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    @Override
    public void onResume() {
        super.onResume();
        mentView.setVisibility(View.VISIBLE);
    }
}
