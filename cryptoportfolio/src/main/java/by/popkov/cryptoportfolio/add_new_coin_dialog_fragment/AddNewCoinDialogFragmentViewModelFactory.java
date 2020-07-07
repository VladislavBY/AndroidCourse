package by.popkov.cryptoportfolio.add_new_coin_dialog_fragment;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import by.popkov.cryptoportfolio.domain.CoinMapper;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepositoryImp;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepositoryImp;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepository;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepositoryImp;

public class AddNewCoinDialogFragmentViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private Context context;

    public AddNewCoinDialogFragmentViewModelFactory(Application application, Context context) {
        this.application = application;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(AddNewCoinDialogFragmentViewModel.class)) {
            return (T) new AddNewCoinDialogFragmentViewModel(application, getApiRepository(), getDatabaseRepository(), getSettingsRepository());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    @NonNull
    private ApiRepository getApiRepository() {
        return new ApiRepositoryImp();
    }

    @NonNull
    private DatabaseRepository getDatabaseRepository() {
        return new DatabaseRepositoryImp(context, new CoinMapper());
    }

    @NonNull
    private SettingsRepository getSettingsRepository() {
        return new SettingsRepositoryImp(context);
    }

}
