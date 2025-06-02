package com.example.sngnews.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedManager {

    private SharedPreferences sharedPreferences;

    private Gson gson;

    public SharedManager(Context baseContext){
        this.sharedPreferences = baseContext.getSharedPreferences("SnGNews", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public void logIn(){
        sharedPreferences.edit().putBoolean("isLogIn", true).apply();
    }

    public void logOut(){
        sharedPreferences.edit().putBoolean("isLogIn", false).apply();
    }

    public boolean isLogIn(){
        return sharedPreferences.getBoolean("isLogIn", false);
    }

}
