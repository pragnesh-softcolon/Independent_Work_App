package com.example.independentworkapp.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.independentworkapp.MainActivity;
import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

//   Override onNewToken to get new token
    @Override
    public void onNewToken(@NonNull String token)
    {
        Log.d("newToken", "Refreshed token: " + token);
    }
    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage)
    {
        Log.e("anyText5",""+remoteMessage);
        if (remoteMessage.getNotification() != null)
        {
            if ( !new SharedPrefs(getApplicationContext()).getUserToken().isEmpty())
            {
                showNotification(remoteMessage.getNotification().getTitle(),
                        remoteMessage.getNotification().getBody());
            }
        }
    }
    public void showNotification(String title,String message)
    {
        Intent intent = new Intent(this, MainActivity.class);
        String channel_id = "INDEPENDENT WORK APP";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(getApplicationContext(),
                channel_id)
                .setSmallIcon(R.drawable.img)
                .setAutoCancel(true)
                .setVibrate(new long[] { 1000, 1000, 1000,
                        1000, 1000 })
                .setContentIntent(pendingIntent);
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.img);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "INDEPENDENT WORK APP",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, builder.build());
    }

}