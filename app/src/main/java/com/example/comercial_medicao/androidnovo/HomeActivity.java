package com.example.comercial_medicao.androidnovo;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import me.itangqi.waveloadingview.WaveLoadingView;

public class HomeActivity extends Activity {

    Caixas caixa;

    StringBuilder sb;
    private Button btn;
    private boolean mediu = false;
//    DadosArduino dadosArduino;     <-- buscando nova Classe (?)

    EditText txtCaixa1, txtCaixa2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TredArduino();

        new DadosArduino().execute();

        while(mediu == false){
            try {
             Thread.sleep(100);
             Log.e("TESTE","ESTOU RODANDO");
           } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        startService(new Intent(this, NotificationService.class));

    }

//  --> Atualizar entrada de dados para que venham da classe DadosArduino
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_home);
        txtCaixa1 = (EditText) findViewById(R.id.txtCaixa1);
        txtCaixa2 = (EditText) findViewById(R.id.txtCaixa2);

        if(caixa.getNivel() != null) {

            //Caixa d'água 1
            WaveLoadingView mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
            mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.SQUARE);
            mWaveLoadingView.startAnimation();
            if (Integer.parseInt(caixa.getNivel()) == 100) {
                mWaveLoadingView.setProgressValue(97);
            } else {
                mWaveLoadingView.setProgressValue(Integer.parseInt(caixa.getNivel()));
            }
            mWaveLoadingView.setCenterTitle(caixa.getNivel() + "%");
            mWaveLoadingView.setAmplitudeRatio(60);
            mWaveLoadingView.setTopTitleStrokeWidth(3);
            mWaveLoadingView.setAnimDuration(3000);
            if (Integer.parseInt(caixa.getNivel()) < 49) {
                mWaveLoadingView.setWaveColor(getColor(R.color.critico));
            } else {
                mWaveLoadingView.setWaveColor(getColor(R.color.azul_w));
            }

            //Caixa d'água 2
            WaveLoadingView mWaveLoadingView2 = (WaveLoadingView) findViewById(R.id.waveLoadingView2);
            mWaveLoadingView2.setShapeType(WaveLoadingView.ShapeType.SQUARE);
            if (Integer.parseInt(caixa.getNivel2()) == 100) {
                mWaveLoadingView2.setProgressValue(97);
            } else if (Integer.parseInt(caixa.getNivel2()) > 100){
                mWaveLoadingView2.setProgressValue(103);
            } else {
                mWaveLoadingView2.setProgressValue(Integer.parseInt(caixa.getNivel2()));
            }
            if(Integer.parseInt(caixa.getNivel2()) > 100) {
                mWaveLoadingView2.setCenterTitle("Atenção");
            }else {
                mWaveLoadingView2.setCenterTitle(caixa.getNivel2() + "%");
            }
            mWaveLoadingView2.setAmplitudeRatio(60);
            mWaveLoadingView2.setTopTitleStrokeWidth(3);
            mWaveLoadingView2.setAnimDuration(3000);
            if (Integer.parseInt(caixa.getNivel2()) < 49 || Integer.parseInt(caixa.getNivel2()) > 100 ) {
                mWaveLoadingView2.setWaveColor(getColor(R.color.critico));
            } else {
                mWaveLoadingView2.setWaveColor(getColor(R.color.azul_w));
            }

//            notificacaoVazio(caixa.getNivel(), caixa.getNivel2());
//            notificacaoBaixo(caixa.getNivel(), caixa.getNivel2());
//            notificacaoMax(Integer.parseInt(caixa.getNivel()), Integer.parseInt(caixa.getNivel2()));

        }
    }

/*
    --> Notificações sendo movidas para outra classe: NotificationService

//   NOTIFICAÇÃO VAZIO
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
    public void notificacaoVazio(String nv1, String nv2){
        if(nv1.equals("00")||nv2.equals("00")){
            gerarNotificacaoVazio(getApplicationContext());
        }
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
    public void notificacaoBaixo(String nv1, String nv2){
        if(nv1.equals("25")||nv2.equals("25")){
            gerarNotificacaoBaixo(getApplicationContext());
        }
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
    public void notificacaoMax(int nv1, int nv2){
        if(nv1 == 100 || nv2 == 100){
            gerarNotificacaoMax(getApplicationContext());
        }
    }
*/

    public void medir(View v) {
       
        Intent mIt  = getIntent();
        finish();
        startActivity(mIt);
    }

    public PendingIntent criarContent(Context ctx) {
        Intent it = new Intent(ctx, HomeActivity.class);
        it.putExtra("titulo", "teste");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addNextIntent(it);
        return stackBuilder.getPendingIntent(1001, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void sair(View v) {
        //Toast.makeText(this, "Ainda não é possivel sair.", Toast.LENGTH_LONG).show();
        //notification.setSmallIcon(R.mipmap.ic_launcher);
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);
        finish();
    }

/*
   --> Movendo a subClasse DadosArduino para a nova classe DadosArduino...

    class DadosArduino extends AsyncTask<Void, Void, String> {

        final SweetAlertDialog pDialog = new SweetAlertDialog(HomeActivity.this,
                SweetAlertDialog.PROGRESS_TYPE);

        protected void onPreExecute() {
            super.onPreExecute();


            pDialog.setTitleText("Aguarde !!!");
            pDialog.setContentText("Atualizando Informações...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... voids) {

            caixa = new Caixas();


            List<String> valor = new ArrayList<>();


            String result = null;
            try {



                result = HttpRemote.getPost("http://www.android.hidroluz.com.br/php/bye.php", "");
                JSONArray  obj = new JSONArray(result);

                for(int i=0; i < obj.length(); i++) {
                    JSONObject obj2 = obj.getJSONObject(i);

                    valor.add(i,obj2.getString("nivel" + i ));

                }
                // String valor = obj.getString(Integer.parseInt("nivel"));


                caixa.nivel = valor.get(0).replaceAll("^0","");
                caixa.nivel2 = valor.get(1).replaceAll("^0","");


                Log.e("teste",caixa.getNivel());

                mediu = true;


            } catch (Exception e) {
                e.printStackTrace();
            }


            return result;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            pDialog.dismiss();

        }

    }
*/

    public void TredArduino(){

       /* try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        Thread t = new Thread(new Runnable() {
            public void run() {

                try {
                    Log.e("TESTE","ESTOU RODANDO1");
                   Thread.sleep(600000);
                   Log.e("TESTE","ESTOU RODANDO2");
                    Intent mIt  = getIntent();
                    finish();

                    new DadosArduino().execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        t.start();

    }

// Verificar quais outros métodos deverão sofrer alterações devido às novas classes criadas

}




