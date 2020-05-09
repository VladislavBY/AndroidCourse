package by.popkov.homework8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    static final String UNITS = "UNITS";

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

    void showViewsWithCurrentSettings() {
        String cityName = sharedPreferences.getString(MainActivity.SELECTED_CITY_KEY, "London");
        String units = sharedPreferences.getString(MainActivity.CELSIUS_CHECKED_KEY, MainActivity.UNITS_METRIC);
        setUnitsSign(units);
        showWeatherNow(cityName, units);
        showWeatherForecast(cityName, units);
    }

    private void setListeners() {
        if (onClickButtonListener != null) {
            settingsImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickButtonListener.onSettingsButtonClick();
                }
            });
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickButtonListener.onCitySelectButtonClick();
                }
            });
        }

    }

    private void setUnitsSign(String units) {
        if (units.equals(MainFragment.UNITS_METRIC)) {
            unitsSing = context.getString(R.string.UNITS_METRIC);
        } else unitsSing = context.getString(R.string.UNITS_IMPERIAL);
    }

    private void makeRecyclerView() {
        predictionRecyclerView.setAdapter(new MainFragmentAdapter());
        predictionRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        mainFragmentAdapter = (MainFragmentAdapter) predictionRecyclerView.getAdapter();
    }

    private void showWeatherForecast(String cityName, final String units) {
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
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mainFragmentAdapter.setUnitsSign(unitsSing);
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
        settingsImageButton = view.findViewById(R.id.settingsImageButton);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
    }

    private void showWeatherNow(final String cityName, final String units) {
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
                            setWeatherView(weatherNow, cityName, units);
                        }
                    });

                }
            }
        });
    }

    private void setWeatherView(WeatherApi weatherNow, String cityName, String units) {
        cityTextView.setText(cityName);
        tempTextView.setText(Math.round(weatherNow.getWeatherApiMain().getTemp()) + unitsSing);
        weatherMainTextView.setText(weatherNow.getWeatherApiListWeather().get(0).getMain());
        String icon = weatherNow.getWeatherApiListWeather().get(0).getIcon();
        weatherMainImageView.setImageResource(getResources().getIdentifier("weather" + icon, "drawable", context.getPackageName()));
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
