package by.popkov.homework9.weather_api_data_classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherApiForecastListObject {
    @SerializedName("dt")
    private int dt;
    @SerializedName("main")
    private WeatherApiMain weatherApiMain;
    @SerializedName("weather")
    private List<WeatherApiListWeather> weatherApiListWeather;

    public int getDt() {
        return dt;
    }

    public WeatherApiMain getWeatherApiMain() {
        return weatherApiMain;
    }

    public List<WeatherApiListWeather> getWeatherApiListWeather() {
        return weatherApiListWeather;
    }
}
