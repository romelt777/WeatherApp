package com.example.weatherapp.models;

public class Location {
    private String name;
    private String region;
    private String country;

    public Location(String n,String r, String c){
        this.name = n;
        this.region = r;
        this.country =c;
    }
    public String getName(){return name;};

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }
}
