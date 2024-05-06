package com.example.weatherapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.models.Weather;

import java.math.BigDecimal;
import java.util.HashMap;

public class CurrentFragment extends Fragment {
    View view;
    Weather weather;
    public CurrentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { //returning a view: itself?
        // Inflate the layout for this fragment
        //saving the view itself to a variable.
        view = inflater.inflate(R.layout.fragment_current, container, false);

        //setting the local weather object to the passed data bundle.
        weather = (Weather) getArguments().getSerializable("weather"); //getting the object then casted to weather object.

        if(weather != null){ //check if weather object is null

            //Display the temperature.
            TextView textViewTemperature = view.findViewById(R.id.textViewTemperature); //change to binding.textViewTemperature later.
            String temperature = (String.valueOf(getFloatRoundedAsString(weather.getCurrent().getTemperature())) + "°C");
            textViewTemperature.setText(temperature);

            //display condition description
            TextView textViewDescription = view.findViewById(R.id.textViewDescription);
            textViewDescription.setText((weather.getCurrent().getCondition().getText()));

            //display the weather icon
            ImageView imageView = view.findViewById(R.id.imageViewIcon);
            //need to use glide to load the image. needs view of activity main layout.
            String imageUrl = "https:" + weather.getCurrent().getCondition().getIcon();
            Glide.with(view).load(imageUrl).into(imageView);


            //display feels Like
            TextView textViewFeelsLike = view.findViewById(R.id.textViewFeelsLike); //change to binding.textViewTemperature later.
            String tempFeelsLike = (String.valueOf("Feels like: "+ getFloatRoundedAsString(weather.getCurrent().getFeelsLike()) + "°C"));
            textViewFeelsLike.setText(tempFeelsLike);

            //displays wind
            TextView textViewWind = view.findViewById(R.id.textViewWind); //change to binding.textViewTemperature later.
            String windString = (String.valueOf("Wind: "+ weather.getCurrent().getWindDir() + " "+ getFloatRoundedAsString(weather.getCurrent().getWindSpeed())+ " kph"));
            textViewWind.setText(windString);


        }


        return view;
    }

    private String getFloatRoundedAsString(float num){
        return BigDecimal.valueOf(num).setScale(0,BigDecimal.ROUND_HALF_UP).toString();
    }
}