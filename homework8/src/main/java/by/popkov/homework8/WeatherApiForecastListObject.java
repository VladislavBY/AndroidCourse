package by.popkov.homework8;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class WeatherApiForecastListObject {
    @SerializedName("dt")
    private int dt;
    @SerializedName("main")
    private WeatherApiMain weatherApiMain;
    @SerializedName("weather")
    private List<WeatherApiListWeather> weatherApiListWeather;

    int getDt() {
        return dt;
    }

    WeatherApiMain getWeatherApiMain() {
        return weatherApiMain;
    }

    List<WeatherApiListWeather> getWeatherApiListWeather() {
        return weatherApiListWeather;
    }
}
