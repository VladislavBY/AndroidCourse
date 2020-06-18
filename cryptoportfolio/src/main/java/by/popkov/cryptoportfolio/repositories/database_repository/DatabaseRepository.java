package by.popkov.cryptoportfolio.repositories.database_repository;


import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Future;

import by.popkov.cryptoportfolio.domain.Coin;
import io.reactivex.rxjava3.core.Flowable;

public interface DatabaseRepository {
    LiveData<List<Coin>> getCoinListLiveData();
    Future<List<Coin>> getCoinListFuture();
    Flowable<List<Coin>> getCoinListFlowable();
    LiveData<Coin> getCoinLiveData(String symbol);
    Future <Coin> getCoin(String symbol);
    Flowable<Coin> getCoinListFlowable(String symbol);
    void addNewCoin(final Coin coin);
    void deleteCoin(final Coin coin);
    void updateCoin(final Coin coin);
}
