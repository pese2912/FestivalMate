package com.festival.tacademy.festivalmate.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.Data.FestivalDetailResult;
import com.festival.tacademy.festivalmate.Data.FestivalResultResult;
import com.festival.tacademy.festivalmate.Data.HateResult;
import com.festival.tacademy.festivalmate.Data.MySignInResult;
import com.festival.tacademy.festivalmate.Data.MySignUpResult;
import com.festival.tacademy.festivalmate.Data.ShowArtistSurveyResult;
import com.festival.tacademy.festivalmate.Data.ShowFestivalLineups;
import com.festival.tacademy.festivalmate.Data.ShowGoingListResult;
import com.festival.tacademy.festivalmate.Data.ShowMatchingResult;
import com.festival.tacademy.festivalmate.Data.ShowMiniProfileResult;
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

    private static final String MY_SERVER = "http://52.79.159.248:3000";  //회원가입
    private static final String URL_SIGN_UP = MY_SERVER + "/signup";
    public Request signup(Object tag, String mem_name,
                          String mem_id,
                          String mem_pwd,
                          String mem_img,
                          OnResultListener<MySignUpResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_name", mem_name)
                .add("mem_pwd", mem_pwd)
                .add("mem_id", mem_id)
                .add("mem_img",mem_img)
                .build();

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
                          OnResultListener<MySignInResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("mem_id", mem_id)
                .add("mem_pwd", mem_pwd)
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
                                      List<Artist> artist_no,
                                      OnResultListener<MySignUpResult> listener) {

        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .add("artist_no[0]",artist_no.get(0).getArtist_no()+"")
                .add("artist_no[1]",artist_no.get(1).getArtist_no()+"")
                .add("artist_no[2]",artist_no.get(2).getArtist_no()+"")
                .add("artist_no[3]",artist_no.get(3).getArtist_no()+"")
                .add("artist_no[4]",artist_no.get(4).getArtist_no()+"")
                .add("artist_no[5]",artist_no.get(5).getArtist_no()+"")
                .add("artist_no[6]",artist_no.get(6).getArtist_no()+"")
                .add("artist_no[7]",artist_no.get(7).getArtist_no()+"")
                .add("artist_no[8]",artist_no.get(8).getArtist_no()+"")
                .add("artist_no[9]",artist_no.get(9).getArtist_no()+"")
                .build();


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
                                     String mem_img,
                                    String mem_name,
                                  String mem_state_msg,
                                     OnResultListener<ShowMiniProfileResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .add("mem_name", mem_name)
                .add("mem_state_msg", mem_state_msg)
                .build();

        Request request = new Request.Builder()
                .url(URL_MODIFY_PROFILE)
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

    private static final String URL_SHOW_FESTIVAL_LIST = MY_SERVER + "/show_festival_list";       //  홈 공연 정보 리스트 조회
    public Request show_festival_list(Object tag,
                                  int mem_no,
                                  OnResultListener<FestivalResultResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .build();

        Request request = new Request.Builder()
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
        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .add("festival_no", festival_no+"")
                .build();

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



    private static final String URL_SHOW_CHATROOM_DETAIL = MY_SERVER + "/show_chatroom_detail";       // 매칭된 채팅방의 상세 정보 조회
    public Request show_chatroom_detail(Object tag,
                                        int mem_no,
                                        int  chatroom_no,
                                        OnResultListener<ShowMatchingResult> listener) {
        RequestBody body = new FormBody.Builder()
                .add("mem_no", mem_no+"")
                .add("room_no", chatroom_no+"")
                .build();

        Request request = new Request.Builder()
                .url(URL_SHOW_CHATROOM_DETAIL)
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

}
