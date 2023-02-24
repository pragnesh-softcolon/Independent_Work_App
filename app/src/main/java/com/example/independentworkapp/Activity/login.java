package com.example.independentworkapp.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.independentworkapp.MainActivity;
import com.example.independentworkapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class login extends AppCompatActivity
{
    TextInputEditText phone,password;
    TextView signup,phone_error,password_error;
    Button btn_signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        views();
        btn_signIn.setOnClickListener(view -> {
            if (Objects.requireNonNull(phone.getText()).toString().isEmpty() || phone.getText().toString().length()<10)
            {
                phone_error.setVisibility(View.VISIBLE);
                phone.requestFocus();
            }
            else if(Objects.requireNonNull(password.getText()).toString().isEmpty() || password.getText().toString().length()<6)
            {
                phone_error.setVisibility(View.GONE);
                password_error.setVisibility(View.VISIBLE);
                password.requestFocus();
            }
            else
            {
                phone_error.setVisibility(View.GONE);
                password_error.setVisibility(View.GONE);
                Intent mainActivity = new Intent(login.this, MainActivity.class);
                startActivity(mainActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
        signup.setOnClickListener(view -> {
            Intent form1=new Intent(login.this,signup_Form_1.class);
            startActivity(form1);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            finish();
        });
    }

    private void views()
    {
        btn_signIn=findViewById(R.id.btn_signIn);
        signup=findViewById(R.id.signup);
        phone=findViewById(R.id.Phone);
        password=findViewById(R.id.Password);
        phone_error=findViewById(R.id.error_phone);
        password_error=findViewById(R.id.error_password);
    }
}