package com.example.josefernandes.capturalog.util;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.ARQUIVO;
import static com.example.josefernandes.capturalog.activity.LoginActivityConstantes.NOVA_LINHA;

public class Log {

    public static void montaLog(String evento, Context context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Date dataFormatada = simpleDateFormat.getCalendar().getTime();
        String textoGravacao = "App " + evento + " - " + dataFormatada;
        gravar(textoGravacao, context);
    }

    public static void montaLogUsuario(String evento, String email, Context context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Date dataFormatada = simpleDateFormat.getCalendar().getTime();
        String textoGravacao = "App " + evento + " - "+ dataFormatada;
        gravarLogUsuario(textoGravacao, email, context);
    }

    public static void gravar(String data, Context context) {
        try {
            FileOutputStream fOut = context.openFileOutput(ARQUIVO, Context.MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write(NOVA_LINHA + data);
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void gravarLogUsuario(String data, String email, Context context) {
        try {
            FileOutputStream fOut = context.openFileOutput(email +".txt", Context.MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write(NOVA_LINHA + data);
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
