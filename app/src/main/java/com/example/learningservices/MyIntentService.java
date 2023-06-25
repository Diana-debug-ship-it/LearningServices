package com.example.learningservices;

import static com.example.learningservices.MainActivity.CHANNEL_ID;
import static com.example.learningservices.MainActivity.CHANNEL_NAME;
import static com.example.learningservices.MyForegroundService.NOTIFICATION_ID;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("myname");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        log("OnCreate");
        setIntentRedelivery(true);
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, createNotification());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        log("onHandleIntent");
        for (int i=0; i<5; i++){
            try {
                Thread.sleep(1000);
                log("Timer "+i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("OnDestroy");
    }

    private void createNotificationChannel() {
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }
    }

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Title")
                .setContentText("Some text here.............")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build();
    }

    private void log(String message) {
        Log.d(TAG, message);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MyIntentService.class);
    }
}
