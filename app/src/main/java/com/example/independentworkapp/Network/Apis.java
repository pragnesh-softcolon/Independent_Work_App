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
    public final static String VIEW_ALL_EVENT=BASE_URL+Constant.VIEW_ALL_EVENT;
    public final static String VIEW_JOIN_EVENT=BASE_URL+Constant.VIEW_JOIN_EVENT;
    public final static String VIEW_CREATED_EVENT=BASE_URL+Constant.VIEW_CREATED_EVENT;
    public final static String APPLY_EVENT=BASE_URL+Constant.APPLY_EVENT;
    public final static String JOINED_USER=BASE_URL+Constant.JOINED_USER;
    public final static String UPDATE_PROFILE=BASE_URL+Constant.UPDATE_PROFILE;
    public static final String PAYMENT_BASE_URL="https://api.razorpay.com/";
    public static final String PAYMENT=PAYMENT_BASE_URL+Constant.PAYMENT;
    public final static String BASE_URL_IMAGE="http://192.168.29.24:3000/";
    //    Personal api
//    public final static String BASE_URL_IMAGE="http://192.168.162.87:3000/";

    public static final String IMAGE=BASE_URL_IMAGE+Constant.IMAGE;

}