package by.popkov.homework8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    static final String CELSIUS_CHECKED_KEY = "CELSIUS_CHECKED";
    static final String FRAGMENT_TAG = "SettingsFragment";

    private Switch switchCelsius;
    private SharedPreferences sharedPreferences;

    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        if (context != null) {
            switchCelsius = view.findViewById(R.id.switchCelsius);
            setCheckedStatusFromSettings();
            setOnCheckedChangeListener();
        }
    }

    private void setCheckedStatusFromSettings() {
        sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String pos = sharedPreferences.getString(CELSIUS_CHECKED_KEY, MainFragment.UNITS_METRIC);
        if (pos.equals(MainFragment.UNITS_IMPERIAL)) {
            switchCelsius.setChecked(false);
        }
    }

    private void setOnCheckedChangeListener() {
        switchCelsius.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (isChecked) {
                editor.putString(CELSIUS_CHECKED_KEY, MainFragment.UNITS_METRIC);
            } else editor.putString(CELSIUS_CHECKED_KEY, MainFragment.UNITS_IMPERIAL);
            editor.apply();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        context = null;
        sharedPreferences = null;
    }
}
