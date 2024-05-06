package com.example.weatherapp.models;

public class Condition {
    private String text;
    private String icon;

    //GSON is using classes, passing in arguments, then deserializing the json.
    public Condition(String t,String i){
        this.text =t;
        this.icon =i;
    }

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }
}
