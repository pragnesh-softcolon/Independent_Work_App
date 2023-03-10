package com.example.independentworkapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.independentworkapp.Activity.JoinedUsers;
import com.example.independentworkapp.Models.AllEvents.GetAllEvent;
import com.example.independentworkapp.Models.CreatedEvent.GetCreatedEvent;
import com.example.independentworkapp.Models.CreatedEvent.JoinUser;
import com.example.independentworkapp.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class createdEventAdapter extends RecyclerView.Adapter<createdEventAdapter.MyViewHolder>{
    Context context;
    List<GetCreatedEvent> eventData;

    public createdEventAdapter(Context context, List<GetCreatedEvent> eventData)
    {
        this.context=context;
        this.eventData=eventData;
    }

    @NonNull
    @Override
    public createdEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.event_row,parent,false);
        return new createdEventAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull createdEventAdapter.MyViewHolder holder, int position) {
        GetCreatedEvent event = eventData.get(position);
        holder.EventName.setText("Event Name : "+event.getName());
//        int mem=event.getMembers();
//        int join = event.getJoinUser().size();
//        int member=mem-join;
        holder.Work.setText("Work : "+event.getWork());
//        holder.Member.setText("Need "+member+" member");
        holder.Member.setText("Need "+event.getMembers().toString()+" Members");
        holder.Date.setText("Event Duration : "+event.getStartDate()+" to "+event.getEndDate());
        holder.Location.setText("Location : "+event.getLocation());
        holder.Payment.setText("Payment : "+event.getPaymentPerDay().toString()+"/-");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,JoinedUsers.class);
                intent.putExtra("event_id",event.getId());
                context.startActivity(intent);
            }
        });

    }

    public int getItemCount()
    {
        return eventData.size();
    }
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,EventName,Work,Member,Date,Location,Payment;
        LinearLayout layout;
        public MyViewHolder(@NonNull View v) {
            super(v);
            name=v.findViewById(R.id.name);
            EventName=v.findViewById(R.id.EventName);
            Work=v.findViewById(R.id.Work);
            Member=v.findViewById(R.id.Member);
            Date=v.findViewById(R.id.Date);
            Location=v.findViewById(R.id.Locatoin);
            Payment=v.findViewById(R.id.Payment);
            layout=v.findViewById(R.id.layout);
            name.setVisibility(View.GONE);

        }
    }
}
