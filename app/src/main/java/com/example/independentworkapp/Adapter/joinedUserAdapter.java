package com.example.independentworkapp.Adapter;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.independentworkapp.Activity.JoinedUsers;
import com.example.independentworkapp.Extra.AgeCalculator;
import com.example.independentworkapp.Models.JoinedEvents.JoinEvent;
import com.example.independentworkapp.Models.JoinedUsers.JoinedUser;
import com.example.independentworkapp.Network.Apis;
import com.example.independentworkapp.R;

import java.util.List;

public class joinedUserAdapter extends RecyclerView.Adapter<joinedUserAdapter.MyViewHolder>{
    Context context;
    List<JoinedUser> userData;
    public joinedUserAdapter(Context context, List<JoinedUser> userData)
    {
        this.context=context;
        this.userData=userData;
    }
    @NonNull
    @Override
    public joinedUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.joinusers,parent,false);
        return new joinedUserAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull joinedUserAdapter.MyViewHolder holder, int position) {
        JoinedUser user = userData.get(position);
        holder.name.setText(user.getFirstName()+" "+user.getLastName());
        holder.age.setText(new AgeCalculator().calculate(user.getDateOfBirth()) +" years old");
        holder.gender.setText(user.getGender());
        holder.image.setOnClickListener(view -> {
            holder.alert= new AlertDialog.Builder(context);
            holder.alert.setTitle("Aadhaar-card");
            holder.alert.setMessage("Download or view?");
            holder.alert.setPositiveButton("Download", (dialog, which) -> {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Apis.IMAGE+user.getImage()));
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setTitle("");
                request.setDescription("");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "" +user.getFirstName()+"_"+user.getLastName()+".jpg");
                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Toast.makeText(context, "Downloading Aadhaar-card", Toast.LENGTH_SHORT).show();
                manager.enqueue(request);
            });
            holder.alert.setNegativeButton("View", (dialog,which) -> {
                holder.dialog = new Dialog(context);
                holder.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                holder.dialog.setContentView(R.layout.image_dialog);
                holder.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageView img=holder.dialog.findViewById(R.id.img_dialog);
                Glide.with(context).load(Uri.parse(Apis.IMAGE+user.getImage())).into(img);
                holder.dialog.show();
            });
            holder.alert.show();
        });
        holder.phone.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+user.getPhone()));
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(context, "You don't have permission to call", Toast.LENGTH_SHORT).show();
            }
            else
            {
                context.startActivity(callIntent);
            }
        });
    }

    public int getItemCount()
    {
        return userData.size();
    }
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,age,gender;
        Button image,phone;
        AlertDialog.Builder alert;
        Dialog dialog;
        public MyViewHolder(@NonNull View v) {
            super(v);
            name=v.findViewById(R.id.userName);
            age=v.findViewById(R.id.user_age);
            gender=v.findViewById(R.id.user_gender);
            image=v.findViewById(R.id.image);
            phone=v.findViewById(R.id.phone);

        }
    }
}
