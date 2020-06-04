package by.popkov.cryptoportfolio.my_portfolio_view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

class MyPortfolioViewModel extends AndroidViewModel {
    interface ShowThrowable {
        void show(Throwable throwable);
    }

    private ApiRepository apiRepository;
    private DatabaseRepository databaseRepository;
    private MutableLiveData<List<CoinForView>> coinForViewListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PortfolioInfoForView> portfolioInfoForViewMutableLiveData = new MutableLiveData<>();

    MyPortfolioViewModel(Application application, ApiRepository apiRepository, DatabaseRepository databaseRepository) {
        super(application);
        this.apiRepository = apiRepository;
        this.databaseRepository = databaseRepository;
    }

    LiveData<List<CoinForView>> getCoinForViewListLiveData() {
        return coinForViewListMutableLiveData;
    }

    LiveData<PortfolioInfoForView> getPortfolioInfoForViewLiveData() {
        return portfolioInfoForViewMutableLiveData;
    }

    void saveCoin(String symbol, String number, ShowThrowable showThrowable) {
        Coin coin = new Coin.Builder(symbol.toUpperCase().trim(), Double.valueOf(number)).build();
        apiRepository.getCoin(coin, "USD")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coin1 -> databaseRepository.addNewCoin(coin), showThrowable::show);
    }

    void updateCoinList(ShowThrowable showThrowable) {
        try {
            List<Coin> currentCoinListDatabase = databaseRepository.getCoinListFuture().get();
            apiRepository.getCoinsList(currentCoinListDatabase, "USD")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(coinList -> {
                        setCoinForViewListLiveData(coinList);
                        setPortfolioInfoForViewMutableLiveData(coinList);
                    }, showThrowable::show);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    void connectToRepo(LifecycleOwner lifecycleOwner, ShowThrowable showThrowable) {
        databaseRepository.getCoinListLiveData().observe(
                lifecycleOwner, coins -> apiRepository.getCoinsList(coins, "USD")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(coinList -> {
                            setCoinForViewListLiveData(coinList);
                            setPortfolioInfoForViewMutableLiveData(coinList);
                        }, showThrowable::show)
        );
    }


    private void setCoinForViewListLiveData(List<Coin> coinList) {
        List<CoinForView> coinForViews = coinList.stream().map(new CoinForViewMapper()).collect(Collectors.toList());
        coinForViewListMutableLiveData.setValue(coinForViews);
    }

    private void setPortfolioInfoForViewMutableLiveData(List<Coin> coinList) {
        portfolioInfoForViewMutableLiveData
                .setValue(PortfolioInfoForViewConverter.convert(PortfolioInfoConverter.convert(coinList)));
    }
}
