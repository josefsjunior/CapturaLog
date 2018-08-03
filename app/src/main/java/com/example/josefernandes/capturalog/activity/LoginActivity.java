package com.example.josefernandes.capturalog.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.josefernandes.capturalog.R;
import com.example.josefernandes.capturalog.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.APP_CLOSED;
import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.APP_OPENED;
import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.ARQUIVO;
import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.ARQUIVO_FIREBASE;
import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.EMAIL;
import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.NOVA_LINHA;

@Fullscreen
@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    private StorageReference storageReference;

    @ViewById
    EditText login_email;

    @ViewById
    EditText login_senha;

    @ViewById
    Button login_botao;

    @Click
    void login_botao() {
        String email = login_email.getText().toString();
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
        enviaArquivoFirebase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.montaLog(APP_CLOSED, getApplicationContext());
        enviaArquivoFirebase();
    }

    private void vaiParaMenu() {
        Intent vaiParaMenu = new Intent(this, MainActivity_.class);
        vaiParaMenu.putExtra(EMAIL, login_email.getText().toString());
        startActivity(vaiParaMenu);
        finish();
    }

    private void enviaArquivoFirebase() {
        storageReference = FirebaseStorage.getInstance().getReference();
        String caminhoAbsolutoDoArquivo = getApplicationContext().getFilesDir().getPath() + "/" + ARQUIVO;
        Uri file = Uri.fromFile(new File(caminhoAbsolutoDoArquivo));
        StorageReference arquivoRef = storageReference.child(ARQUIVO_FIREBASE);
        arquivoRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //não upou o arquivo
            }
        });
    }
}
