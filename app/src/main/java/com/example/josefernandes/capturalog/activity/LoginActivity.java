package com.example.josefernandes.capturalog.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.josefernandes.capturalog.R;
import com.example.josefernandes.capturalog.util.FirebaseUtil;
import com.example.josefernandes.capturalog.util.Log;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import static com.example.josefernandes.capturalog.activity.Constantes.APP_CLOSED;
import static com.example.josefernandes.capturalog.activity.Constantes.APP_OPENED;
import static com.example.josefernandes.capturalog.activity.Constantes.EMAIL;

@Fullscreen
@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    @ViewById
    EditText login_email;

    @ViewById
    EditText login_senha;

    @ViewById
    Button login_botao;

    @Click
    void login_botao() {
        String email = login_email.getText().toString();
        //email = null;
        String senha = login_senha.getText().toString();

        if(email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        } else {
            vaiParaMenu();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.montaLog(APP_OPENED, getApplicationContext());
        FirebaseUtil.enviaArquivoFirebase(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.montaLog(APP_CLOSED, getApplicationContext());
        FirebaseUtil.enviaArquivoFirebase(getApplicationContext());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.montaLog(APP_CLOSED, getApplicationContext());
        FirebaseUtil.enviaArquivoFirebase(getApplicationContext());
    }

    private void vaiParaMenu() {
        Intent vaiParaMenu = new Intent(this, MainActivity_.class);
        vaiParaMenu.putExtra(EMAIL, login_email.getText().toString());
        startActivity(vaiParaMenu);
        finish();
    }
}
