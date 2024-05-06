package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.fragments.CurrentFragment;
import com.example.weatherapp.fragments.ForecastFragment;
import com.example.weatherapp.models.Location;
import com.example.weatherapp.models.Weather;
import com.example.weatherapp.retrofit.RetrofitClient;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    //initializing fragments
    private CurrentFragment currentFragment;
    private ForecastFragment forecastFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //set view to a variable to use.
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        //get location from startActivity intent
        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude",0);
        double longitude = intent.getDoubleExtra("longitude",0);

        //setting clicking action on nav bar
        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_nav);

        //RETROFIT CLIENT TO GET WEATHER

//        String currentLocation = "44.6671142,-63.6075769";
        String currentLocation = latitude + "," + longitude;


        //create and initializes  the api client.
        Call<Weather> call = RetrofitClient.getInstance().getApi().getWeather(
                "386ec812a55649e2999174049231110",
                currentLocation,
                "3",
                "no",
                "no"
        );

        //make the api call,this is async call.
        call.enqueue(new Callback<Weather>() {//this is a new thread.
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();

                if(weather != null){ // check if weather is null
                    Log.i("TEST:",weather.getLocation().getName());
                    Log.i("test2", "length: " + weather.getForecast().getForecastDays().length);
                    Log.i("test3", "date: " + weather.getForecast().getForecastDays()[0].getDate());
                    Log.i("test3", "date: " + weather.getForecast().getForecastDays()[0].getDay().getMaxTemp());


                    //update location in activity layout c\\
                    DisplayLocation(weather.getLocation());

                    //setupfragment

                    //helper class to package data then pass data to and from fragments and main activity.
                    Bundle bundle = new Bundle();
                    //searialize object: convert to base64 then convert back to move around objects.
                    //add weather object to bundle
                    bundle.putSerializable("weather",weather); // needs key value pair, able to put more than one bundle.

                    //setup fragments
                    currentFragment = new CurrentFragment(); //initializing fragment.
                    //passing bundle of data to the currentFragment class
                    currentFragment.setArguments(bundle);

                    forecastFragment = new ForecastFragment();
                    forecastFragment.setArguments(bundle);

                    //setting default to current
                    bottomNavigationView.setSelectedItemId(R.id.navigation_current);
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.i("ERROR:",t.toString()); //printing error
            }
        });

//
//
//        String json = getJsonFromFile();
//        //checking if we can get the json file from raw.
//        Log.i("TESTING the world",json);
//
//        //use GSON to parse json string
//        Gson gson = new Gson();
//
//        //create weather object
//        //gson.fromJson needs json file, and where to deserialize to.
//        Weather weather = gson.fromJson(json,Weather.class);

///navbar
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();

                if(itemId == R.id.navigation_current){
                    //creating container to swap through multiple fragments, tell which fragment to display
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout,currentFragment)// set the Main framelayout to the current fragment.
                            .commit();
                    return true;
                }
                if (itemId == R.id.navigation_forecast) {
                    //creating container to swap through multiple fragments, tell which fragment to display
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout,forecastFragment)
                            .commit();
                    return true;
                }

                return false;
            }
        });
    }

    //method to display location
    private void DisplayLocation(Location location){
        //display the location name
        //making array from resource strings
//        String[] locationArray = getResources().getStringArray(R.array.provinces);
//        //placeing array in hashmap using function
//        HashMap<String,String> locationHash = getHashFromStringArray(locationArray);
//        //getting region
//        String region = location.getRegion();
//        //getting abbreviated region. giving the hash a key value(province name), will return abbreviation.
//        String abbrev = locationHash.get(region);


        //getting the textview to display the data for location.
        TextView textViewLocationName = findViewById(R.id.textViewLocationName);
        String fullLocation = location.getName();
        if(location.getRegion() != null){
            fullLocation +=  ", " + location.getRegion();
        }
        if(location.getCountry() != null){
            fullLocation += ", " + location.getCountry();
        }
        textViewLocationName.setText(fullLocation);
    }

    //get json string from .json file
    private String getJsonFromFile() {
        String json = "";

        InputStream inputStream = this.getResources().openRawResource(R.raw.weather_api);

        // Create InputStreamReader object
        InputStreamReader isReader = new InputStreamReader(inputStream);

        // Create a BufferedReader object
        BufferedReader reader = new BufferedReader(isReader);

        // Read the buffer and save to string
        json = reader.lines().collect(Collectors.joining(System.lineSeparator()));

        return json;
    }
    // Convert province string array into map<k,v>.
    private HashMap<String, String> getHashFromStringArray(String[] array) {
        //splitting array and placing in as key,value pairs.
        HashMap<String, String> result = new HashMap<>();
        for (String str : array) {
            // e.g. ON, Ontario
            String[] splitItem = str.split(",");
            result.put(splitItem[1], splitItem[0]);
        }
        return result;
    }

    private String getFloatRoundedAsString(float num){
        return BigDecimal.valueOf(num).setScale(0,BigDecimal.ROUND_HALF_UP).toString();
    }
}