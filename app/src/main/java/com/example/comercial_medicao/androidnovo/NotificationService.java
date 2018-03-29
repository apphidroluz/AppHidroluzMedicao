package com.example.comercial_medicao.androidnovo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;

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
        mTimer.schedule(timerTask,2000,2 * 1000);
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

        ConexaoTest funcao = new ConexaoTest();
        if (!funcao.netWorkdisponibilidade(this)) {



            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    notifiy();
                }
            };


        }else {

            new DadosArduino().execute();

        }



        while(mediu == false){
            try {
                Thread.sleep(100);
                Log.e("TESTE","ESTOU RODANDO");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        notificacaoVazio(caixa.getNivel(), caixa.getNivel2());

    }

    //   NOTIFICAÇÃO VAZIO

    public void notificacaoVazio(String nv1, String nv2){


        if(nv1.equals("00")||nv2.equals("00")){
            gerarNotificacaoVazio(getApplicationContext());
        }

        if(nv1.equals("25")||nv2.equals("25")){
            gerarNotificacaoBaixo(getApplicationContext());
        }

        if(nv1.equals("105")||nv2.equals("105")){
            gerarNotificacaoMax(getApplicationContext());
        }
    }

    public void gerarNotificacaoVazio(Context ctx){
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
        Intent it = new Intent(ctx, NotificationService.class);
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
