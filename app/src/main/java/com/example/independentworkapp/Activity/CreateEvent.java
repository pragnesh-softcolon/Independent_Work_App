package com.example.independentworkapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.independentworkapp.Network.Apis;
import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateEvent extends AppCompatActivity
{
    TextInputEditText eventName,eventWork,payment,members,timeing,startDate,endDate,location,mapLinkLocation,address,otherDetails;
    String EventName,EventWork,Payment,Members,Timeing,StartDate,EndDate,Location,MapLinkLocation,Address,OtherDetails,PayableAmount;
    Button btn_generate_invoice,btn_pay,btn_pay_status;
    LinearLayout invoiceLayout;
    TextView user_name,event_name,event_work,event_payment,event_members,event_address,event_date,event_other_details,payable_amount;
    SharedPrefs sp;


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
    String policyName = "BILL FROME PRAGNESH";
    String amount,email,phoneNumber;

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
                startDate.setText(materialDatePicker.getHeaderText());
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
                endDate.setText(materialDatePicker2.getHeaderText());
            }
        });
        btn_generate_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                if (EventName.equals("") ||
                        EventWork.equals("") ||
                        Payment.equals("") ||
                        Members.equals("") ||
                        Timeing.equals("") ||
                        Location.equals("") ||
                        MapLinkLocation.equals("") ||
                        Address.equals("") ||
                        StartDate.equals("") ||
                        EndDate.equals("")
                )
                {
                    Toast.makeText(CreateEvent.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    user_name.setText("Username : "+"Pragnesh");
                    event_name.setText("Event Name : "+EventName);
                    event_work.setText("Event Work: "+EventWork);
                    event_payment.setText("Payment : "+Payment);
                    event_members.setText("Total Members : "+Members);
                    event_address.setText("Address : "+Address);
                    event_date.setText("Date : "+StartDate +" to "+ EndDate);
                    if (event_other_details.length()>0) {
                        event_other_details.setText("Other Details : "+OtherDetails);
                    }
                    else {
                        event_other_details.setVisibility(View.GONE);
                    }
                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d MMM yyyy");
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

    private void checkPaymentStatus() {
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
                                JSONObject jsonArrayObject = jsonArray.getJSONObject(0);
                                String status = jsonArrayObject.getString("status");
                                Toast.makeText(CreateEvent.this, "Payment captured.", Toast.LENGTH_SHORT).show();
                                Log.e("anyText", "array : " + status);
                            }
                            else {
                                Toast.makeText(CreateEvent.this, "Payment is not captured yet.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        Log.e("anyText",""+error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic cnpwX3Rlc3RfYzlTRHdvTXRXVklNZks6Ym51WHVpVm9Vc29vbkpnVE4wamprTkdD");
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
                            JSONObject jsonObject = new JSONObject(response);
                            String id=jsonObject.getString("id");
                            sp.setPaymentId(id);
                            Log.e("anyText",id);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(CreateEvent.this, "Payment Request Sent", Toast.LENGTH_SHORT).show();
                        btn_pay_status.setVisibility(View.VISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        Log.e("anyText",""+error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic cnpwX3Rlc3RfYzlTRHdvTXRXVklNZks6Ym51WHVpVm9Vc29vbkpnVE4wamprTkdD");
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
        sp=new SharedPrefs(getApplicationContext());
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