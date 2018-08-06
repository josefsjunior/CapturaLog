package com.example.josefernandes.capturalog.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.josefernandes.capturalog.R;
import com.example.josefernandes.capturalog.util.FirebaseUtil;
import com.example.josefernandes.capturalog.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.josefernandes.capturalog.activity.Constantes.APP_CRASH;

public class SendLogActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature (Window.FEATURE_NO_TITLE); // make a dialog without a titlebar
        setFinishOnTouchOutside (false); // prevent users from dismissing the dialog by tapping outside
        setContentView(R.layout.activity_send_log);
    }

    @Override
    public void onClick (View v)
    {
        //handleUncaughtException(Thread.currentThread(), new Throwable());

        //sendLogFile();
        finish();
    }

    private void sendLogFile ()
    {
        String fullName = extractLogToFile();
        if (fullName == null)
            return;

        Intent intent = new Intent (Intent.ACTION_SEND);
        intent.setType ("plain/text");
        intent.putExtra (Intent.EXTRA_EMAIL, new String[] {"jose.fernandes@zbra.com.br"});
        intent.putExtra (Intent.EXTRA_SUBJECT, "MyApp log file");
        intent.putExtra (Intent.EXTRA_STREAM, Uri.parse ("file://" + fullName));
        intent.putExtra (Intent.EXTRA_TEXT, "Log file attached."); // do this so some email clients don't complain about empty body.
        startActivity (intent);
    }

    private String extractLogToFile()
    {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo (this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
        }
        String model = Build.MODEL;
        if (!model.startsWith(Build.MANUFACTURER))
            model = Build.MANUFACTURER + " " + model;

        // Make file name - file must be saved to external storage or it wont be readable by
        // the email app.
        String path = Environment.getExternalStorageDirectory() + "/" + "MyApp/";
        String fullName = path + "teste.txt"; //<some name>

        // Extract to file.
        File file = new File (fullName);
        InputStreamReader reader = null;
        FileWriter writer = null;
        try
        {
            // For Android 4.0 and earlier, you will get all app's log output, so filter it to
            // mostly limit it to your app's output.  In later versions, the filtering isn't needed.
            String cmd = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) ?
                    "logcat -d -v time MyApp:v dalvikvm:v System.err:v *:s" :
                    "logcat -d -v time";

            // get input stream
            Process process = Runtime.getRuntime().exec(cmd);
            reader = new InputStreamReader (process.getInputStream());

            // write output stream
            writer = new FileWriter (file);
            writer.write ("Android version: " +  Build.VERSION.SDK_INT + "\n");
            writer.write ("Device: " + model + "\n");
            writer.write ("App version: " + (info == null ? "(null)" : info.versionCode) + "\n");

            char[] buffer = new char[10000];
            do
            {
                int n = reader.read (buffer, 0, buffer.length);
                if (n == -1)
                    break;
                writer.write (buffer, 0, n);
            } while (true);

            reader.close();
            writer.close();
        }
        catch (IOException e)
        {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e1) {
                }

            Log.montaLog(APP_CRASH, getApplicationContext());
            FirebaseUtil.enviaArquivoFirebase(getApplicationContext());
            return null;
        }

        return fullName;
    }

    public boolean isUIThread(){
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically

        if(isUIThread()) {
            invokeLogActivity();
        }else{  //handle non UI thread throw uncaught exception

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    invokeLogActivity();
                }
            });
        }
    }

    private void invokeLogActivity(){
        Intent intent = new Intent ();
        intent.setAction ("com.mydomain.SEND_LOG"); // see step 5.
        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
        startActivity (intent);

        System.exit(1); // kill off the crashed app
    }
}
