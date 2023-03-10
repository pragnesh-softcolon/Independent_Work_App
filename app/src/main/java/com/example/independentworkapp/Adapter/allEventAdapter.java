package com.example.independentworkapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.independentworkapp.Activity.EventDetail;
import com.example.independentworkapp.Fragment.HomeFragment;
import com.example.independentworkapp.Models.AllEvents.GetAllEvent;
import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;

import java.util.ArrayList;
import java.util.List;

public class allEventAdapter extends RecyclerView.Adapter<allEventAdapter.MyViewHolder>
{
    Context context;
    List<GetAllEvent> eventData;

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
//        int mem=event.getMembers();
//        int join = event.getJoinUser().size();
//        int member=mem-join;
        holder.Work.setText("Work : "+event.getWork());
//        holder.Member.setText("Need "+member+" member");
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,EventName,Work,Member,Date,Location,Payment;
        LinearLayout layout;
        String id,eventName,eventWork,payment,timeing,members,startDate,endDate,location,mapLinkLocation,address,otherDetails;
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
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Boolean isJoined=false;
            int position=getAdapterPosition();
            GetAllEvent event = eventData.get(position);
            if (event.getJoinUser().contains(new SharedPrefs(context).getUserId())){
                isJoined=true;
            }
            Intent intent=new Intent(context, EventDetail.class);
            intent.putExtra("activity","allEvent");
            intent.putExtra("eventName",event.getName());
            intent.putExtra("eventWork",event.getWork());
            intent.putExtra("isJoined",isJoined);
            intent.putExtra("payment",event.getPaymentPerDay().toString());
            intent.putExtra("members",event.getMembers().toString());
            intent.putExtra("startDate",event.getStartDate());
            intent.putExtra("endDate",event.getEndDate());
            intent.putExtra("location",event.getLocation());
            intent.putExtra("mapLinkLocation",event.getMapLocation());
            intent.putExtra("address",event.getAddress());
            intent.putExtra("otherDetails",event.getDescription());
            intent.putExtra("timeing",event.getTime());
            intent.putExtra("id",event.getId());
            context.startActivity(intent);
        }
    }
}
