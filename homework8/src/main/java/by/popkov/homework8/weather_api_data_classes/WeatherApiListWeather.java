package by.popkov.homework8.weather_api_data_classes;

import com.google.gson.annotations.SerializedName;

public class WeatherApiListWeather {
    @SerializedName("main")
    private String main;
    @SerializedName("icon")
    private String icon;

    public String getMain() {
        return main;
    }

    public String getIcon() {
        return icon;
    }

    private int iconRes;

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
