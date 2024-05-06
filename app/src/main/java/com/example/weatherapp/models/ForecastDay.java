package com.example.weatherapp.models;

public class ForecastDay {
    private String date;
    private Day day;

    public ForecastDay(String date, Day day) {
        this.date = date;
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public Day getDay() {
        return day;
    }
}
