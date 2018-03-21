package com.example.comercial_medicao.androidnovo;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRemote {
    public String getPost(String strurl, String param) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(strurl).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        PrintWriter print = new PrintWriter(connection.getOutputStream());
        print.print(param);
        print.close();
        connection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String str = "";
        while (true) {
            str = bufferedReader.readLine();
            if (str == null) {
                return sb.toString();
            }
            sb.append(str);
        }
    }
}
