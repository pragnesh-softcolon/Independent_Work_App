package com.example.independentworkapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.independentworkapp.Models.CreatedEvent.GetCreatedEvent;
import com.example.independentworkapp.Models.JoinedEvents.JoinEvent;
import com.example.independentworkapp.Network.Apis;
import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AppliedEventFragment extends Fragment {
    Dialog dialog;
    View view;
    List<JoinEvent> EventData = new ArrayList<>();
    RecyclerView rv;
    joinedEventAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        view =inflater.inflate(R.layout.fragment_applied_event, container, false);
        rv=view.findViewById(R.id.rv);
        LinearLayoutManager manager;
        adapter=new joinedEventAdapter(getContext(),EventData);
        manager=new LinearLayoutManager(getContext());
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        rv.setLayoutManager(manager);
        viewEvent();
        // Inflate the layout for this fragment
        return view;
    }

    private void viewEvent()
    {
        dialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, Apis.VIEW_JOIN_EVENT,
                new Response.Listener<String>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(String response) {
                        Log.e("anyText",response);
                        try {
                            JSONObject userDetails = new JSONObject(response);
                            JSONArray joinEventArray = userDetails.getJSONObject("userDetails").getJSONArray("join_event");
                            Log.e("anyText","array : "+joinEventArray);
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            if (joinEventArray.length()>0)
                            {
                                JoinEvent[] getEvents = gson.fromJson(joinEventArray.toString(), JoinEvent[].class);
                                EventData.addAll(Arrays.asList(getEvents));
                                rv.setAdapter(adapter);
                                dialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(getContext(), "No Join Event Found", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
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
                params.put("Authorization", "Bearer " + new SharedPrefs(getContext()).getUserToken().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}