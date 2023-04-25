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

    public void setUserId(String id) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("_id", id);
        editor.apply();
    }
    public String getUserId(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString("_id", "");
    }


    public void setFirstName(String FirstName) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("FirstName", FirstName);
        editor.apply();
    }
    public String getFirstName(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString("FirstName", "");
    }
    public void setLastName(String LastName) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("LastName", LastName);
        editor.apply();
    }
    public String getLasttName(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString("LastName", "");
    }
    public void setGender(String Gender) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Gender", Gender);
        editor.apply();
    }
    public String getGender(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString("Gender", "");
    }



    public void setDateOfBirth(String DateOfBirth) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("DateOfBirth", DateOfBirth);
        editor.apply();
    }
    public String getDateOfBirth(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString("DateOfBirth", "");
    }
    public void setPhone(String Phone) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Phone", Phone);
        editor.apply();
    }
    public String getPhone(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString("Phone", "");
    }


    public void setLocation(String Location) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Location", Location);
        editor.apply();
    }
    public String getLocation(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString("Location", "");
    }

    public void setImage(String Image) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Image", Image);
        editor.apply();
    }
    public String getImage(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString("Image", "");
    }

    //  get the PAYMENT ID
    public void setPaymentId(String Token) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("PaymentId", Token);
        editor.apply();
    }
    public String getPaymentId()
    {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString("PaymentId", "");
    }
    public void logoutApp()
    {
        SharedPreferences sharedpreferences = context.getSharedPreferences(
                APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear().apply();

    }
//    public void setTokenInDb(String token) {
//        boolean result=db.setToken(token);
//    }
//    public String getTokenFromDb(){
//        Cursor c=db.getToken();
//        int cnt=c.getCount();
//        if(cnt==0){
//           return null;
//        }
//        return c.getString(1);
//    }
}
