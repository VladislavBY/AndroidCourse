package by.popkov.cryptoportfolio.settings_view;

import androidx.lifecycle.ViewModel;

import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepository;

class SettingsFragmentViewModel extends ViewModel {
    private SettingsRepository sharedPreferences;

    SettingsFragmentViewModel(SettingsRepository sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
}
