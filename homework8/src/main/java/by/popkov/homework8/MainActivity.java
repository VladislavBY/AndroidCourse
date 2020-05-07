package by.popkov.homework8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String API_WEATHER_NOW = "https://samples.openweathermap.org/data/2.5/find?q=%s&units=%s&appid=%s";
    private static final String API_KEY = "a179821de4f14533abfde5b6ae9204b0";
    static final String UNITS_IMPERIAL = "imperial";
    static final String UNITS_METRIC = "metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new MainFragment(), "Main")
                .commit();
    }
}
