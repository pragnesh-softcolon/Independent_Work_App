package com.example.independentworkapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.independentworkapp.Activity.CreateEvent;
import com.example.independentworkapp.Activity.EventDetail;
import com.example.independentworkapp.Adapter.allEventAdapter;
import com.example.independentworkapp.MainActivity;
import com.example.independentworkapp.Models.AllEvents.GetAllEvent;
import com.example.independentworkapp.Network.Apis;
import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment
{

    View view;
    RecyclerView rv;
    Dialog dialog;
    int limit=10,offset=0;
    NestedScrollView nestedSV;
    allEventAdapter adapter;
    List<GetAllEvent> EventData = new ArrayList<>();
    Boolean isBack;
    int i = 0;
    FloatingActionButton floatingActionButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        rv=view.findViewById(R.id.rv);
        floatingActionButton=view.findViewById(R.id.CreatePost);
        nestedSV = view.findViewById(R.id.idNestedSV);
        nestedSV.scrollTo(0, nestedSV.getBottom()-1);
        LinearLayoutManager manager;
        adapter=new allEventAdapter(getContext(),EventData);
        manager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        isBack=false;
        viewEvents(limit,offset);
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
        {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    offset = offset + 10;
                    viewEvents(limit,offset);
                }
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
             {
                Intent i=new Intent(getContext(), CreateEvent.class);
                startActivity(i);
            }
        });
        return view;
    }

    private void viewEvents( int limit  , int offset)
    {
//        dialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, Apis.VIEW_ALL_EVENT + "?limit=" + limit + "&offset=" + offset,
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
                                GetAllEvent[] getEvents = gson.fromJson(response, GetAllEvent[].class);
                                EventData.addAll(Arrays.asList(getEvents));
                                rv.setAdapter(adapter);
                                dialog.dismiss();
                            }
                            else
                            {
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