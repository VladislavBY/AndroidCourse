package by.popkov.homework8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.TimeZone;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ItemViewHolder> {
    private List<WeatherApiForecastListObject> weatherApiForecastList;
    private Context context;
    private String unitsSign;

    String getUnitsSign() {
        return unitsSign;
    }

    void setUnitsSign(String unitsSign) {
        this.unitsSign = unitsSign;
    }

    List<WeatherApiForecastListObject> getWeatherApiForecastList() {
        return weatherApiForecastList;
    }

    void setWeatherApiForecastList(List<WeatherApiForecastListObject> weatherApiForecastList) {
        this.weatherApiForecastList = new ArrayList<>(weatherApiForecastList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
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
            String icon = weatherApiForecastListObject.getWeatherApiListWeather().get(0).getIcon();
            weatherIconImageView.setImageResource(
                    context.getResources().getIdentifier(
                            "weather" + icon,
                            "drawable",
                            context.getPackageName()
                    )
            );
            weatherTextView.setText(weatherApiForecastListObject.getWeatherApiListWeather().get(0).getMain());
            tempTextView.setText((Math.round(weatherApiForecastListObject.getWeatherApiMain().getTemp())) + unitsSign);
            timeTextView.setText(convertTimeToLocalTimeZone(weatherApiForecastListObject.getDt() * 1000L));
        }

        private CharSequence convertTimeToLocalTimeZone(Long time) {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTime(new Date(time));
            return calendar.getTime().toString().subSequence(11, 16);
        }

    }
}
