package com.example.github.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBase {
    public static Retrofit retrofit =null;

    public static Retrofit getData(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/search/")
                    .addConverterFactory(GsonConverterFactory.create() )
                    .build();
        }
        return retrofit;
    }
}
