package com.example.weatherapp.retrofit;

import com.example.weatherapp.models.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApi {
    //this is the hostname for api

    String BASE_URL = "https://api.weatherapi.com"; //base url of api call.

    //api  to get the forecast from the API.
    @GET("v1/forecast.json")
    Call<Weather> getWeather(@Query("key") String key,
                             @Query("q") String q,
                             @Query("days") String days,
                             @Query("aqi") String aqi,
                             @Query("alerts") String alerts
    );



}
