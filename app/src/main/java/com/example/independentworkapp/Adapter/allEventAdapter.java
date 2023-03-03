package com.example.independentworkapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.independentworkapp.Fragment.HomeFragment;
import com.example.independentworkapp.Models.AllEvents.GetAllEvent;
import com.example.independentworkapp.R;

import java.util.List;

public class allEventAdapter extends RecyclerView.Adapter<allEventAdapter.MyViewHolder>
{
    Context context;
    List<GetAllEvent> eventData;
    public allEventAdapter(){

    }

    public allEventAdapter(Context context, List<GetAllEvent> eventData){
        this.context=context;
        this.eventData=eventData;
    }

    @NonNull
    @Override
    public allEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.event_row,parent,false);
        return new allEventAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull allEventAdapter.MyViewHolder holder, int position) {
        GetAllEvent event = eventData.get(position);
        holder.name.setText(event.getUser().getFirstName()+" "+event.getUser().getLastName());
        holder.EventName.setText("Event Name : "+event.getName());
        holder.Work.setText("Work : "+event.getWork());
        holder.Member.setText("Need "+event.getMembers().toString()+" Members");
        holder.Date.setText("Event Duration : "+event.getStartDate()+" to "+event.getEndDate());
        holder.Location.setText("Location : "+event.getLocation());
        holder.Payment.setText("Payment : "+event.getPaymentPerDay().toString()+"/-");
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
        public MyViewHolder(@NonNull View v) {
            super(v);
            name=v.findViewById(R.id.name);
            EventName=v.findViewById(R.id.EventName);
            Work=v.findViewById(R.id.Work);
            Member=v.findViewById(R.id.Member);
            Date=v.findViewById(R.id.Date);
            Location=v.findViewById(R.id.Locatoin);
            Payment=v.findViewById(R.id.Payment);
        }
    }
}
