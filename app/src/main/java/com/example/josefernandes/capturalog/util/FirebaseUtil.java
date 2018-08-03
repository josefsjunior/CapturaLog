package com.example.josefernandes.capturalog.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.ARQUIVO;
import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.ARQUIVO_FIREBASE;

public class FirebaseUtil {

    private static StorageReference storageReference;

    public static void enviaArquivoFirebase(Context context) {
        storageReference = FirebaseStorage.getInstance().getReference();
        String caminhoAbsolutoDoArquivo = context.getFilesDir().getPath() + "/" + ARQUIVO;
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

    public static void enviaArquivoFirebaseUsuario(Context context, String email) {
        storageReference = FirebaseStorage.getInstance().getReference();
        String caminhoAbsolutoDoArquivo = context.getFilesDir().getPath() + "/" + email +".txt";
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
