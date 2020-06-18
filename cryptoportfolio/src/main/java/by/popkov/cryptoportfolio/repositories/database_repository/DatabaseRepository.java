package by.popkov.cryptoportfolio.repositories.database_repository;


import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Future;

import by.popkov.cryptoportfolio.domain.Coin;
import io.reactivex.Observable;

public interface DatabaseRepository {
    LiveData<List<Coin>> getCoinListLiveData();
    Future<List<Coin>> getCoinListFuture();
    Observable<List<Coin>> getCoinListFlowable();
    LiveData<Coin> getCoinLiveData(String symbol);
    Future <Coin> getCoin(String symbol);
    Observable<Coin> getCoinListFlowable(String symbol);
    void addNewCoin(final Coin coin);
    void deleteCoin(final Coin coin);
    void updateCoin(final Coin coin);
}
