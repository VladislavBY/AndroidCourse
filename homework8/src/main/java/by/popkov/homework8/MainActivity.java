package by.popkov.homework8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements OnClickButtonListener {
    private static final String API_WEATHER_NOW = "https://samples.openweathermap.org/data/2.5/find?q=%s&units=%s&appid=%s";
    private static final String API_KEY = "a179821de4f14533abfde5b6ae9204b0";
    static final String UNITS_IMPERIAL = "imperial";
    static final String UNITS_METRIC = "metric";

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null){
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new MainFragment(), "MainFragment")
                    .commit();
        }
    }

    @Override
    public void onSettingsButtonClick() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment(), "SettingsFragment")
                .addToBackStack("SettingsFragment")
                .commit();
    }

    @Override
    public void onCitySelectButtonClick() {

    }
}
