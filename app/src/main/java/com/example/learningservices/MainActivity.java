package com.example.learningservices;

import android.Manifest;
import android.app.LauncherActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.learningservices.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final String CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "channel_name";

    public static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnStartSimpleService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(MyService.newIntent(MainActivity.this));
            }
        });
        binding.btnStartForegroundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showNotification();
                ContextCompat.startForegroundService(MainActivity.this, MyForegroundService.newIntent(MainActivity.this));
            }
        });
        binding.btnStartIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(MyIntentService.newIntent(MainActivity.this));
            }
        });
    }

    private void showNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Title")
                .setContentText("Some text here.............")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build();
        manager.notify(1, notification);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}