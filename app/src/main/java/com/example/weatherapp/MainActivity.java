package com.example.weatherapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.model.Coord;
import com.example.weatherapp.model.ForecastRequest;
import com.example.weatherapp.model.WeatherRequest;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "myApp";
    private SharedPreferences sharedPreferences;
    private OpenWeatherRequester openWeatherRequester;
    private MyLocation myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate");

        initLocation();
        initRetrofit();
        initGui();

//        openWeatherRequester.requestForecast(55.75222, 37.615555);
    }

    private void initGui() {
    }

    private void initRetrofit() {
        openWeatherRequester = new OpenWeatherRequester(new OpenWeatherRequester.OnGetWeather() {
            @Override
            public void onGetCurrent(WeatherRequest currentWeather) {
                ((TextView) findViewById(R.id.text_city_name)).setText(currentWeather.getName());
            }

            @Override
            public void onGetForecast(ForecastRequest forecastWeather) {
                ((TextView) findViewById(R.id.text_city_name)).setText(forecastWeather.getCity().getName());
            }

            @Override
            public void onErrorGetWeather(Throwable t) {
                ((TextView) findViewById(R.id.text_city_name)).setText(t.getMessage());
            }
        });
    }


    private void initLocation() {
        Log.d(TAG, "init location");
        myLocation = new MyLocation(MainActivity.this, new MyLocation.OnCoordinatesChanges() {
            @Override
            public void onCoordinatesChanges(Coord coord) {
                openWeatherRequester.requestCurrent(coord.getLat(), coord.getLon());
                Toast.makeText(MainActivity.this, "request " + coord.getLat() + ", " + coord.getLon(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        myLocation.stop();
    }
}
