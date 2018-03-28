package com.example.comercial_medicao.androidnovo;

import android.os.AsyncTask;
import android.util.Log;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DadosArduino extends AsyncTask{

    Caixas caixa;
    private boolean mediu = false;

    // transformar os dados que são obtidos em itens para serem acessados na outra classe...
    // lista ou dados individuais?!

    @Override
    protected Object doInBackground(Object[] objects) {
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
            caixa.nivel = valor.get(0).replaceAll("^0","");
            caixa.nivel2 = valor.get(1).replaceAll("^0","");
            Log.e("teste",caixa.getNivel());
            mediu = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valor;
    }



    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
