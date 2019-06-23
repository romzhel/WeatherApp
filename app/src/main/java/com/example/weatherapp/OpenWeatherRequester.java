package com.example.weatherapp;

import android.util.Log;

import com.example.weatherapp.model.ForecastRequest;
import com.example.weatherapp.model.WeatherRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherRequester {
    public static final String TAG = "myApp";
    public static final String APP_ID = AppId.APP_ID;
    public static final String PARAMETERS_TYPE = "metric";
    public static final String LANGUAGE = "ru";
    private OpenWeather openWeather;
    private OnGetWeather listener;

    public OpenWeatherRequester(OnGetWeather listener) {
        this.listener = listener;
        initRetrofit();
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeather = retrofit.create(OpenWeather.class);
    }

    public void requestCurrent(String city) {
        openWeather.loadWeather(city, PARAMETERS_TYPE, LANGUAGE, APP_ID)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null) {
                            Log.d(TAG, "weather data were received");
                            listener.onGetCurrent(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        Log.d(TAG, "error " + t.getMessage());
                        listener.onErrorGetWeather(t);
                    }
                });
    }

    public void requestForecast(String city) {
        openWeather.loadForecast(city, PARAMETERS_TYPE, LANGUAGE, APP_ID)
                .enqueue(new Callback<ForecastRequest>() {
                    @Override
                    public void onResponse(Call<ForecastRequest> call, Response<ForecastRequest> response) {
                        if (response.body() != null) {
                            Log.d(TAG, "weather data were received");
                            listener.onGetForecast(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ForecastRequest> call, Throwable t) {
                        Log.d(TAG, "error " + t.getMessage());
                        listener.onErrorGetWeather(t);
                    }
                });
    }

    public void requestCurrent(long cityId) {
        openWeather.loadWeather(cityId, PARAMETERS_TYPE, LANGUAGE, APP_ID)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null) {
                            Log.d(TAG, "weather data were received");
                            listener.onGetCurrent(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        Log.d(TAG, "error " + t.getMessage());
                        listener.onErrorGetWeather(t);
                    }
                });
    }

    public void requestForecast(long cityId) {
        openWeather.loadForecast(cityId, PARAMETERS_TYPE, LANGUAGE, APP_ID)
                .enqueue(new Callback<ForecastRequest>() {
                    @Override
                    public void onResponse(Call<ForecastRequest> call, Response<ForecastRequest> response) {
                        if (response.body() != null) {
                            Log.d(TAG, "weather data were received");
                            listener.onGetForecast(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ForecastRequest> call, Throwable t) {
                        Log.d(TAG, "error " + t.getMessage());
                        listener.onErrorGetWeather(t);
                    }
                });
    }

    public void requestCurrent(double lat, double lon) {
        openWeather.loadWeather(lat, lon, PARAMETERS_TYPE, LANGUAGE, APP_ID)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null) {
                            Log.d(TAG, "weather data were received");
                            listener.onGetCurrent(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        Log.d(TAG, "error " + t.getMessage());
                        listener.onErrorGetWeather(t);
                    }
                });
    }

    public void requestForecast(double lat, double lon) {
        openWeather.loadForecast(lat, lon, PARAMETERS_TYPE, LANGUAGE, APP_ID)
                .enqueue(new Callback<ForecastRequest>() {
                    @Override
                    public void onResponse(Call<ForecastRequest> call, Response<ForecastRequest> response) {
                        if (response.body() != null) {
                            Log.d(TAG, "weather data were received");
                            listener.onGetForecast(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ForecastRequest> call, Throwable t) {
                        Log.d(TAG, "error " + t.getMessage());
                        listener.onErrorGetWeather(t);
                    }
                });
    }


    public interface OnGetWeather {
        void onGetCurrent(WeatherRequest currentWeather);

        void onGetForecast(ForecastRequest forecastWeather);

        void onErrorGetWeather(Throwable t);
    }
}
