package com.example.sngnews.app;

import android.app.Application;
import android.content.Context;

import com.example.sngnews.managers.SharedManager;

public class App extends Application {
    public static SharedManager sharedManager;
    public static Context appContext;

    public void onCreate(){
        super.onCreate();
        appContext = getApplicationContext();
        sharedManager = new SharedManager(getBaseContext());
    }

}
