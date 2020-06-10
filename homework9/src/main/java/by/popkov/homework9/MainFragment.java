package by.popkov.homework9;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import by.popkov.homework9.weather_api_data_classes.WeatherApi;
import by.popkov.homework9.weather_api_data_classes.WeatherApiForecast;
import by.popkov.homework9.weather_api_data_classes.WeatherApiForecastListObject;
import by.popkov.homework9.weather_api_data_classes.WeatherApiListWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainFragment extends Fragment {

    static final String FRAGMENT_TAG = "MainFragment";
    private static final String API_WEATHER_NOW = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=%s&appid=%s";
    private static final String API_WEATHER_FORECAST = "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=%s&appid=%s";
    private static final String API_KEY = "a179821de4f14533abfde5b6ae9204b0";
    private static final String DEFAULT_CITY = "London";
    private static final String DEFAULT_UNITS = "metric";

    private String unitsSing;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Context context;
    private OnClickButtonListener onClickButtonListener;
    private MainFragmentAdapter mainFragmentAdapter;
    private SharedPreferences sharedPreferences;

    private ImageView weatherMainImageView;
    private TextView cityTextView;
    private TextView tempTextView;
    private TextView weatherMainTextView;
    private RecyclerView predictionRecyclerView;
    private ImageButton settingsImageButton;
    private FloatingActionButton floatingActionButton;
    private ProgressBar progressBar;

    interface OnClickButtonListener {
        void onSettingsButtonClick();

        void onCitySelectButtonClick();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnClickButtonListener) {
            onClickButtonListener = (OnClickButtonListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        initViews(view);
        if (context != null) {
            sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
            setListeners();
            showViewsWithCurrentSettings();
        }
        makeRecyclerView();
    }

    private void initViews(View view) {
        progressBar = view.findViewById(R.id.progressBar);
        weatherMainImageView = view.findViewById(R.id.weatherMainImageView);
        cityTextView = view.findViewById(R.id.cityTextView);
        tempTextView = view.findViewById(R.id.tempTextView);
        weatherMainTextView = view.findViewById(R.id.weatherMainTextView);
        predictionRecyclerView = view.findViewById(R.id.predictionRecyclerView);
        settingsImageButton = view.findViewById(R.id.settingsImageButton);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
    }

    private void setListeners() {
        if (onClickButtonListener != null) {
            settingsImageButton.setOnClickListener(v -> onClickButtonListener.onSettingsButtonClick());
            floatingActionButton.setOnClickListener(v -> onClickButtonListener.onCitySelectButtonClick());
        }
    }

    private void showViewsWithCurrentSettings() {
        String cityName = sharedPreferences.getString(CityFragment.SELECTED_CITY_KEY, DEFAULT_CITY);
        String units = sharedPreferences.getString(SettingsFragment.SELECTED_UNITS, DEFAULT_UNITS);
        setUnitsSign(units);
        showWeatherNow(cityName, units);
        showWeatherForecast(cityName, units);
    }

    private void setUnitsSign(String units) {
        if (units.equals(SettingsFragment.UNITS_METRIC)) {
            unitsSing = context.getString(R.string.UNITS_METRIC);
        } else unitsSing = context.getString(R.string.UNITS_IMPERIAL);
    }

    private void showWeatherNow(final String cityName, final String units) {
        Request request = new Request.Builder()
                .url(String.format(API_WEATHER_NOW, cityName, units, API_KEY))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                new Handler(context.getMainLooper()).post(() ->
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (response.isSuccessful() && body != null) {
                    String json = body.string();
                    Type type = new TypeToken<WeatherApi>() {
                    }.getType();
                    final WeatherApi weatherNow = new Gson().fromJson(json, type);
                    new Handler(context.getMainLooper()).post(() -> setNowWeatherView(weatherNow, cityName));

                }
            }
        });
    }

    private void setNowWeatherView(WeatherApi weatherNow, String cityName) {
        cityTextView.setText(cityName);
        tempTextView.setText(Math.round(weatherNow.getWeatherApiMain().getTemp()) + unitsSing);
        weatherMainTextView.setText(weatherNow.getWeatherApiListWeather().get(0).getMain());
        String icon = weatherNow.getWeatherApiListWeather().get(0).getIcon();
        weatherMainImageView.setImageResource(getResources().getIdentifier("weather" + icon, "drawable", context.getPackageName()));
    }

    private void showWeatherForecast(String cityName, final String units) {
        Request request = new Request.Builder()
                .url(String.format(API_WEATHER_FORECAST, cityName, units, API_KEY))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                new Handler(context.getMainLooper()).post(() ->
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (response.isSuccessful() && body != null) {
                    String json = body.string();
                    Type type = new TypeToken<WeatherApiForecast>() {
                    }.getType();
                    final WeatherApiForecast weatherForecast = new Gson().fromJson(json, type);
                    generateIconId(weatherForecast);
                    new Handler(context.getMainLooper()).post(() -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        mainFragmentAdapter.setUnitsSign(unitsSing);
                        mainFragmentAdapter.setWeatherApiForecastList(weatherForecast.getWeatherApiForecastList());
                    });
                }
            }
        });
    }

    private void generateIconId(WeatherApiForecast weatherForecast) {
        for (WeatherApiForecastListObject weatherApiForecastListObject : weatherForecast.getWeatherApiForecastList()) {
            WeatherApiListWeather weather = weatherApiForecastListObject.getWeatherApiListWeather().get(0);
            String icon = weather.getIcon();
            weather.setIconRes(context.getResources()
                    .getIdentifier("weather" + icon,
                            "drawable",
                            context.getPackageName()
                    )
            );
        }
    }

    private void makeRecyclerView() {
        predictionRecyclerView.setAdapter(new MainFragmentAdapter());
        predictionRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        mainFragmentAdapter = (MainFragmentAdapter) predictionRecyclerView.getAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        context = null;
        sharedPreferences = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onClickButtonListener = null;
    }
}
