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

import kotlin.text.Regex;

public class signup_Form_3 extends AppCompatActivity
{
    Button btn_next;
    TextInputEditText Password,ConformPassword;
    TextView error;
    String password,conformPassword;
    Regex sampleRegex = new Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,15}$");
    boolean isStrongPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form3);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        error=findViewById(R.id.error_password);
        btn_next=findViewById(R.id.btn_next);
        Password=findViewById(R.id.Password);
        ConformPassword=findViewById(R.id.ConformPassword);
        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                password=Password.getText().toString();
                conformPassword=ConformPassword.getText().toString();

                if (password.length()>=6 && conformPassword.length()>=6)
                {

                    if (password.equals(conformPassword))
                    {
////                        Regex sampleRegex = new Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,15}$");
//                        isStrongPassword= sampleRegex.containsMatchIn(conformPassword);
//                        if (isStrongPassword)
//                        {
                            Toast.makeText(signup_Form_3.this, "Account Successfully created", Toast.LENGTH_SHORT).show();
                            Intent next = new Intent(signup_Form_3.this, MainActivity.class);
                            startActivity(next);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                            finish();
//                        }
//                        else
//                        {
//                            Toast.makeText(signup_Form_3.this, "Required [0-9] [a-z] [A-Z] [Special-character]", Toast.LENGTH_LONG).show();
//                        }
                    } 
                    else 
                    {
                        Toast.makeText(signup_Form_3.this, "Passwords must be same", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(signup_Form_3.this, "Password must have attlist six character", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent form1=new Intent(signup_Form_3.this,signup_Form_2.class);
        startActivity(form1);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}