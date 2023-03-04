package com.example.independentworkapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.independentworkapp.Network.Apis;
import com.example.independentworkapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import kotlin.text.Regex;

public class signup_Form_3 extends AppCompatActivity
{
    Button btn_next;
    TextInputEditText Password,ConformPassword;
    TextInputLayout ed_paassword,ed_conformPassword;
    String password,conformPassword;
    String Phone,BirthDate,Place,imagePath;
    String FirstName,LastName, Gender="MALE";
    Bitmap bitmap;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form3);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        views();
        FirstName = getIntent().getStringExtra("FirstName");
        LastName = getIntent().getStringExtra("LastName");
        Gender=getIntent().getStringExtra("Gender");
        Phone = getIntent().getStringExtra("Phone");
        BirthDate = getIntent().getStringExtra("BirthDate");
        Place = getIntent().getStringExtra("Place");
        imagePath = getIntent().getStringExtra("imagePath");
        Log.e("anyText",FirstName);
        Log.e("anyText",LastName);
        Log.e("anyText",Gender);
        Log.e("anyText",Phone);
        Log.e("anyText",BirthDate);
        Log.e("anyText",Place);
        Log.e("anyText",imagePath);
        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ed_paassword.setError(null);
                ed_conformPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ConformPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ed_paassword.setError(null);
                ed_conformPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                password=Password.getText().toString().trim();
                conformPassword=ConformPassword.getText().toString().trim();

                if (password.length()>=6 && conformPassword.length()>=6)
                {

                    if (password.equals(conformPassword))
                    {
                        Register();
                    } 
                    else 
                    {
                        ed_paassword.setError("Password does not match");
                        ed_conformPassword.setError("Password does not match");
                    }
                }
                else
                {
                    ed_paassword.setError("Password must be at least 6 characters");
                    ed_conformPassword.setError("Password must be at least 6 characters");
                }
            }
        });
    }

    private void views() {

        ed_paassword=findViewById(R.id.ed_paassword);
        ed_conformPassword=findViewById(R.id.ed_conformPassword);
        btn_next=findViewById(R.id.btn_next);
        Password=findViewById(R.id.Password);
        ConformPassword=findViewById(R.id.ConformPassword);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
    }

    private void Register()
    {
        dialog.show();
        File f = new File(imagePath);
        bitmap= BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                Apis.SIGN_UP+
                        "?firstName="+FirstName+"&lastName="+LastName+"&gender="+Gender+
                        "&dateOfBirth="+BirthDate+"&phone="+Phone+"&Location="+Place+"&password="+conformPassword,
                new Response.Listener<NetworkResponse>()
                {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try
                        {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Log.e("anyText","obj is "+obj);
                            dialog.dismiss();
                            Intent next = new Intent(signup_Form_3.this, login.class);
                            next.putExtra("phone",Phone);
                            next.putExtra("password",conformPassword);
                            next.putExtra("register","Register");
                            startActivity(next);
                            finish();
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                            Log.e("anyText","catch : "+e);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_LONG).show();
                        Log.e("anyText",""+error);
                        dialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, DataPart> getByteData()
            {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image", new DataPart(f.getName(), imageBytes));
                Log.e("anyText",""+params);
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
//        Intent form1=new Intent(signup_Form_3.this,signup_Form_2.class);
//        startActivity(form1);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}