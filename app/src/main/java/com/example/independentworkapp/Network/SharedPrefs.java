package com.example.independentworkapp.Network;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs
{
    private Context context;
    private String APP_NAME= "INDEPENDENT_WORK_APP";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor prefsEditor;
    public SharedPrefs(Context context)
    {
        this.context = context;
        mSharedPreferences = context.getSharedPreferences(APP_NAME,
                Context.MODE_PRIVATE);
        prefsEditor = mSharedPreferences.edit();
    }
    //  get the access token of user
    public void setUserToken(String Token) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("User_Token", Token);
        editor.apply();
    }
    public String getUserToken()
    {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString("User_Token", "");

    }
    //  get the PAYMENT ID
    public void setPaymentId(String Token) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("User_Token", Token);
        editor.apply();
    }
    public String getPaymentId()
    {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString("User_Token", "");
    }
}
