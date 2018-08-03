package com.example.josefernandes.capturalog.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.example.josefernandes.capturalog.R;
import com.example.josefernandes.capturalog.util.FirebaseUtil;
import com.example.josefernandes.capturalog.util.Log;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import static com.example.josefernandes.capturalog.activity.Constantes.APP_CLOSED;
import static com.example.josefernandes.capturalog.activity.Constantes.APP_OPENED;
import static com.example.josefernandes.capturalog.activity.Constantes.EMAIL;

@Fullscreen
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    private String email;

    @ViewById
    TextView main_texto_email;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if(intent.hasExtra(EMAIL)){
            main_texto_email.setText(intent.getExtras().getString(EMAIL));
            email = main_texto_email.getText().toString();
            Log.montaLogUsuario(APP_OPENED, email, getApplicationContext());
            FirebaseUtil.enviaArquivoFirebaseUsuario(getApplicationContext(), email);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.montaLogUsuario(APP_CLOSED, email, getApplicationContext());
        FirebaseUtil.enviaArquivoFirebaseUsuario(getApplicationContext(), email);
    }
}
