package by.popkov.cryptoportfolio.repositories.database_repository;


import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Future;

import by.popkov.cryptoportfolio.domain.Coin;

public interface DatabaseRepository {
    LiveData<List<Coin>> getCoinList();
    Future<List<Coin>> getCoinListFuture();
    void addNewCoin(final Coin coin);
    void deleteCoin(final Coin coin);
    void updateCoin(final Coin coin);
}
