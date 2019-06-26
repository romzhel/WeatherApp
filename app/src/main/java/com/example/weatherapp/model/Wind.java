package com.example.weatherapp.model;

import com.example.weatherapp.MyApplication;
import com.example.weatherapp.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    @Expose
    private float speed;
    @SerializedName("deg")
    @Expose
    private float deg;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDeg() {
        return deg;
    }

    public void setDeg(float deg) {
        this.deg = deg;
    }

    public static String getWindDirection(float degree) {
        if (degree > 350 || degree <= 10) {
            return MyApplication.getAppContext().getResources().getString(R.string.wind_north);
        } else if (degree > 10 && degree <= 80) {
            return MyApplication.getAppContext().getResources().getString(R.string.wind_north_east);
        } else if (degree > 80 && degree <= 100) {
            return MyApplication.getAppContext().getResources().getString(R.string.wind_east);
        } else if (degree > 100 && degree <= 170) {
            return MyApplication.getAppContext().getResources().getString(R.string.wind_south_east);
        } else if (degree > 170 && degree <= 190) {
            return MyApplication.getAppContext().getResources().getString(R.string.wind_south);
        } else if (degree > 190 && degree <= 250) {
            return MyApplication.getAppContext().getResources().getString(R.string.wind_south_west);
        } else if (degree > 250 && degree <= 280) {
            return MyApplication.getAppContext().getResources().getString(R.string.wind_west);
        } else if (degree > 280 && degree <= 350) {
            return MyApplication.getAppContext().getResources().getString(R.string.wind_north_west);
        }
        return "";
    }
}
