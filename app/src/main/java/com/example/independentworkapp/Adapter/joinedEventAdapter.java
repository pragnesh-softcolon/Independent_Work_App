package com.example.independentworkapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.independentworkapp.Activity.EventDetail;
import com.example.independentworkapp.Models.AllEvents.GetAllEvent;
import com.example.independentworkapp.Models.CreatedEvent.GetCreatedEvent;
import com.example.independentworkapp.Models.JoinedEvents.JoinEvent;
import com.example.independentworkapp.R;

import java.util.List;

public class joinedEventAdapter extends RecyclerView.Adapter<joinedEventAdapter.MyViewHolder>{
    Context context;
    List<JoinEvent> eventData;
    public joinedEventAdapter(Context context, List<JoinEvent> eventData)
    {
        this.context=context;
        this.eventData=eventData;
    }
    @NonNull
    @Override
    public joinedEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.event_row,parent,false);
        return new joinedEventAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull joinedEventAdapter.MyViewHolder holder, int position) {
        JoinEvent event = eventData.get(position);
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            JoinEvent event = eventData.get(position);
            Intent intent=new Intent(context, EventDetail.class);
            intent.putExtra("activity","joinedEvent");
            intent.putExtra("eventName",event.getName());
            intent.putExtra("eventWork",event.getWork());
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
