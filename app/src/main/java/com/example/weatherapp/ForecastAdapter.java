package com.example.weatherapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.model.Forecast;
import com.example.weatherapp.model.ForecastRequest;
import com.example.weatherapp.model.Main;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private ForecastRequest forecastRequest;

    public ForecastAdapter(ForecastRequest forecastRequest) {
        this.forecastRequest = forecastRequest;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ForecastViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_forecast_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder forecastViewHolder, int i) {
        forecastViewHolder.setData(forecastRequest.getForecasts()[i]);
    }

    @Override
    public int getItemCount() {
        return forecastRequest.getForecasts().length;
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView time;
        private ImageView icon;
        private TextView temperature;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.forecast_date);
            time = itemView.findViewById(R.id.forecast_time);
            icon = itemView.findViewById(R.id.forecast_item_icon);
            temperature = itemView.findViewById(R.id.forecast_temperature);
        }

        public void setData(Forecast forecast) {
            Calendar calendar = Calendar.getInstance();
            int utcOffset = (calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)) / (60 * 1000) / 60;
            String[] dateTime = forecast.getDt_txt().split("\\s");
            date.setText(dateTime[0]);

            int forecastTime = Integer.parseInt(dateTime[1].substring(0, 2));
            int displayedTime = forecastTime + utcOffset >= 24 ? forecastTime + utcOffset - 24 : forecastTime + utcOffset;
            time.setText(String.format("%02d:00", displayedTime));

            Picasso.get()
                    .load("https://openweathermap.org/img/w/" + forecast.getWeather()[0].getIcon() + ".png")
                    .into(icon);

            temperature.setText(String.format(
                    MyApplication.getAppContext().getResources().getString(R.string.temperature),
                    Main.getTempWithSign(forecast.getMain().getTemp())));
        }
    }
}
