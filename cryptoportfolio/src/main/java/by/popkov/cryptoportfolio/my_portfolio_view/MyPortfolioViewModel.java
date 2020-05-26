package by.popkov.cryptoportfolio.my_portfolio_view;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MyPortfolioViewModel extends AndroidViewModel {

    private ApiRepository apiRepository;
    private DatabaseRepository databaseRepository;
    private MutableLiveData<List<CoinForView>> coinForViewListMutableLiveData = new MutableLiveData<>();

    public MyPortfolioViewModel(@NonNull Application application, ApiRepository apiRepository, DatabaseRepository databaseRepository) {
        super(application);
        this.apiRepository = apiRepository;
        this.databaseRepository = databaseRepository;
    }

    public LiveData<List<CoinForView>> fetchCoin() {
        databaseRepository.getCoinList()
                .observe(
                        getApplication(), coins -> apiRepository.getCoinsList(coins, "USD")
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(this::setCoinForViewListLiveData)
                );
        return coinForViewListMutableLiveData;
    }

    public void setCoinForViewListLiveData(List<Coin> coinList) {
        List<CoinForView> coinForViews = coinList.stream().map(new CoinForViewMapper()).collect(Collectors.toList());
        coinForViewListMutableLiveData.setValue(coinForViews);
    }

    public void saveCoin(String symbol, Double number) {
        databaseRepository.addNewCoin(new Coin.Builder(symbol, number).build());
    }
}
