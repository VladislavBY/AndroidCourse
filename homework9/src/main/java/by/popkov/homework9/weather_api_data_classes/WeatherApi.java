package by.popkov.homework9.weather_api_data_classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherApi {
    @SerializedName("weather")
    private List<WeatherApiListWeather> weatherApiListWeather;

    @SerializedName("main")
    private WeatherApiMain weatherApiMain;

    @SerializedName("dt")
    private long dt;

    public WeatherApiMain getWeatherApiMain() {
        return weatherApiMain;
    }

    public List<WeatherApiListWeather> getWeatherApiListWeather() {
        return weatherApiListWeather;
    }

    public long getDt() {
        return dt;
    }
}
