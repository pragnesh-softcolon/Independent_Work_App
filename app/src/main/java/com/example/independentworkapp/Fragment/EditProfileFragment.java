package com.example.independentworkapp.Fragment;

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
import android.widget.Button;

import com.example.independentworkapp.R;
import com.google.android.material.textfield.TextInputLayout;


public class EditProfileFragment extends Fragment
{
    View view;
    Dialog dialog;
    int i = 0;
    TextInputLayout ed_Fname,ed_Lname,ed_gendar,ed_DOB,ed_Phone,ed_Location;
    Button btn_update;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        view=inflater.inflate(R.layout.fragment_edit_profile, container, false);
        views();
        dialog.show();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
                            if(i==30)
                            {
                                dialog.dismiss();
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
        // Inflate the layout for this fragment
        return view;
    }

    private void views() {
        ed_Fname=view.findViewById(R.id.ed_Fname);
        ed_Lname=view.findViewById(R.id.ed_Lname);
        ed_gendar=view.findViewById(R.id.ed_gendar);
        ed_DOB=view.findViewById(R.id.ed_DOB);
        ed_Phone=view.findViewById(R.id.ed_Phone);
        ed_Location=view.findViewById(R.id.ed_Location);
        btn_update=view.findViewById(R.id.btn_update_profile);
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
    }
}