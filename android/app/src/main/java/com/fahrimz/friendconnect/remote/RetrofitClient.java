package com.fahrimz.friendconnect.remote;

import com.fahrimz.friendconnect.PrefManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    PrefManager pref;

    public static Retrofit getClient(String url){

        if(retrofit == null){
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.level(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .addNetworkInterceptor(logInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
