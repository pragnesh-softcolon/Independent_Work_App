package com.example.independentworkapp.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.independentworkapp.Fragment.HomeFragment;
import com.example.independentworkapp.MainActivity;
import com.example.independentworkapp.Network.Apis;
import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class login extends AppCompatActivity
{
    ScrollView layout;
    TextInputEditText phone,password;
    TextInputLayout ed_phone,ed_Password;
    TextView signup,forget_pass;
    Button btn_signIn;
    Dialog dialog;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        views();
        try {
            if (getIntent().getStringExtra("register").equals("Register"))
            {
                dialog.show();
                phone.setText(getIntent().getStringExtra("phone"));
                password.setText(getIntent().getStringExtra("password"));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        login();
                    }
                }, 1000);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    ed_phone.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ed_Password.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_signIn.setOnClickListener(view -> {
            if (Objects.requireNonNull(phone.getText()).toString().trim().isEmpty() || phone.getText().toString().trim().length()<10)
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

                login();

            }
        });
        signup.setOnClickListener(view -> {
            Intent form1=new Intent(login.this,signup_Form_1.class);
            startActivity(form1);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            finish();
        });
        forget_pass.setOnClickListener(view -> {
            Intent form1=new Intent(login.this,forgotPassword.class);
            startActivity(form1);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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
        layout=findViewById(R.id.layout);
        forget_pass=findViewById(R.id.forget_pass);
    }
    public void login() {
        dialog.show();
        Map<String,String> params = new HashMap<>();
        params.put("phone",phone.getText().toString().trim());
        params.put("password",password.getText().toString());
        final JsonObjectRequest request = new JsonObjectRequest(Apis.LOGIN,new JSONObject(params),
                response -> {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JsonParser jp = new JsonParser();
                    JsonElement je = jp.parse(String.valueOf(response));
                    String prettyJsonString = gson.toJson(je);
                    Log.e("anyText",prettyJsonString);
                    try {
                        if (response.getString("message").equals("User does not exist"))
                        {
                            dialog.dismiss();
                            ed_phone.setError(" ");
                            phone.requestFocus();
                            showError("Phone Number does not exist");
                        }
                        else if (response.getString("message").equals("Invalid password"))
                        {
                            dialog.dismiss();
                            ed_phone.setError(null);
                            ed_Password.setError(" ");
                            password.requestFocus();
                            showError("Invalid Password");
                        }
                        else if (response.getString("message").equals("User Login Successfully"))
                        {
                            dialog.dismiss();
                            ed_phone.setError(null);
                            ed_Password.setError(null);
                            new SharedPrefs(login.this).setUserToken(response.getString("token"));

                            JSONObject jsonObject = new JSONObject(response.getString("user"));
                            new SharedPrefs(login.this).setUserId(jsonObject.getString("_id"));
                            new SharedPrefs(login.this).setFirstName(jsonObject.getString("firstName"));
                            new SharedPrefs(login.this).setLastName(jsonObject.getString("lastName"));
                            new SharedPrefs(login.this).setGender(jsonObject.getString("gender"));
                            new SharedPrefs(login.this).setDateOfBirth(jsonObject.getString("dateOfBirth"));
                            new SharedPrefs(login.this).setImage(jsonObject.getString("image"));
                            new SharedPrefs(login.this).setPhone(jsonObject.getString("phone"));
                            new SharedPrefs(login.this).setLocation(jsonObject.getString("Location"));
                            Log.e("anyText505",new SharedPrefs(login.this).getUserId());
                            Log.e("anyText505",new SharedPrefs(login.this).getFirstName());
                            Log.e("anyText505",new SharedPrefs(login.this).getLasttName());
                            Log.e("anyText505",new SharedPrefs(login.this).getGender());
                            Log.e("anyText505",new SharedPrefs(login.this).getDateOfBirth());
                            Log.e("anyText505",new SharedPrefs(login.this).getImage());
                            Log.e("anyText505",new SharedPrefs(login.this).getPhone());
                            Log.e("anyText505",new SharedPrefs(login.this).getLocation());

                                if (new SharedPrefs(login.this).getUserToken().length()>5) {
                                    subscribe();
                                    Intent i = new Intent(login.this, MainActivity.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    finish();
                                }
//                                Toast.makeText(login.this, new SharedPrefs(login.this).getUserToken(), Toast.LENGTH_SHORT).show();
                        }
//                            Toast.makeText(login.this, "" + response, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        dialog.dismiss();
                        showError("Something Went Wrong,Try After Some time");
//                            Toast.makeText(login.this, "Something Went Wrong,Try After Some time", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError("Something Went Wrong,Try After Some time");
//                Toast.makeText(login.this, "Something Went Wrong,Try After Some time", Toast.LENGTH_SHORT).show();
                Log.e("anyText", "Volly error " + error);
                dialog.dismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void showError(String message)
    {
        Snackbar snackbar = Snackbar.make(layout,message,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
    private void subscribe()
    {
//        weather1
        FirebaseMessaging.getInstance().subscribeToTopic("IndependentWorkApp")
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
//                            Toast.makeText(login.this, msg, Toast.LENGTH_SHORT).show();
                            msg = "Subscribe failed";
                        }
                        else
                        {
//                            Toast.makeText(login.this, msg, Toast.LENGTH_SHORT).show();
                            msg="subscribe done";
                        }
                        Log.d("TAG", msg);
//                        Toast.makeText(NumberVerification.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}