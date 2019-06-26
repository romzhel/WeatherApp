package com.example.weatherapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.model.Coord;
import com.example.weatherapp.model.ForecastRequest;
import com.example.weatherapp.model.Main;
import com.example.weatherapp.model.WeatherRequest;
import com.example.weatherapp.model.Wind;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "myApp";
    public static final String SHARED_PREF_KEY = "city_id";
    private SharedPreferences sharedPreferences;
    private OpenWeatherRequester openWeatherRequester;
    private MyLocation myLocation;

    private TextView selectedCity;
    private TextView weatherDateTime;
    private ImageView weatherIcon;
    private TextView temperature;
    private TextView weatherDescription;
    private TextView clouds;
    private TextView windSpeed;
    private TextView windDegree;
    private TextView humidity;
    private TextView pressure;

    private RecyclerView forecastRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        Log.d(TAG, "onCreate");

        initGui();
        initSharedReference();
        initRetrofit();
        initData();
    }

    private void initData() {
        long cityId = sharedPreferences.getLong(SHARED_PREF_KEY, -1);
        if (cityId != -1) {
            Log.d(TAG, "get previous city " + cityId);
            openWeatherRequester.requestCurrent(cityId);
            openWeatherRequester.requestForecast(cityId);
        } else {
            initLocation();
        }
    }

    private void initSharedReference() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
    }

    private void initGui() {
        selectedCity = (TextView) findViewById(R.id.text_city_name);
        weatherDateTime = (TextView) findViewById(R.id.text_weather_date);
        weatherIcon = (ImageView) findViewById(R.id.weather_icon);
        temperature = (TextView) findViewById(R.id.temperature);
        weatherDescription = (TextView) findViewById(R.id.text_weather_description);
        windSpeed = (TextView) findViewById(R.id.text_wind_speed);
        windDegree = (TextView) findViewById(R.id.text_wind_degree);
        humidity = (TextView) findViewById(R.id.text_humidity);
        pressure = (TextView) findViewById(R.id.text_pressure);
        clouds = (TextView) findViewById(R.id.text_clouds);

        forecastRecycleView = (RecyclerView) findViewById(R.id.forecast_cecycle_view);
    }

    private void initRetrofit() {
        openWeatherRequester = new OpenWeatherRequester(new OpenWeatherRequester.OnGetWeather() {
            @Override
            public void onGetCurrent(WeatherRequest currentWeather) {
                Log.d(TAG, "current weather data were received");
                selectedCity.setText(currentWeather.getName());
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                weatherDateTime.setText(sdf.format(Calendar.getInstance().getTime()));
                Picasso.get()
                        .load("https://openweathermap.org/img/w/" + currentWeather.getWeather()[0].getIcon() + ".png")
                        .into(weatherIcon);
                temperature.setText(String.format(getResources().getString(R.string.temperature), Main.getTempWithSign(currentWeather.getMain().getTemp())));

                String weatherDesc = currentWeather.getWeather()[0].getDescription().substring(0, 1).toUpperCase() +
                        currentWeather.getWeather()[0].getDescription().substring(1);
                weatherDescription.setText(weatherDesc);
                clouds.setText(String.format(getResources().getString(R.string.cloudy), String.valueOf(currentWeather.getClouds().getAll())));
                windSpeed.setText(String.format(getResources().getString(R.string.wind_speed), Math.round(currentWeather.getWind().getSpeed())));
                windDegree.setText(Wind.getWindDirection(currentWeather.getWind().getDeg()));
                humidity.setText(String.format(getResources().getString(R.string.humidity), currentWeather.getMain().getHumidity()));
                pressure.setText(String.format(getResources().getString(R.string.pressure), Math.round(currentWeather.getMain().getPressure() / 1.333)));

                saveCityId(currentWeather);
            }

            @Override
            public void onGetForecast(ForecastRequest forecastWeather) {
                Log.d(TAG, "forecast weather data were received");
                forecastRecycleView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
                forecastRecycleView.setAdapter(new ForecastAdapter(forecastWeather));
            }

            @Override
            public void onErrorGetWeather(Throwable t) {
                Log.d(TAG, "error getting weather data");
            }
        });
    }

    private void saveCityId(WeatherRequest currentWeather) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("city_id", currentWeather.getId());
        editor.commit();
    }

    private void initLocation() {
        Log.d(TAG, "init location");
        myLocation = new MyLocation(MainActivity.this, new MyLocation.OnCoordinatesChanges() {
            @Override
            public void onCoordinatesChanges(Coord coord) {
                openWeatherRequester.requestCurrent(coord.getLat(), coord.getLon());
                openWeatherRequester.requestForecast(coord.getLat(), coord.getLon());
                Toast.makeText(MainActivity.this, "request " + coord.getLat() + ", " + coord.getLon(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (myLocation != null) {
            myLocation.stop();
        }
    }
}
