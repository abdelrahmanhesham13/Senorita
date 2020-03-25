package com.senoritasaudi.storeutils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.senoritasaudi.models.UserModel;

public class StoreManager {

    private SharedPreferences mSharedPreferences;

    public StoreManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean getBooleanValueForKey(String key , boolean defaultValue) {
        return mSharedPreferences.getBoolean(key,defaultValue);
    }

    public void setBooleanValueForKey(String key , boolean value) {
        mSharedPreferences.edit().putBoolean(key,value).apply();
    }

    public void saveUser(UserModel userModel) {
        Gson gson = new Gson();
        mSharedPreferences.edit().putString("user",gson.toJson(userModel)).apply();
    }

    public void removeUser() {
        mSharedPreferences.edit().remove("user").apply();
    }

    public boolean containsUser() {
        return !mSharedPreferences.getString("user", "").equals("");
    }

    public void saveToken(String token) {
        mSharedPreferences.edit().putString("token",token).apply();
    }

    public String getToken() {
        return mSharedPreferences.getString("token","");
    }

    public UserModel getUser() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("user", "");
        return gson.fromJson(json, UserModel.class);
    }
}
