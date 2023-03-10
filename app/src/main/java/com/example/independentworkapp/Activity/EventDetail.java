package com.example.independentworkapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.independentworkapp.Extra.AgeCalculator;
import com.example.independentworkapp.Network.Apis;
import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EventDetail extends AppCompatActivity {
    TextInputEditText eventName,eventWork,payment,members,timeing,startDate,endDate,location,mapLinkLocation,address,otherDetails;
    Button btn;
    Dialog dialog;
    LinearLayout layout;
    String eventID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        eventID=getIntent().getStringExtra("id");
        views();
        setData();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn.getText().toString().equalsIgnoreCase("Join Event")) {
                    int age = new AgeCalculator().calculate(new SharedPrefs(EventDetail.this).getDateOfBirth());
                    if (age>14) {
                        joinEvent();
                    }
                    else{
                        showError("your age is not high enough");
                    }
                }
                if (btn.getText().toString().equalsIgnoreCase("Leave Event")) {
                    leaveEvent();
                }

            }
        });
    }

    private void leaveEvent() {
        dialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Apis.LEAVE_EVENT+"/"+eventID,
                new Response.Listener<String>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(String response) {
//                        Gson gsonp = new GsonBuilder().setPrettyPrinting().create();
//                        JsonParser jp = new JsonParser();
//                        JsonElement je = jp.parse(String.valueOf(response));
//                        String prettyJsonString = gsonp.toJson(je);
                        try{
                            JSONObject json = new JSONObject(response);
                            String message = json.getString("message");
                            int success = json.getInt("success");
                            if(success==1){
                                finish();
                            }
                            Log.e("anyText",response);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.d("anyText", String.valueOf(error));
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + new SharedPrefs(EventDetail.this).getUserToken().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(EventDetail.this);
        requestQueue.add(stringRequest);
    }

    private void joinEvent() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Apis.APPLY_EVENT+"/"+getIntent().getStringExtra("id") ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response
                        dialog.dismiss();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        JsonParser jp = new JsonParser();
                        JsonElement je = jp.parse(response);
                        String prettyJsonString = gson.toJson(je);
                        Log.e("anyText",prettyJsonString);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("message").equals("you are successfully join this event")){
                                showError("you are successfully join this event");
                                finish();
                            }
                            if(jsonObject.getString("message").equals("user already joined")){
                                showError("you have already joined");
                            }
                            if(jsonObject.getString("message").equals("Event is full")){
                                showError("Event is full");
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        dialog.dismiss();
                        Log.e("anyText",""+error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + new SharedPrefs(EventDetail.this).getUserToken().toString());
                return params;
            }
        };
        // Add the request to the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    private void setData() {
        eventName.setText(getIntent().getStringExtra("eventName"));
        eventWork.setText(getIntent().getStringExtra("eventWork"));
        payment.setText(getIntent().getStringExtra("payment"));
        members.setText(getIntent().getStringExtra("members"));
        timeing.setText(getIntent().getStringExtra("timeing"));
        location.setText(getIntent().getStringExtra("location"));
        mapLinkLocation.setText(getIntent().getStringExtra("mapLinkLocation"));
        address.setText(getIntent().getStringExtra("address"));
        startDate.setText(getIntent().getStringExtra("startDate"));
        endDate.setText(getIntent().getStringExtra("endDate"));
        otherDetails.setText(getIntent().getStringExtra("otherDetails"));
        if (getIntent().getStringExtra("activity").equals("joinedEvent")){
            btn.setText("Leave Event");
        }
        if (getIntent().getBooleanExtra("isJoined",false)){
            btn.setText("Event already joined");
        }
        else{
            btn.setText("Join Event");
        }
//        if (getIntent().getStringExtra("activity").equals("allEvent")){
//            btn.setText("Join Event");
//        }

    }

    private void views() {
        eventName=findViewById(R.id.event_name);
        eventWork=findViewById(R.id.event_work);
        payment=findViewById(R.id.event_payment);
        members=findViewById(R.id.event_members);
        timeing=findViewById(R.id.event_time);
        location=findViewById(R.id.event_Location);
        mapLinkLocation=findViewById(R.id.event_map_location);
        address=findViewById(R.id.event_address);
        startDate=findViewById(R.id.event_start_date);
        endDate=findViewById(R.id.event_end_date);
        otherDetails=findViewById(R.id.event_otherDetails);
        btn=findViewById(R.id.btn_apply);
        layout=findViewById(R.id.layout);
        dialog = new Dialog(EventDetail.this);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
    }
    public void showError(String message)
    {
        Snackbar snackbar = Snackbar.make(layout,message,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}