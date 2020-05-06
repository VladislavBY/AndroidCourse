package by.popkov.homework8;

import com.google.gson.annotations.SerializedName;

class WeatherApiListMain {
    @SerializedName("temp")
    private double temp;

    public double getTemp() {
        return temp;
    }
}
