package by.popkov.homework8;

import com.google.gson.annotations.SerializedName;

class WeatherApiMain {
    @SerializedName("temp")
    private double temp;

    double getTemp() {
        return temp;
    }
}
