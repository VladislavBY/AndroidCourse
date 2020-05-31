package by.popkov.cryptoportfolio.my_portfolio_view;

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

public class MyPortfolioViewModelFactory implements ViewModelProvider.Factory {
    private Context context;
    private Application application;

    MyPortfolioViewModelFactory(Application application, Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(MyPortfolioViewModel.class)) {
            return (T) new MyPortfolioViewModel(application, getApiRepository(), getDatabaseRepository());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    private ApiRepository getApiRepository() {
        return new ApiRepositoryImp();
    }

    private DatabaseRepository getDatabaseRepository() {
        return new DatabaseRepositoryImp(context, new CoinMapper());
    }
}
