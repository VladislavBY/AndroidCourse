package by.popkov.homework8;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

class MainFragment extends Fragment {
    private static final String API_WEATHER_NOW = "https://samples.openweathermap.org/data/2.5/find?q=%s&units=%s&appid=%s";
    private static final String API_KEY = "a179821de4f14533abfde5b6ae9204b0";
    static final String UNITS_IMPERIAL = "imperial";
    static final String UNITS_METRIC = "metric";

    private OkHttpClient okHttpClient = new OkHttpClient();

    private ImageView weatherMainImageView;
    private TextView cityTextView;
    private TextView tempTextView;
    private TextView weatherMainTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        showWeatherNow("London", UNITS_METRIC);
    }

    private void initViews(View view) {
        weatherMainImageView = view.findViewById(R.id.weatherMainImageView);
        cityTextView = view.findViewById(R.id.cityTextView);
        tempTextView = view.findViewById(R.id.tempTextView);
        weatherMainTextView = view.findViewById(R.id.weatherMainTextView);
    }

    private void showWeatherNow(final String cityName, String units) {
        Request request = new Request.Builder()
                .url(String.format(API_WEATHER_NOW, cityName, UNITS_METRIC, units))
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
                    if (getContext() != null) {
                        new Handler(getContext().getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                setWeatherView(weatherNow, cityName);
                            }
                        });
                    }
                }
            }
        });
    }

    private void setWeatherView(WeatherApi weatherNow, String cityName) {
        cityTextView.setText(cityName);
        tempTextView.setText(String.valueOf(weatherNow.getWeatherApiList().get(0).getWeatherApiListMain().getTemp()));
        weatherMainTextView.setText(weatherNow.getWeatherApiList().get(0).getWeatherApiListWeather().get(0).getMain());
        String icon = weatherNow.getWeatherApiList().get(0).getWeatherApiListWeather().get(0).getIcon();
        weatherMainImageView.setImageResource(getResources().getIdentifier("weather" + icon, "drawable", getContext().getPackageName()));
    }
}
