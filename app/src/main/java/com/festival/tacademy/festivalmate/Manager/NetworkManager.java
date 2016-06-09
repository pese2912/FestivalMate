package com.festival.tacademy.festivalmate.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.WorkerThread;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.Data.ArtistNo;
import com.festival.tacademy.festivalmate.Data.ChatroomApproveResult;
import com.festival.tacademy.festivalmate.Data.ChatroomDisapproveResult;
import com.festival.tacademy.festivalmate.Data.ChatroomKickResult;
import com.festival.tacademy.festivalmate.Data.ChatroomMemListResult;
import com.festival.tacademy.festivalmate.Data.ChatroomSendMsgResult;
import com.festival.tacademy.festivalmate.Data.CheckGoingResult;
import com.festival.tacademy.festivalmate.Data.CreateNewChatroomResult;
import com.festival.tacademy.festivalmate.Data.DeleteGoingListResult;
import com.festival.tacademy.festivalmate.Data.FestivalDetailResult;
import com.festival.tacademy.festivalmate.Data.FestivalResultResult;
import com.festival.tacademy.festivalmate.Data.HateResult;
import com.festival.tacademy.festivalmate.Data.JsonNewChatroom;
import com.festival.tacademy.festivalmate.Data.JsonSelectedArtist;
import com.festival.tacademy.festivalmate.Data.ModifyProfileResult;
import com.festival.tacademy.festivalmate.Data.MySignInResult;
import com.festival.tacademy.festivalmate.Data.MySignUpResult;
import com.festival.tacademy.festivalmate.Data.RequestChatroomJoinResult;
import com.festival.tacademy.festivalmate.Data.RequestNewChatResult;
import com.festival.tacademy.festivalmate.Data.ShowArtistSurveyResult;
import com.festival.tacademy.festivalmate.Data.ShowFestivalLineups;
import com.festival.tacademy.festivalmate.Data.ShowGoingListResult;
import com.festival.tacademy.festivalmate.Data.ShowMatchingResult;
import com.festival.tacademy.festivalmate.Data.ShowMemProfileResult;
import com.festival.tacademy.festivalmate.Data.ShowMiniProfileResult;
import com.festival.tacademy.festivalmate.Data.ShowMyChatroomListResult;
import com.festival.tacademy.festivalmate.Data.ShowMyProfileResult;
import com.festival.tacademy.festivalmate.Data.ShowWaitingListResult;
import com.festival.tacademy.festivalmate.MyApplication;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Tacademy on 2016-05-23.
 */

public class NetworkManager {

    private static NetworkManager instance;
    public static NetworkManager getInstance(){
        if(instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }
    private static final int DEFAULT_CACHE_SIZE = 50*1024*1024;
    private static final String DEFAULT_CACHE_DIR="miniapp";
    OkHttpClient mClient;

    private NetworkManager(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        CookieManager cookieManager = new CookieManager();
        builder.cookieJar(new JavaNetCookieJar(cookieManager)); //메모리 저장하는 쿠키

        File dir = new File(context.getExternalCacheDir(),DEFAULT_CACHE_DIR);
        if(!dir.exists()){
            dir.mkdir();
        }

        builder.cache(new Cache(dir, DEFAULT_CACHE_SIZE));

        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

        mClient = builder.build(); // 외장메모리 저장하는 캐시

    }

    public interface OnResultListener<T>{
        public  void onSuccess(Request request, T result);
        public void onFail(Request request, IOException exception);
    }

    private static final  int MESSAGE_SUCCESS= 1;
    private static final int MESSAGE_FAIL = 0;

    class NetworkHandler extends Handler {
        public NetworkHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NetworkResult result  = (NetworkResult)msg.obj;

            switch (msg.what){
                case  MESSAGE_SUCCESS:
                    result.listener.onSuccess(result.request, result.result);
                    break;

                case MESSAGE_FAIL:
                    result.listener.onFail(result.request, result.exception);
                    break;
            }
        }
    }

    NetworkHandler mHandler = new NetworkHandler(Looper.getMainLooper());
    static class NetworkResult<T>{
        Request request;
        OnResultListener<T> listener;
        IOException exception;
        T result;

    }

    Gson gson = new Gson();

    public static final String MY_SERVER ="http://52.79.159.248:3000";  //회원가입 "http://192.168.204.189:3000";
    private static final String URL_SIGN_UP = MY_SERVER + "/signup";

    public Request signup(Object tag, String mem_name,
                          String mem_id,
                          String mem_pwd,
                          File mem_img,
                          String mem_registration_id,
                          OnResultListener<MySignUpResult> listener) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        if(mem_img==null) {

            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("mem_name", mem_name);
            builder.addFormDataPart("mem_pwd", mem_pwd);
            builder.addFormDataPart("mem_id", mem_id);

        }

        else{

            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("mem_name", mem_name);
            builder.addFormDataPart("mem_pwd", mem_pwd);
            builder.addFormDataPart("mem_id", mem_id);
            builder.addFormDataPart("mem_img", mem_img.getName(),
                    RequestBody.create(MediaType.parse("image/jpeg"), mem_img));
            builder.addFormDataPart("mem_registration_id", mem_registration_id);
        }

        RequestBody body = builder.build();

        Request request = new Request.Builder()
                .url(URL_SIGN_UP)
                .post(body)
                .build();

        final NetworkResult<MySignUpResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    MySignUpResult data = gson.fromJson(text, MySignUpResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        
        return request;
    }


    private static final String URL_SIGN_IN = MY_SERVER + "/login"; // 로그인

    public Request signin(Object tag,
                          String mem_id,
                          String mem_pwd,
                          String mem_registration_id,
                          OnResultListener<MySignInResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_id", mem_id)
                .add("mem_pwd", mem_pwd)
                .add("mem_registration_id", mem_registration_id)
                .build();

        Request request = new Request.Builder()
                .url(URL_SIGN_IN)
                .post(body)
                .build();

        final NetworkResult<MySignInResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    MySignInResult data = gson.fromJson(text, MySignInResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }



    private static final String URL_SHOW_ARTIST_SURVEY = MY_SERVER + "/show_artist_survey"; // 선호가수 조사 리스트 조회
    public Request showArtistSurvey(Object tag,
                          int mem_no,
                          OnResultListener<ShowArtistSurveyResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_SHOW_ARTIST_SURVEY)
                .post(body)
                .build();

        final NetworkResult<ShowArtistSurveyResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ShowArtistSurveyResult data = gson.fromJson(text, ShowArtistSurveyResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String URL_SEARCH_ARTIST_SURVEY = MY_SERVER + "/search_artist_survey"; // 선호가수 조사 검색
    public Request searchArtistSurvey(Object tag,
                                    int mem_no,
                                      String artist_name,
                                    OnResultListener<ShowArtistSurveyResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .add("artist_name", artist_name+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_SEARCH_ARTIST_SURVEY)
                .post(body)
                .build();

        final NetworkResult<ShowArtistSurveyResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ShowArtistSurveyResult data = gson.fromJson(text, ShowArtistSurveyResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String URL_SAVE_ARTIST_SURVEY = MY_SERVER + "/save_artist_survey"; // 선호가수 저장
    public Request saveArtistSurvey(Object tag,
                                      int mem_no,
                                      List<Artist> artist,
                                      OnResultListener<MySignUpResult> listener) {


        ArtistNo data = new ArtistNo();
        data.mem_no = mem_no;
        data.artist = artist;

//        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("mem_no", mem_no+"");
//
//        for(Artist a : artist) {
////            builder.add("artist",a+"");
//            builder.add("artist_no",a.getArtist_no()+"");
//            builder.add("artist_name",a.getName()+"");
//            builder.add("artist_img",a.getPhoto()+"");
//        }
//

        String json = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        Request request = new Request.Builder()
                .url(URL_SAVE_ARTIST_SURVEY)
                .post(body)
                .build();



        final NetworkResult<MySignUpResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    MySignUpResult data = gson.fromJson(text, MySignUpResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String URL_SHOW_FESTIVAL_RESULT = MY_SERVER + "/show_festival_result";       // 공연 검색했을 때
    public Request show_festival_result(Object tag,
                                      String festival_name,
                                      int mem_no,
                                      OnResultListener<FestivalResultResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("festival_name", festival_name)
                .add("mem_no", mem_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_SHOW_FESTIVAL_RESULT)
                .post(body)
                .build();

        final NetworkResult<FestivalResultResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    FestivalResultResult data = gson.fromJson(text, FestivalResultResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String URL_SHOW_WAITING_LIST = MY_SERVER + "/show_waiting_list";       // 모임 승인 대기 리스트 조회
    public Request show_waiting_list(Object tag,
                                     int mem_no,
                                     OnResultListener<ShowWaitingListResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_SHOW_WAITING_LIST)
                .post(body)
                .build();

        final NetworkResult<ShowWaitingListResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ShowWaitingListResult data = gson.fromJson(text, ShowWaitingListResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String URL_SHOW_GOING_LIST = MY_SERVER + "/show_going_list";       // 갈꺼야 등록한 공연 리스트 조회
    public Request show_going_list(Object tag,
                                     int mem_no,
                                     OnResultListener<ShowGoingListResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_SHOW_GOING_LIST)
                .post(body)
                .build();

        final NetworkResult<ShowGoingListResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ShowGoingListResult data = gson.fromJson(text, ShowGoingListResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String URL_SHOW_MINI_PROFILE = MY_SERVER + "/show_mini_profile";       // 햄버거바에서 프로필 조회
    public Request show_mini_profile(Object tag,
                                     int mem_no,
                                     OnResultListener<ShowMiniProfileResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_SHOW_MINI_PROFILE)
                .post(body)
                .build();

        final NetworkResult<ShowMiniProfileResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ShowMiniProfileResult data = gson.fromJson(text, ShowMiniProfileResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String URL_MODIFY_PROFILE = MY_SERVER + "/modify_profile";       // 프로필 수정
    public Request modify_profile(Object tag,
                                     int mem_no,
                                     File mem_img,
                                    String mem_name,
                                  String mem_state_msg,
                                  int mem_location,
                                     OnResultListener<ModifyProfileResult> listener) {



        MultipartBody.Builder builder = new MultipartBody.Builder();
        if(mem_img==null) {

            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("mem_no", mem_no + "");
            builder.addFormDataPart("mem_name", mem_name);
            builder.addFormDataPart("mem_state_msg", mem_state_msg);
            builder.addFormDataPart("mem_location", mem_location+"");

        }
        else{
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("mem_no", mem_no+"");
            builder.addFormDataPart("mem_name", mem_name);
            builder.addFormDataPart("mem_state_msg", mem_state_msg);
            builder.addFormDataPart("mem_location", mem_location+"");
            builder.addFormDataPart("mem_img", mem_img.getName(),
                    RequestBody.create(MediaType.parse("image/jpeg"), mem_img));
        }

        RequestBody body = builder.build();

        Request request = new Request.Builder()
                .url(URL_MODIFY_PROFILE)
                .post(body)
                .build();

        final NetworkResult<ModifyProfileResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ModifyProfileResult data = gson.fromJson(text, ModifyProfileResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String URL_SHOW_FESTIVAL_LIST = MY_SERVER + "/show_festival_list";       //  홈 공연 정보 리스트 조회
    public Request show_festival_list(Object tag,
                                  int mem_no,
                                  OnResultListener<FestivalResultResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .build();


        final Request request = new Request.Builder()
                .url(URL_SHOW_FESTIVAL_LIST)
                .post(body)
                .build();

        final NetworkResult<FestivalResultResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    FestivalResultResult data = gson.fromJson(text, FestivalResultResult.class);
                    if (data.success == 1) {
                       result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }




    private static final String URL_SHOW_FESTIVAL_DETAIL = MY_SERVER + "/show_festival_detail";       // 선택한 공연의 상세 정보를 보여줌
    public Request show_festival_detail(Object tag,
                                        int festival_no,
                                        int mem_no,
                                        OnResultListener<FestivalDetailResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("festival_no", festival_no+"")
                .add("mem_no", mem_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_SHOW_FESTIVAL_DETAIL)
                .post(body)
                .build();

        final NetworkResult<FestivalDetailResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    FestivalDetailResult data = gson.fromJson(text, FestivalDetailResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String URL_SHOW_FESTIVAL_LINEUPS = MY_SERVER + "/show_festival_lineups";       //  공연의 라인업 선택 화면을 보여줌
    public Request show_festival_lineups(Object tag,
                                         int festival_no,
                                         OnResultListener<ShowFestivalLineups> listener) {
        RequestBody body = new FormBody.Builder()
                .add("festival_no",festival_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_SHOW_FESTIVAL_LINEUPS)
                .post(body)
                .build();

        final NetworkResult<ShowFestivalLineups> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ShowFestivalLineups data = gson.fromJson(text, ShowFestivalLineups.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String URL_SHOW_MATCHING_RESULT = MY_SERVER + "/show_matching_result";       // 선택한 라인업으로 채팅방 매칭
    public Request show_matching_result(Object tag,
                                        int mem_no,
                                        int  festival_no,
                                        List<Artist> selected_artist,
                                                OnResultListener<ShowMatchingResult> listener) {

//        RequestBody body = new FormBody.Builder()
//                .add("mem_no", mem_no+"")
//                .add("festival_no", festival_no+"")
//                .build();



        FormBody.Builder builder = new FormBody.Builder();
        builder.add("mem_no", mem_no+"");
        builder.add("festival_no", festival_no+"");

        for(Artist a : selected_artist) {
            builder.add("selected_artist",a.getArtist_no()+"");
        }

//        JsonSelectedArtist data = new JsonSelectedArtist();
//        data.mem_no = mem_no;
//        data.festival_no = festival_no;
//        data.selected_artist = selected_artist;
//
//
//        String json = gson.toJson(data);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        RequestBody body = builder.build();

        Request request = new Request.Builder()
                .url(URL_SHOW_MATCHING_RESULT)
                .post(body)
                .build();

        final NetworkResult<ShowMatchingResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ShowMatchingResult data = gson.fromJson(text, ShowMatchingResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }



//    private static final String URL_SHOW_CHATROOM_DETAIL = MY_SERVER + "/show_chatroom_detail";       // 매칭된 채팅방의 상세 정보 조회
//    public Request show_chatroom_detail(Object tag,
//                                        int mem_no,
//                                        int  chatroom_no,
//                                        OnResultListener<ShowMatchingResult> listener) {
//        RequestBody body = new FormBody.Builder()
//                .add("mem_no", mem_no+"")
//                .add("room_no", chatroom_no+"")
//                .build();
//
//        Request request = new Request.Builder()
//                .url(URL_SHOW_CHATROOM_DETAIL)
//                .post(body)
//                .build();
//
//        final NetworkResult<ShowMatchingResult> result = new NetworkResult<>();
//        result.request = request;
//        result.listener = listener;
//        mClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                result.exception = e;
//                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String text = response.body().string();
//                    ShowMatchingResult data = gson.fromJson(text, ShowMatchingResult.class);
//                    if (data.success == 1) {
//                        result.result = data;
//                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
//                    } else {
//                        result.exception = new IOException(data.message);
//                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
//                    }
//
//                } else {
//                    result.exception = new IOException(response.message());
//                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
//                }
//            }
//        });
//        return request;
//    }

    private static final String URL_HATE = MY_SERVER + "/hate";       // 선택 회원 신고하기
    public Request hate(Object tag,
                                        int selected_mem_no,
                                        OnResultListener<HateResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("selected_mem_no", selected_mem_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_HATE)
                .post(body)
                .build();

        final NetworkResult<HateResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    HateResult data = gson.fromJson(text, HateResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String URL_SHOW_MEM_PROFILE = MY_SERVER + "/show_mem_profile";       // 선택 회원 프로필 정보 조회
    public Request show_mem_profile(Object tag,
                        int selected_mem_no,
                        OnResultListener<ShowMemProfileResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("selected_mem_no", selected_mem_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_SHOW_MEM_PROFILE)
                .post(body)
                .build();

        final NetworkResult<ShowMemProfileResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ShowMemProfileResult data = gson.fromJson(text, ShowMemProfileResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String URL_CHATROOM_MEM_LIST = MY_SERVER + "/chatroom_mem_list";       // 채팅방 참여/대기(host) 목록 보여줌
    public Request chatroom_mem_list(Object tag,
                                    int mem_no,
                                     int chatroom_no,
                                    OnResultListener<ChatroomMemListResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .add("chatroom_no", chatroom_no + "")
                .build();

        Request request = new Request.Builder()
                .url(URL_CHATROOM_MEM_LIST)
                .post(body)
                .build();

        final NetworkResult<ChatroomMemListResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ChatroomMemListResult data = gson.fromJson(text, ChatroomMemListResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String URL_CHATROOM_APPROVE = MY_SERVER + "/chatroom_approve";       // 채팅 대기자 승인
    public Request chatroom_approve(Object tag,
                                     int approved_mem_no,
                                     int chatroom_no,
                                     OnResultListener<ChatroomApproveResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("approved_mem_no", approved_mem_no+"")
                .add("chatroom_no", chatroom_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_CHATROOM_APPROVE)
                .post(body)
                .build();

        final NetworkResult<ChatroomApproveResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ChatroomApproveResult data = gson.fromJson(text, ChatroomApproveResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });

        return request;
    }

    private static final String URL_CHATROOM_KICK = MY_SERVER + "/chatroom_kick";       // 채팅방 강퇴
    public Request chatroom_kick(Object tag,
                                    int kicked_mem_no,
                                    int chatroom_no,
                                    OnResultListener<ChatroomKickResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("kicked_mem_no", kicked_mem_no+"")
                .add("chatroom_no", chatroom_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_CHATROOM_KICK)
                .post(body)
                .build();

        final NetworkResult<ChatroomKickResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ChatroomKickResult data = gson.fromJson(text, ChatroomKickResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }


    private static final String URL_REQUEST_CHATROOM_JOIN = MY_SERVER + "/request_chatroom_join";       // 모임 참여 신청
    public Request request_chatroom_join(Object tag,
                                 int mem_no,
                                 int chatroom_no,
                                         int mem_chatroom_state,
                                 OnResultListener<RequestChatroomJoinResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .add("chatroom_no", chatroom_no + "")
                .add("mem_chatroom_state", mem_chatroom_state+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_REQUEST_CHATROOM_JOIN)
                .post(body)
                .build();

        final NetworkResult<RequestChatroomJoinResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    RequestChatroomJoinResult data = gson.fromJson(text, RequestChatroomJoinResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String URL_DELETE_GOING_LIST = MY_SERVER + "/delete_going_list";       // 갈꺼야 공연 삭제
    public Request delete_going_list(Object tag,
                                         int mem_no,
                                         int festival_no,
                                         OnResultListener<DeleteGoingListResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no + "")
                .add("festival_no", festival_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_DELETE_GOING_LIST)
                .post(body)
                .build();

        final NetworkResult<DeleteGoingListResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    DeleteGoingListResult data = gson.fromJson(text, DeleteGoingListResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String URL_CHECK_GOING = MY_SERVER + "/check_going";       // 갈꺼야 버튼 선택
    public Request check_going(Object tag,
                                     int mem_no,
                                     int festival_no,
                               int mem_going_check,
                                     OnResultListener<CheckGoingResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .add("festival_no", festival_no + "")
                .add("mem_going_check", mem_going_check+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_CHECK_GOING)
                .post(body)
                .build();

        final NetworkResult<CheckGoingResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    CheckGoingResult data = gson.fromJson(text, CheckGoingResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String URL_CREATE_NEW_CHATROOM = MY_SERVER + "/create_new_chatroom";       // 새로운 채팅방을 만듦
    public Request create_new_chatroom(Object tag,
                               int mem_no,
                               int festival_no,
                               String chatroom_name,
                                       int chatroom_maxSize,
                                       int chatroom_location,
                                       int chatroom_age,
                                       File chatroom_img,
                                       int chatroom_bg,
                                       List<Artist> chatroom_lineup,
                               OnResultListener<CreateNewChatroomResult> listener) {


            MultipartBody.Builder builder = new MultipartBody.Builder();
        if(chatroom_bg==0) {
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("mem_no", mem_no + "");
            builder.addFormDataPart("festival_no", festival_no + "");
            builder.addFormDataPart("chatroom_name", chatroom_name);
            builder.addFormDataPart("chatroom_maxSize", chatroom_maxSize + "");
            builder.addFormDataPart("chatroom_location", chatroom_location + "");
            builder.addFormDataPart("chatroom_age", chatroom_age + "");
            builder.addFormDataPart("chatroom_bg", chatroom_bg + "");
            builder.addFormDataPart("chatroom_img", chatroom_img.getName(),
                    RequestBody.create(MediaType.parse("image/jpeg"), chatroom_img));

        }

        else{
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("mem_no", mem_no + "");
            builder.addFormDataPart("festival_no", festival_no + "");
            builder.addFormDataPart("chatroom_name", chatroom_name);
            builder.addFormDataPart("chatroom_maxSize", chatroom_maxSize + "");
            builder.addFormDataPart("chatroom_location", chatroom_location + "");
            builder.addFormDataPart("chatroom_age", chatroom_age + "");
            builder.addFormDataPart("chatroom_bg", chatroom_bg + "");

        }
        for(Artist a : chatroom_lineup) {
            builder.addFormDataPart("chatroom_lineup", a.getArtist_no()+"");
        }


        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(URL_CREATE_NEW_CHATROOM)
                .post(body)
                .build();
        final NetworkResult<CreateNewChatroomResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    CreateNewChatroomResult data = gson.fromJson(text, CreateNewChatroomResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    private static final String URL_CHATROOM_DISAPPROVE = MY_SERVER + "/chatroom_disapprove";       // 채팅방 거절
    public Request chatroom_disapprove(Object tag,
                                       int disapproved_mem_no,
                                       int chatroom_no,
                                       OnResultListener<ChatroomDisapproveResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("disapproved_mem_no", disapproved_mem_no+"")
                .add("chatroom_no", chatroom_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_CHATROOM_DISAPPROVE)
                .post(body)
                .build();

        final NetworkResult<ChatroomDisapproveResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ChatroomDisapproveResult data = gson.fromJson(text, ChatroomDisapproveResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });

        return request;
    }

    private static final String URL_SHOW_MY_PROFILE = MY_SERVER + "/show_my_profile";       // 프로필 조회
    public Request show_my_profile(Object tag,
                                       int mem_no,
                                       OnResultListener<ShowMyProfileResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no + "")
                .build();

        Request request = new Request.Builder()
                .url(URL_SHOW_MY_PROFILE)
                .post(body)
                .build();

        final NetworkResult<ShowMyProfileResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ShowMyProfileResult data = gson.fromJson(text, ShowMyProfileResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });

        return request;
    }


    private static final String URL_SHOW_MY_CHATROOM_LIST = MY_SERVER + "/show_my_chatroom_list";       // 메이트톡 리스트
    public Request show_my_chatroom_list(Object tag,
                                   int mem_no,
                                   OnResultListener<ShowMyChatroomListResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_SHOW_MY_CHATROOM_LIST)
                .post(body)
                .build();

        final NetworkResult<ShowMyChatroomListResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ShowMyChatroomListResult data = gson.fromJson(text, ShowMyChatroomListResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });

        return request;
    }


    private static final String URL_CHATROOM_SEND_MSG = MY_SERVER + "/chatroom_send_msg";

    public Request chatroom_send_msg(Object tag,
                                     int mem_no,
                                     int chatroom_no,
                                     String chat_content,
                                     int chat_sender_no,
                               OnResultListener<ChatroomSendMsgResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_no", "" + mem_no)
                .add("chatroom_no", "" + chatroom_no)
                .add("chat_content", chat_content)
                .add("chat_sender_no", "" + chat_sender_no)
                .build();

        Request request = new Request.Builder()
                .url(URL_CHATROOM_SEND_MSG)
                .post(body)
                .build();


        final NetworkResult<ChatroomSendMsgResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    ChatroomSendMsgResult data = gson.fromJson(text, ChatroomSendMsgResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });

        return request;
    }

    private static final String URL_LOGIN_FB = MY_SERVER + "/login_fb"; // 페이스북 로그인
    public Request login_fb(Object tag,
                          String mem_access_token,
                          String mem_registration_id,
                          OnResultListener<MySignInResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_access_token", mem_access_token)
                .add("mem_registration_id", mem_registration_id)
                .build();

        Request request = new Request.Builder()
                .url(URL_LOGIN_FB)
                .post(body)
                .build();

        final NetworkResult<MySignInResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    MySignInResult data = gson.fromJson(text, MySignInResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });

        return request;
    }

    private static final String URL_CREATE_NEW_PRIVATE_CHATROOM = MY_SERVER + "/create_new_private_chatroom"; // 새로운 채팅방을 만듦
    public Request create_new_private_chatroom(Object tag,
                            int mem_no,
                            int another_mem_no,
                            OnResultListener<CreateNewChatroomResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .add("another_mem_no", another_mem_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_CREATE_NEW_PRIVATE_CHATROOM)
                .post(body)
                .build();

        final NetworkResult<CreateNewChatroomResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    CreateNewChatroomResult data = gson.fromJson(text, CreateNewChatroomResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });

        return request;
    }

    private static final String URL_REQUEST_NEW_CHAT = MY_SERVER + "/request_new_chat"; //새로운 메세지 전송


    public Request request_new_chat(Object tag,
                                     int mem_no,
                                     int chatroom_no,
                                     String last_regdate,
                                     OnResultListener<RequestNewChatResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_no", "" + mem_no)
                .add("chatroom_no", "" + chatroom_no)
                .add("last_regdate", last_regdate)
                .build();

        Request request = new Request.Builder()
                .url(URL_REQUEST_NEW_CHAT)
                .post(body)
                .build();


        final NetworkResult<RequestNewChatResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    RequestNewChatResult data = gson.fromJson(text, RequestNewChatResult.class);
                    if (data.success == 1) {
                        result.result = data;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    } else {
                        result.exception = new IOException(data.message);
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                    }

                } else {
                    result.exception = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });

        return request;
    }

    @WorkerThread
    public RequestNewChatResult request_new_chat(Object tag,
                                                 int mem_no,
                                                 int chatroom_no,
                                                 String last_regdate) throws IOException {

        RequestBody body = new FormBody.Builder()
                .add("mem_no", "" + mem_no)
                .add("chatroom_no", "" + chatroom_no)
                .add("last_regdate", last_regdate)
                .build();

        Request request = new Request.Builder()
                .url(URL_REQUEST_NEW_CHAT)
                .post(body)
                .build();

        Response response = mClient.newCall(request).execute();

        if (response.isSuccessful()) {
            String text = response.body().string();
            RequestNewChatResult data = gson.fromJson(text, RequestNewChatResult.class);
            return data;

        } else {
            throw new IOException(response.message());
        }
    }


}
