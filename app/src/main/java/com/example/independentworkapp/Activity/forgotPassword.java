package com.example.independentworkapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.independentworkapp.MainActivity;
import com.example.independentworkapp.Network.Apis;
import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class forgotPassword extends AppCompatActivity {
    private EditText digit1;
    private EditText digit2;
    private EditText digit3;
    private EditText digit4;
    private EditText digit5;
    private EditText digit6;
    TextView signIn;
    String phone;
    String token;
    Boolean isOTPGenerated=false;
    private static final String CHANNEL_ID1 = "INDEPENDENT WORK APP";
    private static final int NOTIFICATION_ID1 = 101;
    Button request_otp;
    Dialog dialog;
    TextInputLayout ed_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ed_phone=findViewById(R.id.ed_phone);
        digit1 = findViewById(R.id.digit1);
        digit2 = findViewById(R.id.digit2);
        digit3 = findViewById(R.id.digit3);
        digit4 = findViewById(R.id.digit4);
        digit5 = findViewById(R.id.digit5);
        digit6 = findViewById(R.id.digit6);
        signIn=findViewById(R.id.go_signin);
        request_otp = findViewById(R.id.btn_sent);
        dialog = new Dialog(forgotPassword.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        digit1.addTextChangedListener(new PasscodeTextWatcher(digit1, digit2));
        digit2.addTextChangedListener(new PasscodeTextWatcher(digit2, digit3));
        digit3.addTextChangedListener(new PasscodeTextWatcher(digit3, digit4));
        digit4.addTextChangedListener(new PasscodeTextWatcher(digit4, digit5));
        digit5.addTextChangedListener(new PasscodeTextWatcher(digit5, digit6));
        digit6.addTextChangedListener(new PasscodeTextWatcher(digit6, null));
        PermissionGranted();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgotPassword.this, login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });
        request_otp.setOnClickListener(view ->{
            phone =ed_phone.getEditText().getText().toString().trim();
            if (phone.isEmpty()){
                ed_phone.setError("Enter Phone Number");
                ed_phone.getEditText().requestFocus();
            }
            else if(phone.length()!=10){
                ed_phone.setError("Enter Valid Phone Number");
                ed_phone.getEditText().requestFocus();
            }
            else{
                ed_phone.setError(null);
                generateOTP(phone);
            }
        });

        digit1.setOnKeyListener((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_DEL) {
                digit1.setText("");
                digit1.requestFocus();
            } else if (digit1.getText().length() == 1)
            {
                digit2.requestFocus();
            }
            return false;
        });

        digit2.setOnKeyListener((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_DEL) {
                if (digit2.getText().length() == 0) {
//                        digit1.setText("");
                    digit1.requestFocus();
                } else {
                    digit2.setText("");
                    digit2.requestFocus();
                }
            } else if (digit2.getText().length() == 1) {
                digit3.requestFocus();
            }
            return false;
        });

        digit3.setOnKeyListener((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_DEL)
            {
                if (digit3.getText().length() == 0) {
//                        digit2.setText("");
                    digit2.requestFocus();
                } else
                {
                    digit3.setText("");
                    digit3.requestFocus();
                }
            } else if (digit3.getText().length() == 1) {
                digit4.requestFocus();
            }
            return false;
        });

        digit4.setOnKeyListener((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_DEL)
            {
                if (digit4.getText().length() == 0) {
//                        digit3.setText("");
                    digit3.requestFocus();
                } else
                {
                    digit4.setText("");
                    digit4.requestFocus();
                }
            } else if (digit4.getText().length() == 1) {
                digit5.requestFocus();
            }
            return false;
        });
        digit5.setOnKeyListener((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_DEL)
            {
                if (digit5.getText().length() == 0) {
//                        digit3.setText("");
                    digit4.requestFocus();
                } else
                {
                    digit5.setText("");
                    digit5.requestFocus();
                }
            } else if (digit5.getText().length() == 1) {
                digit6.requestFocus();
            }
            return false;
        });
        digit6.setOnKeyListener((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_DEL)
            {
                if (digit6.getText().length() == 0) {
//                        digit3.setText("");
                    digit5.requestFocus();
                } else
                {
                    digit6.setText("");
                    digit6.requestFocus();
                }
            } else if (digit6.getText().length() == 1) {
                digit6.requestFocus();
            }
            return false;
        });

    }

    private void generateOTP(String phone) {
        dialog.show();
        Map<String,String> params = new HashMap<>();
        params.put("phone",phone);
        final JsonObjectRequest request = new JsonObjectRequest(Apis.REQUEST_OTP,new JSONObject(params),
                response -> {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JsonParser jp = new JsonParser();
                    JsonElement je = jp.parse(String.valueOf(response));
                    String prettyJsonString = gson.toJson(je);
                    Log.e("anyText",prettyJsonString);
                    dialog.dismiss();
                    try {
                        String otp = response.getString("otp");
                        Notify(otp);
                        token = response.getString("access_token");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                showError("Something Went Wrong,Try After Some time");
                Toast.makeText(forgotPassword.this, "Something Went Wrong,Try After Some time", Toast.LENGTH_SHORT).show();
                Log.e("anyText", "Volly error " + error);
                dialog.dismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private class PasscodeTextWatcher implements TextWatcher {

        private View currentView;
        private View nextView;

        PasscodeTextWatcher(View currentView, View nextView) {
            this.currentView = currentView;
            this.nextView = nextView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // This method is called before the text is changed
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 1) {
                if (nextView != null) {
                    nextView.requestFocus();
                } else {
                    if (isOTPGenerated) {
                        verifyOTP(digit1.getText().toString() +
                                digit2.getText().toString() +
                                digit3.getText().toString() +
                                digit4.getText().toString() +
                                digit5.getText().toString() +
                                digit6.getText().toString());
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // This method is called after the text is changed
        }

    }
    private void verifyOTP(String otp) {
        dialog.show();
        Map<String,String> params = new HashMap<>();
        params.put("otp",otp);
        params.put("phone",ed_phone.getEditText().getText().toString().trim());
        final JsonObjectRequest request = new JsonObjectRequest(Apis.VERIFY_OTP,new JSONObject(params),
                response -> {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JsonParser jp = new JsonParser();
                    JsonElement je = jp.parse(String.valueOf(response));
                    String prettyJsonString = gson.toJson(je);
                    Log.e("anyText",prettyJsonString);
                    dialog.dismiss();
                    try {
                        String token = response.getString("access_token");
                        Toast.makeText(this, "OTP Verified", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(forgotPassword.this,changePassword.class);
                        i.putExtra("token",token);
                        i.putExtra("phone",phone);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        finish();
                    } catch (JSONException e) {
                        Toast.makeText(forgotPassword.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }
                }, error -> {
    //                showError("Something Went Wrong,Try After Some time");
                    Toast.makeText(forgotPassword.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    Log.e("anyText", "Volly error " + error);
                    dialog.dismiss();
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " +token);
                return params;
            }
        };;
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void Notify(String otp)
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(getApplicationContext(),
                CHANNEL_ID1)
                .setSmallIcon(R.drawable.img)
                .setAutoCancel(true)
                .setContentText("Your OTP is : "+otp)
                .setSubText("New OTP")
                .setVibrate(new long[] { 1000, 1000, 1000,
                        1000, 1000 });
        notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID1,"INDEPENDENT WORK APP",NotificationManager.IMPORTANCE_HIGH));
        notificationManager.notify(NOTIFICATION_ID1,builder.build());
        isOTPGenerated=true;
    }

    public void PermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkSelfPermission("android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED)
            {

            }
            else
            {
                ActivityCompat.requestPermissions(forgotPassword.this, new String[]{"android.permission.POST_NOTIFICATIONS"}, 1);
            }
        }
        else
        {
            Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}