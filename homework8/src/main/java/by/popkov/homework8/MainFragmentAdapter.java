package by.popkov.homework8;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Locale;

import by.popkov.homework8.weather_api_data_classes.WeatherApiForecastListObject;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ItemViewHolder> {
    private List<WeatherApiForecastListObject> weatherApiForecastList;
    private String unitsSign;

    void setUnitsSign(String unitsSign) {
        this.unitsSign = unitsSign;
    }

    void setWeatherApiForecastList(List<WeatherApiForecastListObject> weatherApiForecastList) {
        this.weatherApiForecastList = new ArrayList<>(weatherApiForecastList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_activity_main, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindData(weatherApiForecastList.get(position));
    }

    @Override
    public int getItemCount() {
        if (weatherApiForecastList != null) {
            return weatherApiForecastList.size();
        } else return 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView weatherIconImageView;
        private TextView timeTextView;
        private TextView weatherTextView;
        private TextView tempTextView;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherIconImageView = itemView.findViewById(R.id.weatherIconImageView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            weatherTextView = itemView.findViewById(R.id.weatherTextView);
            tempTextView = itemView.findViewById(R.id.tempTextView);
        }

        private void bindData(WeatherApiForecastListObject weatherApiForecastListObject) {
            weatherIconImageView.setImageResource(weatherApiForecastListObject.getWeatherApiListWeather().get(0).getIconRes());
            weatherTextView.setText(weatherApiForecastListObject.getWeatherApiListWeather().get(0).getMain());
            tempTextView.setText((Math.round(weatherApiForecastListObject.getWeatherApiMain().getTemp())) + unitsSign);
            timeTextView.setText(convertTimeToLocalTimeZone(weatherApiForecastListObject.getDt() * 1000L));
        }

        private String convertTimeToLocalTimeZone(Long time) {
            return new SimpleDateFormat("EEE HH:mm", Locale.getDefault()).format(new Date(time));
        }
    }
}
