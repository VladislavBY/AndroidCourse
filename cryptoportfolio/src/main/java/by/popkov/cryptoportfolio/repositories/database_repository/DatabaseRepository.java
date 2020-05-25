package by.popkov.cryptoportfolio.repositories.database_repository;


import androidx.lifecycle.LiveData;

import java.util.List;

import by.popkov.cryptoportfolio.domain.Coin;

public interface DatabaseRepository {
    LiveData<List<Coin>> getCoinList();
    void addNewCoin(final Coin coin);
    void deleteCoin(final Coin coin);
    void updateCoin(final Coin coin);
}
