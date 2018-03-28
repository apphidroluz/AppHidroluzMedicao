package com.example.comercial_medicao.androidnovo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;

public class ConexaoTest {

    public boolean netWorkdisponibilidade(Context cont) {
        @SuppressLint("WrongConstant") ConnectivityManager conmag = (ConnectivityManager) cont.getSystemService("connectivity");
        conmag.getActiveNetworkInfo();
        if (conmag.getNetworkInfo(1).isConnected()) {
            return true;
        }
        if (conmag.getNetworkInfo(0).isConnected()) {
            return true;
        }
        return false;
    }

}