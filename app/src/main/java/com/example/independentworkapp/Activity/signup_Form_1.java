package com.example.independentworkapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.independentworkapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class signup_Form_1 extends AppCompatActivity {
    Button male,female,female2,btn_next;
    TextInputEditText Fname,Lname;
    TextInputLayout ed_first_name,ed_last_name;
    String FirstName,LastName, Gender;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form1);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        views();
        male.setBackground(getResources().getDrawable(R.drawable.buttone_style));
        male.setTextColor(getResources().getColor(R.color.white));
        female.setBackground(getResources().getDrawable(R.drawable.gender_inactive));
        female.setTextColor(getResources().getColor(R.color.blue));
        male.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view)
            {
                female2.setVisibility(View.GONE);
                male.setBackground(getResources().getDrawable(R.drawable.buttone_style));
                male.setTextColor(getResources().getColor(R.color.white));
                female.setBackground(getResources().getDrawable(R.drawable.gender_inactive));
                female.setTextColor(getResources().getColor(R.color.blue));
            }
        });
        female.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view)
            {
                female2.setVisibility(View.VISIBLE);
                male.setBackground(getResources().getDrawable(R.drawable.gender_inactive));
                male.setTextColor(getResources().getColor(R.color.blue));
                female.setBackground(getResources().getDrawable(R.drawable.buttone_style));
                female.setTextColor(getResources().getColor(R.color.white));
                female2.setBackground(getResources().getDrawable(R.drawable.buttone_style));
                female2.setTextColor(getResources().getColor(R.color.white));
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent next=new Intent(signup_Form_1.this,signup_Form_2.class);
                startActivity(next);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });
    }

    private void views() {
        Fname=findViewById(R.id.Fname);
        Lname=findViewById(R.id.Lname);
        male=findViewById(R.id.btn_gendar_male);
        female=findViewById(R.id.btn_gendar_female);
        female2=findViewById(R.id.btn_gendar_female2);
        btn_next=findViewById(R.id.btn_next);
        ed_first_name=findViewById(R.id.ed_first_name);
        ed_last_name=findViewById(R.id.ed_last_name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backLogin=new Intent(signup_Form_1.this,login.class);
        startActivity(backLogin);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}