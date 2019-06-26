package com.example.weatherapp;

import com.example.weatherapp.model.ForecastRequest;
import com.example.weatherapp.model.WeatherRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String cityCountry, @Query("units") String type, @Query("lang") String lang, @Query("appid") String keyApi);

    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("id") long cityId, @Query("units") String type, @Query("lang") String lang, @Query("appid") String keyApi);

    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("lat") double lat, @Query("lon") double lon, @Query("units") String type, @Query("lang") String lang, @Query("appid") String keyApi);

    @GET("data/2.5/forecast")
    Call<ForecastRequest> loadForecast(@Query("q") String cityCountry, @Query("units") String type, @Query("lang") String lang, @Query("appid") String keyApi);

    @GET("data/2.5/forecast")
    Call<ForecastRequest> loadForecast(@Query("id") long cityId, @Query("units") String type, @Query("lang") String lang, @Query("appid") String keyApi);

    @GET("data/2.5/forecast")
    Call<ForecastRequest> loadForecast(@Query("lat") double lat, @Query("lon") double lon, @Query("units") String type, @Query("lang") String lang, @Query("appid") String keyApi);
}
