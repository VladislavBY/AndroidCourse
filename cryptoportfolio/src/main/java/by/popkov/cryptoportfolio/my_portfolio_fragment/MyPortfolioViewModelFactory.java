package by.popkov.cryptoportfolio.my_portfolio_fragment;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.function.Function;

import by.popkov.cryptoportfolio.data_classes.CoinForView;
import by.popkov.cryptoportfolio.data_classes.CoinForViewMapper;
import by.popkov.cryptoportfolio.data_classes.PortfolioInfo;
import by.popkov.cryptoportfolio.data_classes.PortfolioInfoForView;
import by.popkov.cryptoportfolio.data_classes.PortfolioInfoForViewMapper;
import by.popkov.cryptoportfolio.data_classes.PortfolioInfoMapper;
import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.domain.CoinMapper;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepositoryImp;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepositoryImp;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepository;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepositoryImp;

public class MyPortfolioViewModelFactory implements ViewModelProvider.Factory {
    private Context context;
    private Application application;

    MyPortfolioViewModelFactory(Application application, Context context) {
        this.context = context;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(MyPortfolioViewModel.class)) {
            return (T) new MyPortfolioViewModel(
                    application,
                    getApiRepository(),
                    getDatabaseRepository(),
                    getSettingsRepository(),
                    getCoinForViewMapper(),
                    getPortfolioInfoMapper(),
                    getPortfolioInfoForViewMapper()
            );
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
    private Function<Coin, CoinForView> getCoinForViewMapper() {
        return new CoinForViewMapper();
    }

    @NonNull
    private Function<List<Coin>, PortfolioInfo> getPortfolioInfoMapper() {
        return new PortfolioInfoMapper();
    }

    @NonNull
    private Function<PortfolioInfo, PortfolioInfoForView> getPortfolioInfoForViewMapper() {
        return new PortfolioInfoForViewMapper();
    }
}
