package com.example.learningservices;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Discouraged;
import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyService extends Service {

    private CompositeDisposable compositeDisposable;
    private static final String TAG = "MyService";

    public MyService() {
    }

    //у  сервисов есть свой ЖЦ
    @Override
    public void onCreate() {
        super.onCreate();
        compositeDisposable = new CompositeDisposable();
        log("OnCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("OnStartCommand");
        Disposable disposable = Observable.fromCallable(() -> {
            for (int i=0; i<100; i++){
                Thread.sleep(1000);
                log("Timer "+i);
            }
            return null;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        compositeDisposable.add(disposable);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
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
        return new Intent(context, MyService.class);
    }
}
