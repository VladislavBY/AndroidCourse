package by.popkov.cryptoportfolio.coin_info_view;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.function.Function;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.domain.CoinMapper;
import by.popkov.cryptoportfolio.my_portfolio_view.CoinForView;
import by.popkov.cryptoportfolio.my_portfolio_view.CoinForViewMapper;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepositoryImp;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepositoryImp;

public class CoinInfoFragmentViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    CoinInfoFragmentViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(CoinInfoFragmentViewModel.class)) {
            return (T) new CoinInfoFragmentViewModel(getApiRepository(), getDatabaseRepository(), getMapper());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    private ApiRepository getApiRepository() {
        return new ApiRepositoryImp();
    }

    private DatabaseRepository getDatabaseRepository() {
        return new DatabaseRepositoryImp(context, new CoinMapper());
    }

    private Function<Coin, CoinForView> getMapper() {
        return new CoinForViewMapper();
    }
}
