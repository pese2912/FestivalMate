package com.festival.tacademy.festivalmate;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tacademy on 2016-05-13.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public  static Context getContext(){
        return  context;
    }
}