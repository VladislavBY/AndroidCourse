package by.popkov.homework8;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class WeatherApiList {
    @SerializedName("dt")
    private int dt;
    @SerializedName("main")
    private WeatherApiListMain weatherApiListMain;
    @SerializedName("weather")
    private List<WeatherApiListWeather> weatherApiListWeather;

    public int getDt() {
        return dt;
    }

    WeatherApiListMain getWeatherApiListMain() {
        return weatherApiListMain;
    }

    List<WeatherApiListWeather> getWeatherApiListWeather() {
        return weatherApiListWeather;
    }
}
