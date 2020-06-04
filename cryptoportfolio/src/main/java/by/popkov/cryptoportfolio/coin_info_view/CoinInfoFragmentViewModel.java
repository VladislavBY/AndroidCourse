package by.popkov.cryptoportfolio.coin_info_view;

import androidx.lifecycle.LifecycleOwner;
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
    interface ShowThrowable {
        void show(Throwable throwable);
    }

    private ApiRepository apiRepository;
    private DatabaseRepository databaseRepository;
    private Function<Coin, CoinForView> mapper;
    private CoinForView coinForView;
    private MutableLiveData<CoinForView> coinForViewMutableLiveData = new MutableLiveData<>();

    CoinInfoFragmentViewModel(CoinForView coinForView, ApiRepository apiRepository, DatabaseRepository databaseRepository, Function<Coin, CoinForView> mapper) {
        this.apiRepository = apiRepository;
        this.databaseRepository = databaseRepository;
        this.mapper = mapper;
        this.coinForView = coinForView;
    }
    
    private void setCoinForViewMutableLiveData(CoinForView coinForView) {
        coinForViewMutableLiveData.setValue(coinForView);
    }

    LiveData<CoinForView> getCoinForViewLiveData() {
        return coinForViewMutableLiveData;
    }

    void refreshCoinData(ShowThrowable showThrowable) {
        try {
            Coin currentCoinDatabase = databaseRepository.getCoin(coinForView.getSymbol()).get();
            apiRepository.getCoin(currentCoinDatabase, "USD")
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(coin -> mapper.apply(coin))
                    .subscribe(this::setCoinForViewMutableLiveData, showThrowable::show);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    void connectToRepo(LifecycleOwner lifecycleOwner, ShowThrowable showThrowable) {
        databaseRepository.getCoinLiveData(coinForView.getSymbol()).observe(
                lifecycleOwner, coin -> apiRepository.getCoin(coin, "USD")
                        .map(coin1 -> mapper.apply(coin1))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setCoinForViewMutableLiveData, showThrowable::show)
        );
    }

    void updateCoin(Double number) {
        databaseRepository.updateCoin(new Coin.Builder(coinForView.getSymbol(), number).build());
    }

    void deleteCoin() {
        databaseRepository.deleteCoin(new Coin.Builder(coinForView.getSymbol(), 0.0).build());
    }

}
