package by.popkov.cryptoportfolio.coin_info_view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.my_portfolio_view.CoinForView;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

class CoinInfoFragmentViewModel extends ViewModel {
    private ApiRepository apiRepository;
    private DatabaseRepository databaseRepository;
    private MutableLiveData<CoinForView> coinForViewMutableLiveData = new MutableLiveData<>();
    private Function<Coin, CoinForView> mapper;

    CoinInfoFragmentViewModel(ApiRepository apiRepository, DatabaseRepository databaseRepository, Function<Coin, CoinForView> mapper) {
        this.apiRepository = apiRepository;
        this.databaseRepository = databaseRepository;
        this.mapper = mapper;
    }

    void setCoinForViewMutableLiveData(CoinForView coinForView) {
        coinForViewMutableLiveData.setValue(coinForView);
    }

    LiveData<CoinForView> getCoinForViewLiveData() {
        return coinForViewMutableLiveData;
    }

    void updateCoinData() {
        CoinForView value = coinForViewMutableLiveData.getValue();
        if (value != null) {
            try {
                Coin currentCoinDatabase = databaseRepository.getCoin(value.getSymbol()).get();
                apiRepository.getCoin(currentCoinDatabase, "USD")
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(coin -> mapper.apply(coin))
                        .subscribe(this::setCoinForViewMutableLiveData);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
