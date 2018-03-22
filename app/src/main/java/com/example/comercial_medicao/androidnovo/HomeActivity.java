package com.example.comercial_medicao.androidnovo;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import me.itangqi.waveloadingview.WaveLoadingView;

public class HomeActivity extends Activity {

    Caixas caixa;

    StringBuilder sb;
    private Button btn;

    EditText txtCaixa1, txtCaixa2, txtCisterna1;
    //NotificationCompat.Builder notification;
    //private static final int uniqueID = 010101;


    @Override
    protected void onStart() {
        super.onStart();

        setContentView(R.layout.activity_home);
        txtCaixa1 = (EditText) findViewById(R.id.txtCaixa1);
        txtCaixa2 = (EditText) findViewById(R.id.txtCaixa2);
        txtCisterna1 = (EditText) findViewById(R.id.txtCaixa1);

        //notification = new NotificationCompat.Builder(this);
        //notification.setAutoCancel(true);


        //Caixa d'água 1
        WaveLoadingView mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.SQUARE);
        mWaveLoadingView.setProgressValue(Integer.parseInt(caixa.getNivel()));
        mWaveLoadingView.setCenterTitle(caixa.getNivel()+"%");
        mWaveLoadingView.setAmplitudeRatio(40);
        mWaveLoadingView.setTopTitleStrokeWidth(3);
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();

        //Caixa d'água 2
        WaveLoadingView mWaveLoadingView2 = (WaveLoadingView) findViewById(R.id.waveLoadingView2);
        mWaveLoadingView2.setShapeType(WaveLoadingView.ShapeType.SQUARE);
        mWaveLoadingView2.setProgressValue(Integer.parseInt(caixa.getNivel2())); //O quanto o círculo está preenchido
        mWaveLoadingView2.setCenterTitle(caixa.getNivel2()+"%");
        mWaveLoadingView2.setAmplitudeRatio(60);
        mWaveLoadingView2.setTopTitleStrokeWidth(3);
        mWaveLoadingView2.setAnimDuration(3000);
        mWaveLoadingView2.pauseAnimation();
        mWaveLoadingView2.resumeAnimation();
        mWaveLoadingView2.cancelAnimation();
        mWaveLoadingView2.startAnimation();

        //Cisterna 1
        WaveLoadingView mWaveLoadingView3 = (WaveLoadingView) findViewById(R.id.waveLoadingView3);
        mWaveLoadingView3.setShapeType(WaveLoadingView.ShapeType.SQUARE);
        mWaveLoadingView3.setProgressValue(75); //O quanto o círculo está preenchido
        mWaveLoadingView3.setCenterTitle("75%");
        mWaveLoadingView3.setAmplitudeRatio(60);
        mWaveLoadingView3.setTopTitleStrokeWidth(3);
        mWaveLoadingView3.setAnimDuration(3000);
        mWaveLoadingView3.pauseAnimation();
        mWaveLoadingView3.resumeAnimation();
        mWaveLoadingView3.cancelAnimation();
        mWaveLoadingView3.startAnimation();

        Button btn = (Button) findViewById(R.id.btnmedir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new teste().execute();

                // medir(getApplicationContext());

                //notification.setSmallIcon(R.mipmap.ic_launcher);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


          new teste().execute();

    }

    public void medir(Context ctx) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);
        mBuilder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Teste")
                .setContentText("testando")
                .setLights(Color.WHITE, 1000, 5000)
                .setVibrate(new long[]{100, 500, 200, 800})
                .setWhen(System.currentTimeMillis())
                .setContentIntent(criarContent(ctx));

        NotificationManagerCompat nmc = NotificationManagerCompat.from(ctx);
        nmc.notify(340, mBuilder.build());

        //Toast.makeText(this, "Em processo de desenvolvimento.", Toast.LENGTH_LONG).show();
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

    class teste extends AsyncTask<Void, Void, String> {




        @Override
        protected String doInBackground(Void... voids) {

          caixa = new Caixas();
          List<String> valor = new ArrayList<>();


            String result = null;
            try {
                result = HttpRemote.getPost("http://192.168.1.126/php/bye.php", "");
                JSONArray  obj = new JSONArray(result);

                for(int i=0; i < obj.length(); i++) {
                    JSONObject obj2 = obj.getJSONObject(i);

                    valor.add(i,obj2.getString("nivel" + i ));

                }
               // String valor = obj.getString(Integer.parseInt("nivel"));


                caixa.nivel = valor.get(0);
                
                caixa.nivel2 = valor.get(1);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String result) {



        }




    }


}




