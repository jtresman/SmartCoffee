package com.example.android.bluetoothchat;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateNotificationActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);
        createNotification(getCurrentFocus());
    }

    public void createNotification(View view) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Build notification
        // Actions are just fake

        Notification.Builder builder = new Notification.Builder(this);

        switch (CommonStuff.getRunCycle()){
            case 1:
                builder.setContentTitle("BrewIT")
                       .setTicker("Coffee Done!")
                       .setSmallIcon(R.drawable.coffee_maker)
                       .setContentText("The Coffee is Done!")
                       .setContentIntent(pIntent);
                break;
            case 2:
                builder.setContentTitle("BrewIT")
                       .setTicker("Grinder Done!")
                       .setSmallIcon(R.drawable.coffee_maker)
                       .setContentText("The Coffee Has Been Ground!")
                       .setContentIntent(pIntent);
                break;
            case 3:
               builder.setContentTitle("BrewIT")
                      .setTicker("Water Filled!")
                      .setSmallIcon(R.drawable.coffee_maker)
                      .setContentText("The Water Tank Has Been Filled!")
                      .setContentIntent(pIntent);
                break;
            case 4:
                builder.setContentTitle("BrewIT")
                       .setTicker("Coffee Done!")
                       .setSmallIcon(R.drawable.coffee_maker)
                       .setContentText("Your Coffee is Done!")
                       .setContentIntent(pIntent);
                break;
        }

        Notification noti = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        noti.defaults |= Notification.DEFAULT_SOUND;

        notificationManager.notify(0, noti);

        finish();

    }
}
