package com.example.comercial_medicao.androidnovo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends Activity {

    private static final String PREF_NAME = "dadoslogin";
    private String login;
    private String senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREF_NAME,
                MODE_PRIVATE);
        String logado = settings.getString("logado", "0");
        String login = settings.getString("login", "");
        String senha = settings.getString("senha", "");

        EditText lclogin = (EditText) findViewById(R.id.editUser);
        EditText lcsenha = (EditText) findViewById(R.id.editPass);

        lclogin.setText(login);
        lcsenha.setText(senha);

        if (logado.equals("1")) {
            Intent it = new Intent(getApplicationContext(),
                    HomeActivity.class);
            startActivity(it);
        }
    }

    public void logar() throws InterruptedException {

        EditText lclogin = (EditText) findViewById(R.id.editUser);
        EditText lcsenha = (EditText) findViewById(R.id.editPass);
        login = lclogin.getText().toString();
        senha = lcsenha.getText().toString();

        if (login.equals("Villa") && senha.equals("1600")){

            SharedPreferences settings = getSharedPreferences(
                    PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();

             editor.putString("logado", "1");
             editor.putString("login", login);
             editor.putString("senha", senha);

            editor.commit();

            Intent it = new Intent(getApplicationContext(), HomeActivity.class);

            startActivity(it);

            finish();

        }else{

            Toast.makeText(this, "Login incorreto",
                    Toast.LENGTH_LONG).show();

        }

    }

    public void entrar(View v) {

        ConexaoTest funcao = new ConexaoTest();
        if (!funcao.netWorkdisponibilidade(this)) {
            Toast.makeText(this, "Sem conexão com rede, verifique seu sinal",
                    Toast.LENGTH_LONG).show();
        }else{

            final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this,
                    SweetAlertDialog.PROGRESS_TYPE);
            pDialog.setTitleText("Aguarde !!!");
            pDialog.setContentText("Processando login.....");
            pDialog.setCancelable(false);
            pDialog.show();

            new Thread() {
                public void run() {
                    try {

                        logar();
                    } catch (Exception e) {
                        Log.e("tag", e.getMessage());
                    }
                    // encerra progress dialog
                    pDialog.dismiss();
                }
            }.start();

        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            finish();
        }
        return false;
    }

}
