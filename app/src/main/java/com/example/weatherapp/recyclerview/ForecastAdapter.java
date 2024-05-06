package com.example.weatherapp.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.models.ForecastDay;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

//bring data from weather object to recycler view
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    //implement 3 methods as part of the adapter.

    private ForecastDay[] forecastDays;
    private View view;
    private Context context;

    //constructor


    public ForecastAdapter(ForecastDay[] forecastDays, View view, Context context) {
        this.forecastDays = forecastDays;
        this.view = view;
        this.context = context;
    }

    //ViewHolder contains all the views inside of the layout_forecast.
    public static class ViewHolder extends RecyclerView.ViewHolder{ //viewHolder holds views.
        private final TextView textViewForecastMaxTemp;
        private final ImageView imageView;
        private final TextView textViewForecastMinTemp;
        private final TextView textViewDate;
        private final TextView description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewForecastMaxTemp = itemView.findViewById(R.id.textViewForecastMaxTemp);
            this.imageView = itemView.findViewById(R.id.imageViewForecast);
            this.textViewForecastMinTemp = itemView.findViewById(R.id.textViewForecastMinTemp);
            this.textViewDate = itemView.findViewById(R.id.textViewForecastDate);
            this.description = itemView.findViewById(R.id.textViewForecastDescription);

        }

        public TextView getTextViewDate() {
            return textViewDate;
        }

        public TextView getDescription() {
            return description;
        }

        public TextView getTextViewForecastMinTemp() {
            return textViewForecastMinTemp;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewForecastMaxTemp() {
            return textViewForecastMaxTemp;
        }
    }

    @NonNull
    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set up and return the view
        //this function gets called on create, will pass the view to the constructor when object is built

        ///boiler plate code:
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_forecast,parent,false);


        return new ViewHolder(view); //return viewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ViewHolder holder, int position) {
        //int position: tells where we are in the collection: we have 3 forecast days.
        //take data from weather object, than plug into views.

        ForecastDay forecastDay = this.forecastDays[position];

        //MAXTEMP
        //getting info
        float maxTemp = forecastDay.getDay().getMaxTemp();
        String strMaxTemp = Math.round(maxTemp) + "°C High";
        //setting info to view
        holder.getTextViewForecastMaxTemp().setText(strMaxTemp);

        //MINTEMP
        float minTemp = forecastDay.getDay().getMinTemp();
        String strMinTemp = Math.round(minTemp) + "°C Low";
        //setting info to view
        holder.getTextViewForecastMinTemp().setText(strMinTemp);

        //icon image
        String imageUrl = "https://" + forecastDay.getDay().getCondition().getIcon();
        ImageView imageView = holder.getImageView();
        Glide.with(view).load(imageUrl).into(imageView);

        //Date
        String dateStr = forecastDay.getDate();
        Date date = Date.valueOf(dateStr);
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM", Locale.CANADA);
        holder.getTextViewDate().setText(dateFormat.format(date));

        //description
        String description = forecastDay.getDay().getCondition().getText() + ".";
        int chanceRain = forecastDay.getDay().getChanceRain();
        float amountRain = forecastDay.getDay().getAmountRain();
        int chanceSnow = forecastDay.getDay().getChanceSnow();
        float amountSnow = forecastDay.getDay().getAmountSnow();

        if(chanceRain > 0){
            description += " " + chanceRain + "%. Amount " + Math.round(amountRain) + "mm.";
        }

        if(chanceSnow > 0){
            description += " " + chanceSnow + "%. Amount " + Math.round(amountSnow) + "cm.";
        }

        description += " Maximum winds " + forecastDay.getDay().getMaxWind();
        description += ". Humidity " + forecastDay.getDay().getHumidity() + "%.";
        holder.getDescription().setText(description);


    }

    @Override
    public int getItemCount() {
        return forecastDays.length; //return length of forecastdays array.
    }
}
