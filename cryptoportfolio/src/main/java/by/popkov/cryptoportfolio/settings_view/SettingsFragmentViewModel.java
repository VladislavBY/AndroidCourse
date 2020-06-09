package by.popkov.cryptoportfolio.settings_view;

import androidx.lifecycle.ViewModel;

import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepository;

class SettingsFragmentViewModel extends ViewModel {
    private SettingsRepository settingsRepository;

    SettingsFragmentViewModel(SettingsRepository sharedPreferences) {
        this.settingsRepository = sharedPreferences;
    }

    String getFiatSettings() {
        return settingsRepository.getFiatSetting();
    }

    void saveFiatSetting(String fiatSymbol) {
        settingsRepository.saveFiatSetting(fiatSymbol);
    }

}
