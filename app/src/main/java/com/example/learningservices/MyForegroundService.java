package com.example.learningservices;

import static com.example.learningservices.MainActivity.CHANNEL_ID;
import static com.example.learningservices.MainActivity.CHANNEL_NAME;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyForegroundService extends Service {

    public static final int NOTIFICATION_ID = 1;
//    private CompositeDisposable compositeDisposable;
    private static final String TAG = "MyForegroundService";

    public MyForegroundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        compositeDisposable = new CompositeDisposable();
        log("OnCreate");
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, createNotification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("OnStartCommand");
//        Disposable disposable = Observable.fromCallable(() -> {
//            for (int i=0; i<10; i++){
//                Thread.sleep(1000);
//                log("Timer "+i);
//            }
//            //stopSelf(); //to stop service from the inside
//            return null;
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
//        compositeDisposable.add(disposable);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                        log("Timer " + i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                //stopSelf();
            }
        });
        return START_STICKY;
    }

    public void stopService(){
        stopForeground(true);
        stopSelf();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
//        compositeDisposable.dispose();
        log("OnDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void log(String message) {
        Log.d(TAG, message);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MyForegroundService.class);
    }
}
