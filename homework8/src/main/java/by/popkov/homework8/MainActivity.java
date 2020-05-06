package by.popkov.homework8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity {
    private static final String API_WEATHER_NOW = "https://samples.openweathermap.org/data/2.5/find?q=%s&units=%s&appid=%s";
    private static final String API_KEY = "a179821de4f14533abfde5b6ae9204b0";
    static final String UNITS_IMPERIAL = "imperial";
    static final String UNITS_METRIC = "metric";

    private OkHttpClient okHttpClient = new OkHttpClient();

    private ImageView weatherMainImageView;
    private TextView cityTextView;
    private TextView tempTextView;
    private TextView weatherMainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        showWeatherNow("London", UNITS_METRIC);
    }

    private void initViews() {
        weatherMainImageView = findViewById(R.id.weatherMainImageView);
        cityTextView = findViewById(R.id.cityTextView);
        tempTextView = findViewById(R.id.tempTextView);
        weatherMainTextView = findViewById(R.id.weatherMainTextView);
    }

    private void showWeatherNow(final String cityName, String units) {
        Request request = new Request.Builder()
                .url(String.format(API_WEATHER_NOW, cityName, UNITS_METRIC, units))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(MainActivity.this, getString(R.string.no_connection), Toast.LENGTH_SHORT)
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
                    new Handler(getMainLooper()).post(new Runnable() {
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
        tempTextView.setText(String.valueOf(weatherNow.getWeatherApiList().get(0).getWeatherApiListMain().getTemp()));
        weatherMainTextView.setText(weatherNow.getWeatherApiList().get(0).getWeatherApiListWeather().get(0).getMain());
        String icon = weatherNow.getWeatherApiList().get(0).getWeatherApiListWeather().get(0).getIcon();
        weatherMainImageView.setImageResource(getResources().getIdentifier("weather" + icon, "drawable", getPackageName()));
    }
}
