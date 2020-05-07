package by.popkov.homework8;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainFragment extends Fragment {

    private static final String API_WEATHER_NOW = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=%s&appid=%s";
    private static final String API_WEATHER_FORECAST = "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=%s&appid=%s";

    private static final String API_KEY = "a179821de4f14533abfde5b6ae9204b0";
    static final String UNITS_IMPERIAL = "imperial";
    static final String UNITS_METRIC = "metric";

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Context context;
    private MainFragmentAdapter mainFragmentAdapter;

    private ImageView weatherMainImageView;
    private TextView cityTextView;
    private TextView tempTextView;
    private TextView weatherMainTextView;
    private RecyclerView predictionRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        if (context != null) {
            showWeatherNow("London", UNITS_METRIC);
            showWeatherForecast("London", UNITS_METRIC);
        }
        initViews(view);
        makeRecyclerView();
    }

    private void makeRecyclerView() {
        predictionRecyclerView.setAdapter(new MainFragmentAdapter(context));
        predictionRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        mainFragmentAdapter = (MainFragmentAdapter) predictionRecyclerView.getAdapter();
    }

    private void showWeatherForecast(String cityName, String units) {
        Request request = new Request.Builder()
                .url(String.format(API_WEATHER_FORECAST, cityName, units, API_KEY))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getContext(), getString(R.string.no_connection), Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (response.isSuccessful() && body != null) {
                    String json = body.string();
                    Type type = new TypeToken<WeatherApiForecast>() {
                    }.getType();
                    final WeatherApiForecast weatherForecast = new Gson().fromJson(json, type);
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mainFragmentAdapter.setWeatherApiForecastList(weatherForecast.getWeatherApiForecastList());
                        }
                    });
                }
            }
        });
    }

    private void initViews(View view) {
        weatherMainImageView = view.findViewById(R.id.weatherMainImageView);
        cityTextView = view.findViewById(R.id.cityTextView);
        tempTextView = view.findViewById(R.id.tempTextView);
        weatherMainTextView = view.findViewById(R.id.weatherMainTextView);
        predictionRecyclerView = view.findViewById(R.id.predictionRecyclerView);
    }

    private void showWeatherNow(final String cityName, String units) {
        Request request = new Request.Builder()
                .url(String.format(API_WEATHER_NOW, cityName, units, API_KEY))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getContext(), getString(R.string.no_connection), Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (response.isSuccessful() && body != null) {
                    String json = body.string();
                    Type type = new TypeToken<WeatherApi>() {
                    }.getType();
                    final WeatherApi weatherNow = new Gson().fromJson(json, type);
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            setWeatherView(weatherNow, cityName);
                        }
                    });

                }
            }
        });
    }

    private void setWeatherView(WeatherApi weatherNow, String cityName) {
        cityTextView.setText(cityName);
        tempTextView.setText(String.valueOf(weatherNow.getWeatherApiMain().getTemp()));
        weatherMainTextView.setText(weatherNow.getWeatherApiListWeather().get(0).getMain());
        String icon = weatherNow.getWeatherApiListWeather().get(0).getIcon();
        weatherMainImageView.setImageResource(getResources().getIdentifier("weather" + icon, "drawable", context.getPackageName()));
    }
}
