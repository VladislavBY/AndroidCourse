package by.popkov.homework8.weather_api_data_classes;

import com.google.gson.annotations.SerializedName;

public class WeatherApiMain {
    @SerializedName("temp")
    private double temp;

    public double getTemp() {
        return temp;
    }
}
