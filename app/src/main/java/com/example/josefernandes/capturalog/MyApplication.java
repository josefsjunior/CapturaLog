package com.example.josefernandes.capturalog;


import android.app.Application;
import android.content.Intent;

import com.example.josefernandes.capturalog.util.FirebaseUtil;
import com.example.josefernandes.capturalog.util.Log;

public class MyApplication extends Application {
    public void onCreate() {
        // Setup handler for uncaught exceptions.
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically

        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND"); // see step 5.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
        startActivity(intent);

        Log.montaLog(e.getMessage(), getApplicationContext());
        FirebaseUtil.enviaArquivoFirebase(getApplicationContext());

        System.exit(1); // kill off the crashed app
    }
}
