package com.example.independentworkapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class EditProfileFragment extends Fragment
{
    View view;
    Dialog dialog;
    int i = 0;
    TextInputLayout ed_Fname,ed_Lname,ed_gendar,ed_DOB,ed_Phone,ed_Location;
    TextInputEditText Fname,Lname,DOB,Phone;
    AutoCompleteTextView gendar,Location;
    String[] array_gendar ={"MALE","FEMALE"};
    String[] array_Location ={"Ahmedabad","Amreli","Anand","Aravalli","Banaskantha (Palanpur)","Bharuch","Bhavnagar","Botad",
            "Chhota Udepur","Dahod","Dangs (Ahwa)","Devbhoomi Dwarka","Gandhinagar","Gir Somnath","Jamnagar","Junagadh",
            "Kachchh","Kheda (Nadiad)","Mahisagar","Mehsana","Morbi","Narmada (Rajpipla)","Navsari","Panchmahal (Godhra)","Patan",
            "Porbandar","Rajkot","Sabarkantha (Himmatnagar)","Surat","Surendranagar","Tapi (Vyara)","Vadodara","Valsad"};

    Button btn_update;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        view=inflater.inflate(R.layout.fragment_edit_profile, container, false);
        views();
        ArrayAdapter<String> adapter_gendar = new ArrayAdapter<String>
                (getContext(),android.R.layout.select_dialog_item,array_gendar);
        gendar.setThreshold(1);
        gendar.setAdapter(adapter_gendar);
        ArrayAdapter<String> adapter_location = new ArrayAdapter<String>
                (getContext(),android.R.layout.select_dialog_item,array_Location);
        Location.setThreshold(1);
        Location.setAdapter(adapter_location);
        dialog.show();
        Handler hdlr=new Handler();
        new Thread(new Runnable()
        {
            public void run()
            {
                while (i < 100)
                {
                    i += 2;
                    hdlr.post(new Runnable()
                    {
                        public void run()
                        {
                            if(i==10)
                            {
                                dialog.dismiss();
                                setData();
                            }
                        }
                    });
                    try
                    {
                        Thread.sleep(200);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT BIRTH DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        DOB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                materialDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
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
                        DOB.setText(Date);
                    }
                });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void setData() {
        Fname.setText(new SharedPrefs(getContext()).getFirstName());
        Lname.setText(new SharedPrefs(getContext()).getLasttName());
        gendar.setText(new SharedPrefs(getContext()).getGender());
        DOB.setText(new SharedPrefs(getContext()).getDateOfBirth());
        Phone.setText(new SharedPrefs(getContext()).getPhone());
        Location.setText(new SharedPrefs(getContext()).getLocation());
    }

    private void views() {
        ed_Fname=view.findViewById(R.id.ed_Fname);
        ed_Lname=view.findViewById(R.id.ed_Lname);
        ed_gendar=view.findViewById(R.id.ed_gendar);
        ed_DOB=view.findViewById(R.id.ed_DOB);
        ed_Phone=view.findViewById(R.id.ed_Phone);
        ed_Location=view.findViewById(R.id.ed_Location);
        Fname=view.findViewById(R.id.first_name);
        Lname=view.findViewById(R.id.last_name);
        gendar=view.findViewById(R.id.gendar);
        DOB=view.findViewById(R.id.DOB);
        Phone=view.findViewById(R.id.phone);
        Location=view.findViewById(R.id.Location);
        btn_update=view.findViewById(R.id.btn_update_profile);
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
    }
}