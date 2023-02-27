package com.example.independentworkapp.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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

import com.example.independentworkapp.Activity.CreateEvent;
import com.example.independentworkapp.MainActivity;
import com.example.independentworkapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment
{

    View view;
    Dialog dialog;
    int i = 0;
    FloatingActionButton floatingActionButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        floatingActionButton=view.findViewById(R.id.CreatePost);
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
}