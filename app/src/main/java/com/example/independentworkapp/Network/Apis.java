package com.example.independentworkapp.Network;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.independentworkapp.Activity.login;

import org.json.JSONException;
import org.json.JSONObject;

public class Apis
{
//    Office api
    public final static String BASE_URL="http://192.168.29.24:3000/api/";
//    Personal api
//    public final static String BASE_URL="http://192.168.162.87:3000/api/";
    public final static String LOGIN=BASE_URL+Constant.LOGIN;
    public final static String SIGN_UP=BASE_URL+Constant.SIGN_UP;
    public final static String POST_EVENT=BASE_URL+Constant.POST_EVENT;
    public final static String APPLY_EVENT=BASE_URL+Constant.APPLY_EVENT;
    public final static String UPDATE_PROFILE=BASE_URL+Constant.UPDATE_PROFILE;
    public static final String PAYMENT_BASE_URL="https://api.razorpay.com/";
    public static final String PAYMENT=PAYMENT_BASE_URL+Constant.PAYMENT;
}





//    public void login() {
//        dialog.show();
//        final JSONObject jsonBody;
//        try {
//            jsonBody = new JSONObject("{\"phone\":\"6351621487\",\"password\":\"Pragnesh\"}");
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//        final JsonObjectRequest request = new JsonObjectRequest(Apis.LOGIN,jsonBody,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.e("anyText", "" + response);
//                        Toast.makeText(login.this, "" + response, Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
//                Log.e("anyText", "Volly error " + error);
//                dialog.dismiss();
//            }
//        });
//        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
