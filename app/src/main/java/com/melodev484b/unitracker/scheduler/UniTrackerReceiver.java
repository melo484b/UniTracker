package com.melodev484b.unitracker.scheduler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.melodev484b.unitracker.R;

public class UniTrackerReceiver extends BroadcastReceiver {
    final String CHANNEL_ID = "scheduler_channel";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_calendar_art)
                .setContentText(intent.getStringExtra("key"))
                .setContentTitle("Reminder")
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, notification);
    }

    private void createNotificationChannel(Context context) {
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}