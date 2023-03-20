package com.example.independentworkapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.independentworkapp.Network.Apis;
import com.example.independentworkapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class changePassword extends AppCompatActivity {

    Button btn_next;
    TextInputEditText Password,ConformPassword;
    TextInputLayout ed_paassword,ed_conformPassword;
    String password,conformPassword;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
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
                         changepassword();
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

    private void changepassword()
    {
        dialog.show();
        Map<String,String> params = new HashMap<>();
        params.put("password",conformPassword);
        params.put("phone",getIntent().getExtras().getString("phone"));
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,Apis.CHANGE_PASSWORD,new JSONObject(params),
                response -> {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JsonParser jp = new JsonParser();
                    JsonElement je = jp.parse(String.valueOf(response));
                    String prettyJsonString = gson.toJson(je);
                    Log.e("anyText",prettyJsonString);
                    dialog.dismiss();
                    try {
                        String message = response.getString("message");
                        Intent next = new Intent(changePassword.this, login.class);
                        next.putExtra("phone",getIntent().getExtras().getString("phone"));
                        next.putExtra("password",conformPassword);
                        next.putExtra("register","Register");
                        startActivity(next);
                        finish();

                    } catch (JSONException e) {
                        Toast.makeText(changePassword.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }
                }, error -> {
            //                showError("Something Went Wrong,Try After Some time");
            Toast.makeText(changePassword.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            Log.e("anyText", "Volly error " + error);
            dialog.dismiss();
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " +getIntent().getExtras().getString("token"));
                return params;
            }
        };;
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}