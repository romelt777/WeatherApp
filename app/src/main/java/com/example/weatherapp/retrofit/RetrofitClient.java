package com.example.weatherapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //get instance of client, load api interface.

    //creating class variable
    private static RetrofitClient instance = null;

    private RetrofitApi api;

    //constructor.
    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api.BASE_URL) //base url
                .addConverterFactory(GsonConverterFactory.create()) //convert json
                .build();
        api = retrofit.create(RetrofitApi.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null){
            instance = new RetrofitClient(); //creating client just in case null.
        }
        return instance;
    }

    //method to get api
    public RetrofitApi getApi(){
        return api;
    }


}
