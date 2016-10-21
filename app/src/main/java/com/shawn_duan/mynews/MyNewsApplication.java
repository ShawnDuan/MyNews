package com.shawn_duan.mynews;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by sduan on 10/21/16.
 */

public class MyNewsApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
