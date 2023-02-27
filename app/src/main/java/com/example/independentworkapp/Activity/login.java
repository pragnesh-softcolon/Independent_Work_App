package com.example.independentworkapp.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.independentworkapp.MainActivity;
import com.example.independentworkapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class login extends AppCompatActivity
{
    TextInputEditText phone,password;
    TextInputLayout ed_phone,ed_Password;
    TextView signup;
    Button btn_signIn;
    Dialog dialog;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        views();
        btn_signIn.setOnClickListener(view -> {
            if (Objects.requireNonNull(phone.getText()).toString().isEmpty() || phone.getText().toString().length()<10)
            {
                ed_phone.setError("Please enter a valid phone number");
                phone.requestFocus();
            }
            else if(Objects.requireNonNull(password.getText()).toString().isEmpty() || password.getText().toString().length()<6)
            {
                ed_phone.setError(null);
                ed_Password.setError("Please enter a valid password");
                password.requestFocus();
            }
            else
            {
                ed_phone.setError(null);
                ed_Password.setError(null);
                dialog.show();
                Handler hdlr=new Handler();
        new Thread(new Runnable()
        {
            public void run()
            {
                while (i < 100)
                {
                    i += 2;
                    hdlr.post(new Runnable()
                    {
                        public void run()
                        {
                            if(i==30)
                            {
                                dialog.dismiss();
                                Intent mainActivity = new Intent(login.this, MainActivity.class);
                                startActivity(mainActivity);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();
                            }
                        }
                    });
                    try
                    {
                        Thread.sleep(200);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

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
        ed_Password=findViewById(R.id.ed_Password);
        ed_phone=findViewById(R.id.ed_phone);
        btn_signIn=findViewById(R.id.btn_signIn);
        signup=findViewById(R.id.signup);
        phone=findViewById(R.id.Phone);
        password=findViewById(R.id.Password);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
    }
}