package by.popkov.cryptoportfolio.settings_view;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepository;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepositoryImp;

public class SettingsFragmentViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    SettingsFragmentViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(SettingsFragmentViewModel.class)) {
            return (T) new SettingsFragmentViewModel(getSettingsRepository());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    @NonNull
    private SettingsRepository getSettingsRepository() {
        return new SettingsRepositoryImp(context);
    }
}
