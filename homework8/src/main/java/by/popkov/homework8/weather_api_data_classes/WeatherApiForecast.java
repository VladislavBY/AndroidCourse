package by.popkov.homework8.weather_api_data_classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherApiForecast {
    @SerializedName("list")
    private List<WeatherApiForecastListObject> weatherApiForecastList;

    public List<WeatherApiForecastListObject> getWeatherApiForecastList() {
        return weatherApiForecastList;
    }
}