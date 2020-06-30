package by.popkov.homework9.widget_weather;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import by.popkov.homework9.MainActivity;
import by.popkov.homework9.R;
import by.popkov.homework9.weather_api_data_classes.WeatherApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.app.PendingIntent.*;
import static by.popkov.homework9.CityFragment.*;
import static by.popkov.homework9.MainActivity.*;
import static by.popkov.homework9.MainFragment.*;
import static by.popkov.homework9.SettingsFragment.*;

public class WeatherWidgetProvider extends AppWidgetProvider {

    public static final String DATE_FORMAT = "dd MMM HH:mm";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        setData(context, appWidgetManager, appWidgetIds);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void setData(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        OkHttpClient okHttpClient = new OkHttpClient();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String city = sharedPreferences.getString(SELECTED_CITY_KEY, DEFAULT_CITY);
        String units = sharedPreferences.getString(SELECTED_UNITS, DEFAULT_UNITS);
        Request request = new Request.Builder().url(String.format(API_WEATHER_NOW, city, units, API_KEY))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                new Handler(context.getMainLooper()).post(() -> Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (response.isSuccessful() && body != null) {
                    String json = body.string();
                    Type type = new TypeToken<WeatherApi>() {
                    }.getType();
                    final WeatherApi weatherNow = new Gson().fromJson(json, type);
                    new Handler(context.getMainLooper()).post(() ->
                            WeatherWidgetProvider.this.setDataToView(context, appWidgetManager, appWidgetIds, weatherNow, city, units));
                }
            }
        });
    }

    private void setDataToView(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, WeatherApi weatherApi, String city, String units) {
        for (int appWidgetId : appWidgetIds) {
            Optional<String> unitsSymbolOptional = Optional.empty();
            if (units.equals(UNITS_METRIC)) {
                unitsSymbolOptional = Optional.of(context.getString(R.string.UNITS_METRIC));
            } else if (units.equals(UNITS_IMPERIAL)) {
                unitsSymbolOptional = Optional.of(context.getString(R.string.UNITS_IMPERIAL));
            }
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_weather);
            remoteViews.setTextViewText(R.id.cityName, String.valueOf(city));
            remoteViews.setTextViewText(R.id.date, getTime(weatherApi.getDt()));
            unitsSymbolOptional.ifPresent(s -> remoteViews.setTextViewText(R.id.weatherNow, weatherApi.getWeatherApiMain().getTemp() + s));
            remoteViews.setImageViewResource(R.id.icon, getDrawableId(context, weatherApi));
            remoteViews.setOnClickPendingIntent(R.id.rootLayout,
                    getActivity(context, 0, new Intent(context, MainActivity.class), FLAG_UPDATE_CURRENT));
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @NotNull
    private String getTime(long dt) {
        return new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date(dt * 1000));
    }

    private int getDrawableId(@NotNull Context context, @NotNull WeatherApi weatherApi) {
        return context.getResources().getIdentifier(
                "weather" + weatherApi.getWeatherApiListWeather().get(0).getIcon(), "drawable", context.getPackageName()
        );
    }
}
