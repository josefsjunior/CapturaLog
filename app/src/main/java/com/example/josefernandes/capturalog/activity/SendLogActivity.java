package com.example.josefernandes.capturalog.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.example.josefernandes.capturalog.R;

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
        finish();
    }
}
