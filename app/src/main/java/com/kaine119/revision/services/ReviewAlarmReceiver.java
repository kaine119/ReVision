package com.kaine119.revision.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.kaine119.revision.MainActivity;
import com.kaine119.revision.R;

/**
 * AlarmReceiver for weekly review reminder.
 */

public class ReviewAlarmReceiver extends BroadcastReceiver {
    
    @Override
    public void onReceive(Context context, Intent intent) {
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
    
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("What have you learnt?")
                    .setContentText("Hello World!");
        
        Intent notificationIntent = new Intent(context, MainActivity.class);
    
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultingPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        
        mBuilder.setContentIntent(resultingPendingIntent);
        
        notificationManager.notify(1, mBuilder.build());
        
    }
}
