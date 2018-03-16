package com.example.comercial_medicao.androidnovo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

    public void entrar(View v) {

        EditText lclogin = (EditText) findViewById(R.id.editUser);
        EditText lcsenha = (EditText) findViewById(R.id.editPass);
        String login = lclogin.getText().toString();
        String senha = lcsenha.getText().toString();

        if(login.equals("hidroluz") && senha.equals("1234")){
            Intent it = new Intent(getApplicationContext(), HomeActivity.class);
            final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this,
                    SweetAlertDialog.PROGRESS_TYPE);
            pDialog.setTitleText("Aguarde !!!");
            pDialog.setContentText("Processando login.....");
            pDialog.setCancelable(false);
            pDialog.show();

            startActivity(it);

            pDialog.dismiss();


        }else{


            Toast.makeText(this, "Login incorreto",
                    Toast.LENGTH_LONG).show();

        }

    }



}
