package com.example.weatherapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.models.Forecast;
import com.example.weatherapp.models.ForecastDay;
import com.example.weatherapp.models.Weather;
import com.example.weatherapp.recyclerview.ForecastAdapter;

import java.math.BigDecimal;

public class ForecastFragment extends Fragment {
    //create class variable for the view
    View view;
    Weather weather;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forecast, container, false);

        //setting the local weather object to the passed data bundle.
        weather = (Weather) getArguments().getSerializable("weather"); //getting the object then casted to weather object.

        if(weather != null) { //check if weather object is null
            //setup the Forecast RecyclerView
            ForecastDay[] forecastDays = weather.getForecast().getForecastDays();

            RecyclerView recyclerView = view.findViewById(R.id.RecyclerViewForecast);
            ForecastAdapter forecastAdapter = new ForecastAdapter(forecastDays,view,getActivity().getApplicationContext());
            recyclerView.setAdapter(forecastAdapter);
        }


        return view;
    }

    private String getFloatRoundedAsString(float num){
        return BigDecimal.valueOf(num).setScale(0,BigDecimal.ROUND_HALF_UP).toString();
    }
}