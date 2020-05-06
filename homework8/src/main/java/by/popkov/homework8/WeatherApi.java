package by.popkov.homework8;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class WeatherApi {
    @SerializedName("list")
    private List<WeatherApiList> weatherApiList;

    List<WeatherApiList> getWeatherApiList() {
        return weatherApiList;
    }
}
