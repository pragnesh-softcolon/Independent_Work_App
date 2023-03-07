package com.example.independentworkapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.independentworkapp.MainActivity;
import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (new SharedPrefs(splashScreen.this).getUserToken().length() > 0) {
            Intent intent = new Intent(splashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(splashScreen.this, login.class);
            startActivity(intent);
            finish();
        }
    }
}