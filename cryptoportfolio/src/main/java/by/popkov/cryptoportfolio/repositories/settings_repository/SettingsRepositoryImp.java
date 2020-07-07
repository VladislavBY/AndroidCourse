package by.popkov.cryptoportfolio.repositories.settings_repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepositoryImp;

public class SettingsRepositoryImp implements SettingsRepository {

    public static final String ALPHABET_SORT = "ALPHABET_SORT";
    public static final String SUM_SORT = "SUM_SORT";
    public static final String TIME_ADD_SORT = "TIME_ADD_SORT";

    private static final String SORT_TYPE_KEY = "SORT_TYPE_KEY";
    private static final String DEFAULT_SORT = TIME_ADD_SORT;

    private static final String SETTINGS_SHARED_PREFERENCES_NAME = "settingsSharedPreferencesName";
    private static final String FIAT_SYMBOL_KEY = "FIAT_SYMBOL_KEY";
    private static final String DEFAULT_FIAT = ApiRepositoryImp.USD;

    private SharedPreferences sharedPreferences;

    public SettingsRepositoryImp(@NonNull Context context) {
        this.sharedPreferences = context.getSharedPreferences(SETTINGS_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Save symbol to convert
     *
     * @param fiatSymbol from {@link ApiRepositoryImp}
     */
    @Override
    public void saveFiatSetting(String fiatSymbol) {
        sharedPreferences.edit()
                .putString(FIAT_SYMBOL_KEY, fiatSymbol)
                .apply();
    }

    @Override
    public String getFiatSetting() {
        return sharedPreferences.getString(FIAT_SYMBOL_KEY, DEFAULT_FIAT);
    }

    @Override
    public void saveSortSetting(String sortType) {
        sharedPreferences.edit()
                .putString(SORT_TYPE_KEY, sortType)
                .apply();
    }

    @Override
    public String getSortSetting() {
        return sharedPreferences.getString(SORT_TYPE_KEY, DEFAULT_SORT);
    }
}
