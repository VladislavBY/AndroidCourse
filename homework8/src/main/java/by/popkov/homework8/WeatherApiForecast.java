package by.popkov.homework8;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class WeatherApiForecast {
    @SerializedName("list")
    private List<WeatherApiForecastListObject> weatherApiForecastList;

    List<WeatherApiForecastListObject> getWeatherApiForecastList() {
        return weatherApiForecastList;
    }
}