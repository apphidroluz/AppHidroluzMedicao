package com.example.comercial_medicao.androidnovo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Display;
import android.widget.Button;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    Caixas caixa;

    boolean mediu = false;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTimer = new Timer();
        mTimer.schedule(timerTask,0,100);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private Timer mTimer;
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            notifiy();
        }
    };

    @Override
    public void onDestroy() {
        try {
            mTimer.cancel();
            timerTask.cancel();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void notifiy() {

        new DadosArduino().execute();

        while(mediu == false){
            try {
                Thread.sleep(100);
                Log.e("TESTE","ESTOU RODANDO");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notificacoes(caixa.getNivel(), caixa.getNivel2());
    }

    //   NOTIFICAÇÃO VAZIO

    public void notificacoes(String nv1, String nv2){

        if(nv1 != null && nv1.equals("00") || nv2 != null && nv2.equals("00")){
            gerarNotificacaoVazio(getApplicationContext());
        }

        if(nv1 != null && nv1.equals("25") || nv2 != null && nv2.equals("25")){
            gerarNotificacaoBaixo(getApplicationContext());
        }

        if(nv1 != null && nv1.equals("105") || nv2 != null && nv2.equals("105")){
            gerarNotificacaoMax(getApplicationContext());
        }
    }

    public void gerarNotificacaoVazio(Context ctx){

        String FOLLWERS_CHANNEL_ID = "mychannel01";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(FOLLWERS_CHANNEL_ID, "Followers",
                    NotificationManager.IMPORTANCE_DEFAULT);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);
        mBuilder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("AVISO")
                .setContentText("Seu reservatório está vazio")
                .setLights(Color.WHITE, 1000, 5000)
                .setVibrate(new long[]{100, 500, 200, 800})
                .setWhen(System.currentTimeMillis())
                .setContentIntent(criarContent(ctx));

        NotificationManagerCompat nmc = NotificationManagerCompat.from(ctx);
        nmc.notify(340, mBuilder.build());
    }

    //   NOTIFICAÇÃO BAIXO
    public void gerarNotificacaoBaixo(Context ctx){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);
        mBuilder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("AVISO")
                .setContentText("O nível do reservatório está baixo")
                .setLights(Color.WHITE, 1000, 5000)
                .setVibrate(new long[]{100, 500, 200, 800})
                .setWhen(System.currentTimeMillis())
                .setContentIntent(criarContent(ctx));
        NotificationManagerCompat nmc = NotificationManagerCompat.from(ctx);
        nmc.notify(341, mBuilder.build());
    }

    //   NOTIFICAÇÃO CHEIO
    public void gerarNotificacaoMax(Context ctx){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);
        mBuilder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("AVISO")
                .setContentText("Seu reservatório está cheio")
                .setLights(Color.WHITE, 1000, 5000)
                .setVibrate(new long[]{100, 500, 200, 800})
                .setWhen(System.currentTimeMillis())
                .setContentIntent(criarContent(ctx));

        NotificationManagerCompat nmc = NotificationManagerCompat.from(ctx);
        nmc.notify(341, mBuilder.build());
    }

    public PendingIntent criarContent(Context ctx) {
        Intent it = new Intent(ctx, HomeActivity.class);
        it.putExtra("titulo", "teste");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addNextIntent(it);
        return stackBuilder.getPendingIntent(1001, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // Criar notifições para os 3 níveis de aviso:
    // 0% - Vazio
    // 25% - Baixo
    // 100% - Cheio / Risco de transbordo
    // Aguardando DadosArduino para adaptar a leitura do valor

    class DadosArduino extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            caixa = new Caixas();
            List<String> valor = new ArrayList<>();
            String result = null;

            try {
                result = HttpRemote.getPost("http://www.android.hidroluz.com.br/php/bye.php", "");
                JSONArray obj = new JSONArray(result);

                for(int i=0; i < obj.length(); i++) {
                    JSONObject obj2 = obj.getJSONObject(i);
                    valor.add(i,obj2.getString("nivel" + i ));
                }
                // String valor = obj.getString(Integer.parseInt("nivel"));

                caixa.nivel = valor.get(0).replaceAll("^0","");
                caixa.nivel2 = valor.get(1).replaceAll("^0","");
                mediu = true;

                Log.e("teste",caixa.getNivel());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}

