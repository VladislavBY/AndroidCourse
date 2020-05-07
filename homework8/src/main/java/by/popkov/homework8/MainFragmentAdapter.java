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
import java.util.List;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ItemViewHolder> {
    private List<WeatherApiForecastListObject> weatherApiForecastList;
    private Context context;

    public List<WeatherApiForecastListObject> getWeatherApiForecastList() {
        return weatherApiForecastList;
    }

    public void setWeatherApiForecastList(List<WeatherApiForecastListObject> weatherApiForecastList) {
        this.weatherApiForecastList = new ArrayList<>(weatherApiForecastList);
        notifyDataSetChanged();
    }

    MainFragmentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity_main, parent, false);
        return new ItemViewHolder(view);
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
            tempTextView.setText(weatherApiForecastListObject.getWeatherApiListWeather().get(0).getMain());
        }
    }
}
