package com.senoritasaudi.firebase;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.senoritasaudi.R;
import com.senoritasaudi.storeutils.StoreManager;
import com.senoritasaudi.views.MainActivity;
import com.senoritasaudi.views.SplashActivity;

import java.util.Map;

public class FirebaseNotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Map<String, String> notificationMessage = remoteMessage.getData();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "0");

        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(notificationMessage.get("title")));
        mBuilder.setContentTitle(notificationMessage.get("title"));
        mBuilder.setContentText(notificationMessage.get("body"));
        mBuilder.setChannelId("0");
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setColor(color);
            mBuilder.setSmallIcon(R.drawable.ic_notification_icon);
        } else {
            mBuilder.setSmallIcon(R.drawable.ic_notification_icon);
        }
        Intent resultIntent = new Intent(this, MainActivity.class);


        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SplashActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("0", "test", NotificationManager.IMPORTANCE_HIGH);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);


        int random = (int) System.currentTimeMillis();
        if (mNotificationManager != null) {
            mNotificationManager.notify(random, mBuilder.build());
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        StoreManager storeManager = new StoreManager(this);
        storeManager.saveToken(s);
    }

}
