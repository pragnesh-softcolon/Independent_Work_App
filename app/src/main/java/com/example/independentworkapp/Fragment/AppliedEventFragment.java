package com.example.independentworkapp.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.independentworkapp.Activity.EventDetail;
import com.example.independentworkapp.Adapter.joinedEventAdapter;
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
    AlertDialog.Builder alert = null;
    Boolean isBack;
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
        isBack=false;
        rv.setLayoutManager(manager);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                alert = new AlertDialog.Builder(getContext());
                alert.setTitle("INDEPENDENT WORK APP");
                alert.setMessage("Are you sure you want to leave this event?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (position >=0) {
                            leaveEvent(EventData.get(position).getId(),position);
                        }
                        if (EventData.isEmpty()){

                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.show();
            }
        }).attachToRecyclerView(rv);
        viewEvent();
        // Inflate the layout for this fragment
        return view;
    }
    private void leaveEvent(String id, int position) {
        dialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Apis.LEAVE_EVENT+"/"+id,
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
                                EventData.remove(position);
                                adapter.notifyDataSetChanged();
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
                params.put("Authorization", "Bearer " + new SharedPrefs(getContext()).getUserToken().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
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
    @Override
    public void onPause() {
        super.onPause();
        isBack = true;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (isBack) //Do something
        {
            EventData.clear();
            viewEvent();
            EventData.clear();
        }
    }
}