package com.example.independentworkapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.widget.NestedScrollView;
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
import com.example.independentworkapp.Adapter.allEventAdapter;
import com.example.independentworkapp.Adapter.createdEventAdapter;
import com.example.independentworkapp.Models.AllEvents.GetAllEvent;
import com.example.independentworkapp.Models.CreatedEvent.GetCreatedEvent;
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

public class PostedEventFragment extends Fragment {
    Dialog dialog;
    View view;
    int i = 0;
    NestedScrollView nestedSV;
    createdEventAdapter adapter;
    List<GetCreatedEvent> EventData = new ArrayList<>();
    RecyclerView rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        view =inflater.inflate(R.layout.fragment_posted_event, container, false);
        rv=view.findViewById(R.id.rv);
        LinearLayoutManager manager;
        adapter=new createdEventAdapter(getContext(),EventData);
        manager=new LinearLayoutManager(getContext());
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        rv.setLayoutManager(manager);
        viewEvents();
        return view;
    }

    private void viewEvents() {
        dialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, Apis.VIEW_CREATED_EVENT,
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
                            JSONArray jsonArrayEvent_data = new JSONArray(response);
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            if (jsonArrayEvent_data.length()>0)
                            {
                                GetCreatedEvent[] getEvents = gson.fromJson(response, GetCreatedEvent[].class);
                                EventData.addAll(Arrays.asList(getEvents));
                                rv.setAdapter(adapter);
//                                Toast.makeText(getContext(), "Event Created", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(getContext(), "No Event Created", Toast.LENGTH_SHORT).show();
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