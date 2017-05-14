package com.techno.misskeen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paulina on 5/9/2017.
 */
public class Client {
    public static final String BASE_URL = "http://ditoraharjo.co/misskeen/api/v1/test";

    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
