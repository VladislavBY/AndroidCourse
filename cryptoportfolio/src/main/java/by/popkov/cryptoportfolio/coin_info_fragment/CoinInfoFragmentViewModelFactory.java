package by.popkov.cryptoportfolio.coin_info_fragment;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.function.Function;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.domain.CoinMapper;
import by.popkov.cryptoportfolio.data_classes.CoinForView;
import by.popkov.cryptoportfolio.data_classes.CoinForViewMapper;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepositoryImp;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepositoryImp;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepository;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepositoryImp;

public class CoinInfoFragmentViewModelFactory implements ViewModelProvider.Factory {
    private Context context;
    private Application application;
    private CoinForView coinForView;

    CoinInfoFragmentViewModelFactory(CoinForView coinForView, Application application, Context context) {
        this.context = context;
        this.coinForView = coinForView;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(CoinInfoFragmentViewModel.class)) {
            return (T) new CoinInfoFragmentViewModel(application, coinForView, getApiRepository(),
                    getDatabaseRepository(), getSettingsRepository(), getMapper());
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

    @NonNull
    private Function<Coin, CoinForView> getMapper() {
        return new CoinForViewMapper();
    }
}
