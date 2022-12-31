package com.example.independentworkapp.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.independentworkapp.Activity.CreateEvent;
import com.example.independentworkapp.MainActivity;
import com.example.independentworkapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment
{

    View view;
    FloatingActionButton floatingActionButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);;
        floatingActionButton=view.findViewById(R.id.CreatePost);
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