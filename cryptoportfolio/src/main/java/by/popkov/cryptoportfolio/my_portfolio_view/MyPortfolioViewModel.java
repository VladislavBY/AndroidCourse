package by.popkov.cryptoportfolio.my_portfolio_view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.stream.Collectors;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

class MyPortfolioViewModel extends AndroidViewModel {

    private ApiRepository apiRepository;
    private DatabaseRepository databaseRepository;
    private LifecycleOwner lifecycleOwner;
    private MutableLiveData<List<CoinForView>> coinForViewListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PortfolioInfoForView> portfolioInfoForViewMutableLiveData = new MutableLiveData<>();

    LiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }

    private void setThrowableMutableLiveData(Throwable throwable) {
        this.throwableMutableLiveData.setValue(throwable);
    }

    private MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();


    MyPortfolioViewModel(Application application, LifecycleOwner lifecycleOwner, ApiRepository apiRepository, DatabaseRepository databaseRepository) {
        super(application);
        this.lifecycleOwner = lifecycleOwner;
        this.apiRepository = apiRepository;
        this.databaseRepository = databaseRepository;
    }

    LiveData<List<CoinForView>> getCoinForViewListLiveData() {
        connectToRepo();
        return coinForViewListMutableLiveData;
    }

    LiveData<PortfolioInfoForView> getPortfolioInfoForViewLiveData() {
        return portfolioInfoForViewMutableLiveData;
    }

    void saveCoin(String symbol, String number) {
        Coin coin = new Coin.Builder(symbol.toUpperCase(), Double.valueOf(number)).build();
        apiRepository.getCoin(coin, "USD")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coin1 -> databaseRepository.addNewCoin(coin), this::setThrowableMutableLiveData);
    }

    void updateCoinList() {
        List<Coin> currentCoinListDatabase = databaseRepository.getCoinList().getValue();
        apiRepository.getCoinsList(currentCoinListDatabase, "USD")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coinList -> {
                    setCoinForViewListLiveData(coinList);
                    setPortfolioInfoForViewMutableLiveData(coinList);
                }, this::setThrowableMutableLiveData);
    }

    private void connectToRepo() {
        databaseRepository.getCoinList().observe(
                lifecycleOwner, coins -> apiRepository.getCoinsList(coins, "USD")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(coinList -> {
                            setCoinForViewListLiveData(coinList);
                            setPortfolioInfoForViewMutableLiveData(coinList);
                        }, this::setThrowableMutableLiveData)
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
