package com.example.independentworkapp.Activity;

import static androidx.core.app.ActivityCompat.requestPermissions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.independentworkapp.Adapter.createdEventAdapter;
import com.example.independentworkapp.Adapter.joinedEventAdapter;
import com.example.independentworkapp.Adapter.joinedUserAdapter;
import com.example.independentworkapp.Models.CreatedEvent.GetCreatedEvent;
import com.example.independentworkapp.Models.JoinedUsers.JoinedUser;
import com.example.independentworkapp.Network.Apis;
import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinedUsers extends AppCompatActivity {

    String event_id;
    Dialog dialog;
    RecyclerView rv;
    joinedUserAdapter adapter;
    List<JoinedUser> UserData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_users);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        rv=findViewById(R.id.rv);
        event_id=getIntent().getStringExtra("event_id");
        LinearLayoutManager manager;
        adapter=new joinedUserAdapter(JoinedUsers.this,UserData);
        manager=new LinearLayoutManager(JoinedUsers.this);
        dialog = new Dialog(JoinedUsers.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        rv.setLayoutManager(manager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 10);
        }
        getUsers(event_id);
    }

    private void getUsers(String event_id) {
        dialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, Apis.JOINED_USER+"/"+event_id,
                new Response.Listener<String>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(String response) {
//                        Gson gsonp = new GsonBuilder().setPrettyPrinting().create();
//                        JsonParser jp = new JsonParser();
//                        JsonElement je = jp.parse(String.valueOf(response));
//                        String prettyJsonString = gsonp.toJson(je);
                        Log.e("anyText",response);
                        try {
                            dialog.dismiss();
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length()>0){
                                JoinedUser[] getUsers = gson.fromJson(String.valueOf(jsonArray), JoinedUser[].class);
                                UserData.addAll(Arrays.asList(getUsers));
                                rv.setAdapter(adapter);
                            }

                        } catch (Exception e) {
                            Log.e("anyText", "Error is : " + e);
                            dialog.dismiss();
                            e.printStackTrace();
                        }
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
                params.put("Authorization", "Bearer " + new SharedPrefs(JoinedUsers.this).getUserToken().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(JoinedUsers.this);
        requestQueue.add(stringRequest);
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

}