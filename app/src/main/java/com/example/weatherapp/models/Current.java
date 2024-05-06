package com.example.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class Current {
    //temp does not match name in json, will use gson feature to serialize name to bind together.
    @SerializedName("temp_c") // now json temp_c will be bound to temperature.
    private float temperature;
    @SerializedName("feelslike_c")
    private float feelsLike;
    @SerializedName("wind_dir")

    private String windDir;
    @SerializedName("gust_kph")
    private float windSpeed;


    //adding condition model to current, because condtion is under current in json file.
    private Condition condition;

    public Current(float t, Condition c, float feelsLike, String windDir, float windSpeed){
        this.temperature = t;
        this.condition = c;
        this.feelsLike = feelsLike;
        this.windDir = windDir;
        this.windSpeed = windSpeed;
    }
    public float getTemperature() {
        return temperature;
    }

    public Condition getCondition() {
        return condition;
    }

    public float getFeelsLike() {
        return feelsLike;
    }

    public String getWindDir() {
        return windDir;
    }

    public float getWindSpeed() {
        return windSpeed;
    }
}
