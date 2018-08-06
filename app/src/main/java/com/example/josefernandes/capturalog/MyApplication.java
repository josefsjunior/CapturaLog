package com.example.josefernandes.capturalog;


import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.josefernandes.capturalog.util.FirebaseUtil;
import com.example.josefernandes.capturalog.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

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
        //e.printStackTrace(); // not all Android versions will print the stack trace automatically

        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND"); // see step 5.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
        startActivity(intent);

        StringWriter errors = montaLogDeErro(e);

        Log.montaLog(errors.toString(), getApplicationContext());
        FirebaseUtil.enviaArquivoFirebase(getApplicationContext());

        System.exit(1); // kill off the crashed app
    }

    @NonNull
    private StringWriter montaLogDeErro(Throwable e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors;
    }
}
