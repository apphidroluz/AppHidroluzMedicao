package com.example.comercial_medicao.androidnovo;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import me.itangqi.waveloadingview.WaveLoadingView;

public class HomeActivity extends AppCompatActivity {

    Calendar calender;
    SimpleDateFormat simpleDateFormat;
    String Date;
    TextView viewDateTime;

    Caixas caixa;
    StringBuilder sb;
    private Button btn;
    private boolean mediu = false;
    private static final String PREF_NAME = "dadoslogin";

    EditText txtCaixa1, txtCaixa2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        /*atualizaData = (TextView) findViewById(R.id.atualizaData);
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        calendar = Calendar.getInstance();
        attData = simpleDateFormat.format(calendar.getTime());
        Log.e("teste", attData);
        atualizaData.setText(attData);*/




          //  ThredArduino();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_home);
        txtCaixa1 = (EditText) findViewById(R.id.txtCaixa1);
        txtCaixa2 = (EditText) findViewById(R.id.txtCaixa2);

        viewDateTime = (TextView) findViewById(R.id.viewDateTime);
        calender = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date =simpleDateFormat.format(calender.getTime());

        viewDateTime.setText(Date);

        if(caixa.getNivel() != null) {

            if (!VerifDados.filter_nivel(caixa.getNivel()) || !VerifDados.filter_nivel(caixa.getNivel2())) {

                Intent it = new Intent(
                        HomeActivity.this,
                        Tela_erro.class);
                startActivity(it);

            }else{

                //Caixa d'água 1
                WaveLoadingView mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
                mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.SQUARE);
                if (Integer.parseInt(caixa.getNivel()) == 100) {
                    mWaveLoadingView.setProgressValue(97);
                } else if (Integer.parseInt(caixa.getNivel()) > 100) {
                    mWaveLoadingView.setProgressValue(103);
                } else {
                    mWaveLoadingView.setProgressValue(Integer.parseInt(caixa.getNivel()));
                }
                if (Integer.parseInt(caixa.getNivel()) > 100) {
                    mWaveLoadingView.setCenterTitle("Atenção");
                } else {
                    mWaveLoadingView.setCenterTitle(caixa.getNivel() + "%");
                }
                mWaveLoadingView.setAmplitudeRatio(60);
                mWaveLoadingView.setTopTitleStrokeWidth(3);
                mWaveLoadingView.setAnimDuration(3000);
                if (Integer.parseInt(caixa.getNivel()) < 49 || Integer.parseInt(caixa.getNivel()) > 100) {
                    mWaveLoadingView.setWaveColor(getColor(R.color.critico));
                } else {
                    mWaveLoadingView.setWaveColor(getColor(R.color.azul_w));
                }


                //Caixa d'água 2
            WaveLoadingView mWaveLoadingView2 = (WaveLoadingView) findViewById(R.id.waveLoadingView2);
                mWaveLoadingView2.setShapeType(WaveLoadingView.ShapeType.SQUARE);
                if (Integer.parseInt(caixa.getNivel2()) == 100) {
                    mWaveLoadingView2.setProgressValue(97);
                } else if (Integer.parseInt(caixa.getNivel2()) > 100) {
                    mWaveLoadingView2.setProgressValue(103);
                } else {
                    mWaveLoadingView2.setProgressValue(Integer.parseInt(caixa.getNivel2()));
                }
                if (Integer.parseInt(caixa.getNivel2()) > 100) {
                    mWaveLoadingView2.setCenterTitle("Atenção");
                } else {
                    mWaveLoadingView2.setCenterTitle(caixa.getNivel2() + "%");
                }
                mWaveLoadingView2.setAmplitudeRatio(60);
                mWaveLoadingView2.setTopTitleStrokeWidth(3);
                mWaveLoadingView2.setAnimDuration(3000);
                if (Integer.parseInt(caixa.getNivel2()) < 49 || Integer.parseInt(caixa.getNivel2()) > 100) {
                    mWaveLoadingView2.setWaveColor(getColor(R.color.critico));
                } else {
                    mWaveLoadingView2.setWaveColor(getColor(R.color.azul_w));
                }

            }
        }
    }

    public void medir(View v) {

        ConexaoTest funcao = new ConexaoTest();
        if (!funcao.netWorkdisponibilidade(this)) {


            Toast.makeText(this, "Sem conexão com rede, verifique seu sinal",
                    Toast.LENGTH_LONG).show();

            Intent it = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(it);


        }else {

            Intent mIt  = getIntent();
            finish();
            startActivity(mIt);
        }


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
        onDetach();
    }

    public void onDetach() {
            new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.SUCCESS_TYPE).
                    setTitleText("Saida").setContentText("Você realmente deseja sair ?")
                    .setConfirmText("Sim").setCancelText("Não")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            SharedPreferences settings = getSharedPreferences(
                                    PREF_NAME, 0);
                            settings.edit().remove("logado").commit();

                            HomeActivity.this.finish();


                                startActivity(new Intent(getBaseContext(),
                                        MainActivity.class));

                        }
                    }).showCancelButton(true).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    sDialog.cancel();
                }
            }).show();
        /*Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);
        finish();*/

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

               onDetach();
        }
        return false;
    }

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

    static class VerifDados {
        public static Boolean validaNivel(String u) {
            return Boolean.valueOf(Pattern.compile("[0-9]{1,3}").matcher(u).matches());
        }

        public static boolean filter_nivel(String edt) {
            if (validaNivel(edt).booleanValue()) {
                return true;
            }
            return false;
        }
    }

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
                   Thread.sleep(100);
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


}




