package com.example.comercial_medicao.androidnovo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void logar() throws InterruptedException {

        EditText lclogin = (EditText) findViewById(R.id.editUser);
        EditText lcsenha = (EditText) findViewById(R.id.editPass);
        String login = lclogin.getText().toString();
        String senha = lcsenha.getText().toString();

        if (login.equals("hidroluz") && senha.equals("1234")){
            Intent it = new Intent(getApplicationContext(), HomeActivity.class);

            startActivity(it);

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

}
