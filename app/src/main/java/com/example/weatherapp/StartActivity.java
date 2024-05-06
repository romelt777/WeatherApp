package com.example.weatherapp;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class StartActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //initialized provider
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults){
        //must call super if overrided function
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        //verify the permission granted
        if(requestCode == REQUEST_CODE){
            //only one request, so length should be one
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //now calling getLocation because the location should be granted
                getLocation();
            }
        }
    }

    private void getLocation(){
        //contextCompat will check if app has permission,then check if granted
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //permission granted - get location from device
            Log.i("TESTING","Permission granted!!!");

            fusedLocationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY,null)
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            //android convention to pass one activiity to another.
                            //1.click button --> open browser, (let phone determine intent of browser)
                            //2.click --> explicity intent, opens another activity/module in your app

                            //needs context then need activity its going to invoke.
                            Intent intent = new Intent(StartActivity.this,MainActivity.class);
                            //similar to bundle, passing data to activity.
                            intent.putExtra("latitude",latitude);
                            intent.putExtra("longitude",longitude);
                            startActivity(intent);
                        }
                    });
        } else{
            //permission denied - ask user for permission
            askPermission(); //could add loop to keep asking permissions, then after certain number exitapp/error out
        }
    }

    //displays screen to request permission
    private void askPermission(){
        //this method can request multiple permissions, thats why an array
        ActivityCompat.requestPermissions(
                this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_CODE);
    }
}