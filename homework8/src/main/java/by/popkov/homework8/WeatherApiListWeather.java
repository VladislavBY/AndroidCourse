package by.popkov.homework8;

import com.google.gson.annotations.SerializedName;

class WeatherApiListWeather {
    @SerializedName("main")
    private String main;
    @SerializedName("icon")
    private String icon;

    String getMain() {
        return main;
    }

    String getIcon() {
        return icon;
    }
}
