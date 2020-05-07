package by.popkov.homework8;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class WeatherApi {
    @SerializedName("weather")
    private List<WeatherApiListWeather> weatherApiListWeather;

    @SerializedName("main")
    private WeatherApiMain weatherApiMain;

    WeatherApiMain getWeatherApiMain() {
        return weatherApiMain;
    }

    List<WeatherApiListWeather> getWeatherApiListWeather() {
        return weatherApiListWeather;
    }
}
