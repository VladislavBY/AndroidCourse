package by.popkov.cryptoportfolio;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class Settings {

    private static final String SETTINGS_SHARED_PREFERENCES_NAME = "settingsSharedPreferencesName";
    private static final String FIAT_SYMBOL_KEY = "fiatSymbolKey";
    private static final String DEFAULT_FIAT = "USD";
    private SharedPreferences sharedPreferences;

    public Settings(@NonNull Context context) {
        this.sharedPreferences = context.getSharedPreferences(SETTINGS_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void saveFiatSetting(String fiatSymbol) {
        sharedPreferences.edit()
                .putString(FIAT_SYMBOL_KEY, fiatSymbol)
                .apply();
    }

    public String getFiatSetting() {
        return sharedPreferences.getString(FIAT_SYMBOL_KEY, DEFAULT_FIAT);
    }
}
