package com.example.comercial_medicao.androidnovo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import me.itangqi.waveloadingview.WaveLoadingView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends Activity {

    Conexao resposta = new Conexao();
    ProgressDialog progress;

    EditText txtCaixa1, txtCaixa2, txtCisterna1;
    //NotificationCompat.Builder notification;
    //private static final int uniqueID = 010101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        txtCaixa1 = (EditText) findViewById(R.id.txtCaixa1);
        txtCaixa2 = (EditText) findViewById(R.id.txtCaixa2);
        txtCisterna1 = (EditText) findViewById(R.id.txtCaixa1);

        //notification = new NotificationCompat.Builder(this);
        //notification.setAutoCancel(true);


        //Caixa d'água 1


        WaveLoadingView mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.RECTANGLE);
        mWaveLoadingView.setProgressValue(100);
        mWaveLoadingView.setCenterTitle("100%");
        mWaveLoadingView.setAmplitudeRatio(40);
        mWaveLoadingView.setTopTitleStrokeWidth(3);
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();

        //Caixa d'água 2
        WaveLoadingView mWaveLoadingView2 = (WaveLoadingView) findViewById(R.id.waveLoadingView2);
        mWaveLoadingView2.setShapeType(WaveLoadingView.ShapeType.RECTANGLE);
        mWaveLoadingView2.setProgressValue(25); //O quanto o círculo está preenchido
        mWaveLoadingView2.setCenterTitle("25%");
        mWaveLoadingView2.setAmplitudeRatio(60);
        mWaveLoadingView2.setTopTitleStrokeWidth(3);
        mWaveLoadingView2.setAnimDuration(3000);
        mWaveLoadingView2.pauseAnimation();
        mWaveLoadingView2.resumeAnimation();
        mWaveLoadingView2.cancelAnimation();
        mWaveLoadingView2.startAnimation();

        //Cisterna 1
        WaveLoadingView mWaveLoadingView3 = (WaveLoadingView) findViewById(R.id.waveLoadingView3);
        mWaveLoadingView3.setShapeType(WaveLoadingView.ShapeType.RECTANGLE);
        mWaveLoadingView3.setProgressValue(75); //O quanto o círculo está preenchido
        mWaveLoadingView3.setCenterTitle("75%");
        mWaveLoadingView3.setAmplitudeRatio(60);
        mWaveLoadingView3.setTopTitleStrokeWidth(3);
        mWaveLoadingView3.setAnimDuration(3000);
        mWaveLoadingView3.pauseAnimation();
        mWaveLoadingView3.resumeAnimation();
        mWaveLoadingView3.cancelAnimation();
        mWaveLoadingView3.startAnimation();


    }


    public void medir(View v){
        Toast.makeText(this, "Em processo de construção.", Toast.LENGTH_LONG).show();
    }

    public void sair(View v){
        //Toast.makeText(this, "Ainda não é possivel sair.", Toast.LENGTH_LONG).show();
        //notification.setSmallIcon(R.mipmap.ic_launcher);
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);
        finish();
    }



   /* Conexao service = (Conexao) HttpConnection.createService(HttpConnection.RetrofitService.class);



            public void onResponse(Call<Conexao> call, Response<Conexao> response) {

                if (response.isSuccessful()) {

                    Conexao respostaServidor = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (respostaServidor != null) {


                        resposta.setNivel0(respostaServidor.getNivel0());
                        resposta.setNivel1(respostaServidor.getNivel1());
                        resposta.setNivel2(respostaServidor.getNivel2());
                        resposta.setNivel3(respostaServidor.getNivel3());
                        resposta.setNivel4(respostaServidor.getNivel4());

                        progress.dismiss();


                    } else {

                        Toast.makeText(getApplicationContext(), "Insira unidade e valores válidos", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                }

            }
*/

        }




