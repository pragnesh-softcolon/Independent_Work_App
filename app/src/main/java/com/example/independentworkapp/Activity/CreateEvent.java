package com.example.independentworkapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.independentworkapp.Extra.DateComparator;
import com.example.independentworkapp.MainActivity;
import com.example.independentworkapp.Network.Apis;
import com.example.independentworkapp.Network.Constant;
import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateEvent extends AppCompatActivity
{
    TextInputEditText eventName,eventWork,payment,members,timeing,startDate,endDate,location,mapLinkLocation,address,otherDetails;
    TextInputLayout ed_eventName,ed_eventWork,ed_payment,ed_members,ed_timeing,ed_location,ed_mapLocation,ed_address,ed_startDate,ed_endDate,ed_otherDetails;
    String EventName,EventWork,Payment,Members,Timeing,StartDate,EndDate,Location,MapLinkLocation,Address,OtherDetails,PayableAmount;
    Button btn_generate_invoice,btn_pay,btn_pay_status;
    LinearLayout invoiceLayout;
    TextView user_name,event_name,event_work,event_payment,event_members,event_address,event_date,event_other_details,payable_amount;
    SharedPrefs sp;
    Dialog dialog;
    ScrollView layout;
    String paymentId;

    Boolean isBack,isValid=true;
    int i=1;

    String referenceID;
    String currency = "INR";
    String acceptPartial = "true";
    String firstMinPartialAmount = "100";
    String expireBy = "1691097357";
    String description = "Test payment";
    String name = "Independent Work App";

    String sms = "true";
    String notifyEmail = "true";
    String reminderEnable = "true";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        views();
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        startDate.setOnClickListener(view -> {
            materialDateBuilder.setTitleText("SELECT EVENT START DATE");
            materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPositiveButtonClick(Object selection) {
                String Rdate=materialDatePicker.getHeaderText();
                java.util.Date date1=new Date(Rdate);
                ZoneId z = ZoneId.of("Asia/Kolkata");
                String fdate=""+date1.toInstant().atZone(z);
                String reach_date = fdate.substring(0,10);
                String st="";
                ArrayList list =new ArrayList();
                String[] strSplit = reach_date.split("-");
                for (int j=0;j<strSplit.length;j++)
                {
                    st=strSplit[j];
                    list.add(st);
                }
                String Date=list.get(2)+"/"+list.get(1)+"/"+list.get(0);
                startDate.setText(Date);
            }
        });
        MaterialDatePicker.Builder materialDateBuilder2 = MaterialDatePicker.Builder.datePicker();
        final MaterialDatePicker materialDatePicker2 = materialDateBuilder.build();
        endDate.setOnClickListener(view -> {
            materialDateBuilder2.setTitleText("SELECT EVENT END DATE");
            materialDatePicker2.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });
        materialDatePicker2.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPositiveButtonClick(Object selection) {
                String Rdate=materialDatePicker2.getHeaderText();
                java.util.Date date1=new Date(Rdate);
                ZoneId z = ZoneId.of("Asia/Kolkata");
                String fdate=""+date1.toInstant().atZone(z);
                String reach_date = fdate.substring(0,10);
                String st="";
                ArrayList list =new ArrayList();
                String[] strSplit = reach_date.split("-");
                for (int j=0;j<strSplit.length;j++)
                {
                    st=strSplit[j];
                    list.add(st);
                }
                String Date=list.get(2)+"/"+list.get(1)+"/"+list.get(0);
                endDate.setText(Date);
            }
        });
        btn_generate_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValid = true;
                EventName = eventName.getText().toString().trim();
                EventWork =eventWork.getText().toString().trim();
                Payment = payment.getText().toString().trim();
                Members = members.getText().toString().trim();
                Timeing = timeing.getText().toString().trim();
                StartDate = startDate.getText().toString().trim();
                EndDate =  endDate.getText().toString().trim();
                Location =  location.getText().toString().trim();
                MapLinkLocation = mapLinkLocation.getText().toString().trim();
                Address = address.getText().toString().trim();
                OtherDetails = otherDetails.getText().toString().trim();
                String inputDate = "08/03/2023"; // Replace with your desired date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(inputDate, formatter);
                ed_eventName.setError(null);
                ed_eventWork.setError(null);
                ed_payment.setError(null);
                ed_members.setError(null);
                ed_timeing.setError(null);
                ed_location.setError(null);
                ed_mapLocation.setError(null);
                ed_address.setError(null);
                ed_startDate.setError(null);
                ed_endDate.setError(null);
                ed_otherDetails.setError(null);
                if (EventName.equals("")) {
                    ed_eventName.setError("Required");
                  ;
                    isValid=false;
                }
               if(EventWork.equals("")){
                    ed_eventWork.setError("Required");

                   isValid=false;
                }
                if(Payment.equals("")){
                    ed_payment.setError("Required");

                    isValid=false;
                }
               if(Members.equals("") ){
                    ed_members.setError("Required");

                   isValid=false;
                }
                if(Timeing.equals("")){
                    ed_timeing.setError("Required");

                    isValid=false;
                }
                if(Location.equals("")){
                    ed_location.setError("Required");

                    isValid=false;
                }
                if(MapLinkLocation.equals("")){
                    ed_mapLocation.setError("Required");
                    isValid=false;
                }
                if(Address.equals("") ){
                    ed_address.setError("Required");

                    isValid=false;
                }
                DateComparator dateComparator = new DateComparator();
                if(StartDate.equals("") ){
                    ed_startDate.setError("Required");
                    isValid=false;
                }
                else {

                    if (dateComparator.date1(StartDate) == 0) {
                        ed_startDate.setError("Start Date Must Be Today's or Future-date");
                        isValid = false;
                    }
                }

                if(EndDate.equals("")){
                   ed_endDate.setError("Required");
                    isValid=false;
                }
                else {
                    if (dateComparator.date2(StartDate, EndDate) == 0) {
                        ed_startDate.setError("End Date Must Be Today's or Future-date");
                        isValid = false;
                    }
                }
                if(OtherDetails.equals("")){
                    ed_otherDetails.setError("Required");
                    isValid=false;
                }
                if(isValid)
                {

                    user_name.setText("Username : "+"Pragnesh");
                    event_name.setText("Event Name : "+EventName);
                    event_work.setText("Event Work: "+EventWork);
                    event_payment.setText("Payment : "+Payment);
                    event_members.setText("Total Members : "+Members);
                    event_address.setText("Address : "+Address);
                    event_date.setText("Date : "+StartDate +" to "+ EndDate);
                    event_other_details.setText("Other Details : "+OtherDetails);
                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate sDate = LocalDate.parse(StartDate, inputFormatter);
                    LocalDate eDate = LocalDate.parse(EndDate, inputFormatter);
                    // Calculate age based on birthdate
                    LocalDate today = LocalDate.now();
                    Period days = Period.between(sDate, eDate);
                    int total= (Integer.parseInt(Payment) * Integer.parseInt(Members) )* (days.getDays()+1);
                    Log.e("anyText",""+days.getDays()+1);
                    PayableAmount=""+((total*10)/100);
                    payable_amount.setText("Total Payable Amount : "+PayableAmount);
                    invoiceLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(PayableAmount)<=100000) {
                    referenceID = UUID.randomUUID().toString();
                    paymentRequest();
                }
            }
        });
        btn_pay_status.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPaymentStatus();
            }
        });
    }

    private void createEvent(String id)
    {
        Log.e("anyText","call");
        dialog.show();
        Map<String,String> params = new HashMap<>();
                params.put("name",eventName.getText().toString().trim());
                params.put("work",eventWork.getText().toString().trim());
                params.put("Location",location.getText().toString().trim());
                params.put("start_date",startDate.getText().toString().trim());
                params.put("end_date",endDate.getText().toString().trim());
                params.put("map_location",mapLinkLocation.getText().toString().trim());
                params.put("Address",address.getText().toString().trim());
                params.put("members",members.getText().toString().trim());
                params.put("description",otherDetails.getText().toString().trim());
                params.put("paymentID",id);
                params.put("time",timeing.getText().toString().trim());
                params.put("paymentPerDay",payment.getText().toString().trim());
                Log.e("anyText","perams is "+params);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,Apis.POST_EVENT,new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        JsonParser jp = new JsonParser();
                        JsonElement je = jp.parse(String.valueOf(response));
                        String prettyJsonString = gson.toJson(je);
                        Log.e("anyText",prettyJsonString);
                        Toast.makeText(getApplicationContext(),"Event Created Successfully",Toast.LENGTH_SHORT).show();
                        finish();
//                        activateEvent();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError("Something Went Wrong,Try After Some time");
//                Toast.makeText(login.this, "Something Went Wrong,Try After Some time", Toast.LENGTH_SHORT).show();
                Log.e("anyText", "Volly error " + error);
                error.printStackTrace();
                dialog.dismiss();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + new SharedPrefs(getApplicationContext()).getUserToken().toString());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void checkPaymentStatus() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Apis.PAYMENT+"/"+sp.getPaymentId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        JsonParser jp = new JsonParser();
                        JsonElement je = jp.parse(response);
                        String prettyJsonString = gson.toJson(je);
                        Log.e("anyText",prettyJsonString);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("payments");
                            if (jsonArray.length()>0) {
                                dialog.dismiss();
                                JSONObject jsonArrayObject = jsonArray.getJSONObject(0);
                                String status = jsonArrayObject.getString("status");
                                if (status.equals("captured")) {
                                    createEvent(paymentId);

                                }
//                                Toast.makeText(CreateEvent.this, "Payment captured.", Toast.LENGTH_SHORT).show();
                                Log.e("anyText", "array : " + status);
                                finish();
                            }
                            else {
                                dialog.dismiss();
                                Toast.makeText(CreateEvent.this, "Payment is not captured yet.", Toast.LENGTH_SHORT).show();
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
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constant.BASIC_AUTH);
                headers.put("Content-Type", "application/json");
                return headers;
            }



        };

// Add the request to the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }


    private void paymentRequest()
    {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Apis.PAYMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        JsonParser jp = new JsonParser();
                        JsonElement je = jp.parse(response);
                        String prettyJsonString = gson.toJson(je);
                        Log.e("anyText",prettyJsonString);
                        try {
                            dialog.dismiss();
                            i=2;
                            JSONObject jsonObject = new JSONObject(response);
                            Intent pay=new Intent(CreateEvent.this,payment.class);
                            pay.putExtra("paymentLink",jsonObject.getString("short_url"));
                            startActivity(pay);
                            paymentId=jsonObject.getString("id");
//                            createEvent(id);
                            sp.setPaymentId(paymentId);
                            Log.e("anyText",paymentId);
                        } catch (JSONException e) {
                            dialog.dismiss();
                            Toast.makeText(CreateEvent.this, "Something Went Wrong,Try After Some time",Toast.LENGTH_SHORT).show();
                            throw new RuntimeException(e);
                        }
//                        Toast.makeText(CreateEvent.this, "Payment Request Sent", Toast.LENGTH_SHORT).show();
                        btn_pay_status.setVisibility(View.VISIBLE);
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
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constant.BASIC_AUTH);
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String requestBody = "{\n" +
                        "  \"amount\": " + (Integer.parseInt(PayableAmount)*100) + ",\n" +
                        "  \"currency\": \"" + currency + "\",\n" +
                        "  \"accept_partial\": " + acceptPartial + ",\n" +
                        "  \"first_min_partial_amount\": " + firstMinPartialAmount + ",\n" +
                        "  \"expire_by\": " + expireBy + ",\n" +
                        "  \"reference_id\": \"" + referenceID + "\",\n" +
                        "  \"description\": \"" + description + "\",\n" +
                        "  \"customer\": {\n" +
                        "    \"name\": \"" + name + "\",\n" +
                        "    \"contact\": \"" + "+916351621487" + "\",\n" +
                        "    \"email\": \"" + "pragneshkoli84344@gmail.com" + "\"\n" +
                        "  },\n" +
                        "  \"notify\": {\n" +
                        "    \"sms\": " + sms + ",\n" +
                        "    \"email\": " + notifyEmail + "\n" +
                        "  },\n" +
                        "  \"reminder_enable\": " + reminderEnable + ",\n" +
                        "  \"notes\": {\n" +
                        "    \"policy_name\": \"" + EventName + "\"\n" +
                        "  }\n" +
                        "}";
                return requestBody.getBytes();
            }
        };

// Add the request to the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    private void views()
    {
        isBack=false;
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
        btn_generate_invoice=findViewById(R.id.btn_generate_invoice);
        user_name=findViewById(R.id.userName);
        event_name=findViewById(R.id.eventName);
        event_work=findViewById(R.id.eventWork);
        event_payment=findViewById(R.id.eventPayment);
        event_members=findViewById(R.id.eventMembers);
        event_address=findViewById(R.id.eventAddress);
        event_date=findViewById(R.id.eventDate);
        event_other_details=findViewById(R.id.eventOtherDetails);
        payable_amount=findViewById(R.id.payableAmount);
        btn_pay=findViewById(R.id.btn_pay);
        btn_pay_status=findViewById(R.id.btn_pay_status);
        invoiceLayout=findViewById(R.id.invoice_layout);
        ed_eventName=findViewById(R.id.ed_eventName);
        ed_eventWork=findViewById(R.id.ed_eventWork);
        ed_payment=findViewById(R.id.ed_payment);
        ed_members=findViewById(R.id.ed_members);
        ed_timeing=findViewById(R.id.ed_timeing);
        ed_location=findViewById(R.id.ed_location);
        ed_mapLocation=findViewById(R.id.ed_mapLocation);
        ed_address=findViewById(R.id.ed_address);
        ed_startDate=findViewById(R.id.ed_startDate);
        ed_endDate=findViewById(R.id.ed_endDate);
        ed_otherDetails=findViewById(R.id.ed_otherDetails);
        sp=new SharedPrefs(getApplicationContext());
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        layout=findViewById(R.id.layout);
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
    public void showError(String message)
    {
        Snackbar snackbar = Snackbar.make(layout,message,Snackbar.LENGTH_SHORT);
        snackbar.show();
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
            checkPaymentStatus();
            Toast.makeText(this, "payment status is on checking", Toast.LENGTH_SHORT).show();
        }
    }
}