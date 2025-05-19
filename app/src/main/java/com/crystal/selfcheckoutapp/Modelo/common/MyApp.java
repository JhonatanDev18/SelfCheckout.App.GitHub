package com.crystal.selfcheckoutapp.Modelo.common;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import javax.crypto.SecretKey;

public class MyApp extends Application {
    private static MyApp instance;

    static {
        System.loadLibrary("bxl_common");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
