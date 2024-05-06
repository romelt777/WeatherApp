package com.example.weatherapp.models;

import java.io.Serializable;

public class Weather implements Serializable {//making class serializable

    private Location location;
    private Current current;
    private Forecast forecast;

    public Weather(Location l, Current c, Forecast f){
        this.location = l;
        this.current = c;
        this.forecast = f;
    }

    public Location getLocation(){ return location;};
    public Current getCurrent(){return current;};
    public Forecast getForecast() {
        return forecast;
    }
}
