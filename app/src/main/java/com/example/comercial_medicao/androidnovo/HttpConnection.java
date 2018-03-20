package com.example.comercial_medicao.androidnovo;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class  HttpConnection {

    public static final String API_BASE_URL = "http://http://192.168.0.234/php/bye.php";

    public static <S> S createService(Class<S> serviceClass) {

        //Instancia do interceptador das requisições
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS);

        httpClient.addInterceptor(loggingInterceptor);


        //Instância do retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(httpClient.build())
                .build();

        return retrofit.create(serviceClass);
    }

    public interface RetrofitService {

        @FormUrlEncoded
        @POST("bye.php")
        Call<HttpConnection> converterUnidade(@Field("nivel0") String nivel0,
                                              @Field("nivel1") String nivel1,
                                              @Field("nivel2") String nivel2,
                                              @Field("nivel3") String nivel3,
                                              @Field("nivel5") String nivel4);

    }

}
