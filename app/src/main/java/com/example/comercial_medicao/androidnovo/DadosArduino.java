package com.example.comercial_medicao.androidnovo;

import android.os.AsyncTask;
import android.util.Log;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DadosArduino extends  AsyncTask{

    // Não esrevi o código dessa classe, foi uma cópia do que estava na SubClasse para começarmos a
    // fazer as modificações que forem aparecendo...

    // transformar os dados que são obtidos em itens para serem acessados na outra classe...
    // lista ou dados individuais?!

    @Override
    protected Object doInBackground(Object[] objects) {
        List<String> valores = new ArrayList<>();
        String result = null;
        try {
            result = HttpRemote.getPost("http://www.android.hidroluz.com.br/php/bye.php", "");
            JSONArray obj = new JSONArray(result);
            for(int i=0; i < obj.length(); i++) {
                JSONObject obj2 = obj.getJSONObject(i);
                valores.add(i,obj2.getString("nivel" + i ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return valores;
    }


    public static void main(String[] args) {

        List<String> valores = new ArrayList<>();


      new DadosArduino().execute();

    Log.e("Resultado", valores.get(0));

    }



}
