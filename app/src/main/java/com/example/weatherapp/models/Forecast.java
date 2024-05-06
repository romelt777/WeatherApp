package com.example.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    //mapping to proper json value.
    @SerializedName("forecastday")
    private ForecastDay[] forecastDays;

    public Forecast (ForecastDay[] forecastDays){
        this.forecastDays = forecastDays;
    }

    public ForecastDay[] getForecastDays() {
        return forecastDays;
    }
}
