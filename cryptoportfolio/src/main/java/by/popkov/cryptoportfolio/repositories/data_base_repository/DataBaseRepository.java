package by.popkov.cryptoportfolio.repositories.data_base_repository;


import androidx.lifecycle.LiveData;

import java.util.List;

import by.popkov.cryptoportfolio.Coin;

public interface DataBaseRepository {
    LiveData<List<Coin>> getCoinList();
    void addNewCoin(final Coin coin);
    void deleteCoin(final Coin coin);
    void updateCoin(final Coin coin);
}
