package by.popkov.homework8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragment.OnClickButtonListener {
    static final String SHARED_PREFERENCES_NAME = "SETTINGS";


    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        showMainFragment(savedInstanceState);

    }

    private void showMainFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new MainFragment(), MainFragment.FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onSettingsButtonClick() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment(), SettingsFragment.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCitySelectButtonClick() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new CityFragment(), CityFragment.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }
}
