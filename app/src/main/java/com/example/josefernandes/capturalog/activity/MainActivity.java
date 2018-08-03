package com.example.josefernandes.capturalog.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.josefernandes.capturalog.R;
import com.example.josefernandes.capturalog.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import java.io.File;

import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.APP_CLOSED;
import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.APP_OPENED;
import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.ARQUIVO_FIREBASE;
import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.EMAIL;

@Fullscreen
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    private StorageReference storageReference;
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
            enviaArquivoFirebase(main_texto_email.getText().toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.montaLogUsuario(APP_CLOSED, email, getApplicationContext());
        enviaArquivoFirebase(main_texto_email.getText().toString());
    }

    private void enviaArquivoFirebase(String email) {
        storageReference = FirebaseStorage.getInstance().getReference();
        String caminhoAbsolutoDoArquivo = getApplicationContext().getFilesDir().getPath() + "/" + main_texto_email.getText().toString()+".txt";
        Uri file = Uri.fromFile(new File(caminhoAbsolutoDoArquivo));
        StorageReference arquivoRef = storageReference.child(email).child(ARQUIVO_FIREBASE);
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
