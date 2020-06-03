package by.popkov.cryptoportfolio.coin_info_view;

import androidx.lifecycle.ViewModel;

import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;

public class CoinInfoFragmentViewModel extends ViewModel {
    private ApiRepository apiRepository;
    private DatabaseRepository databaseRepository;

    CoinInfoFragmentViewModel(ApiRepository apiRepository, DatabaseRepository databaseRepository) {
        this.apiRepository = apiRepository;
        this.databaseRepository = databaseRepository;
    }
}
