package by.popkov.cryptoportfolio.repositories.data_base_repository;

import java.util.List;

import by.popkov.cryptoportfolio.Coin;

public interface DataBaseRepository {
    interface onResultListener {
        void onResult(List<Coin> coinList);
    }

    void getCoinList(onResultListener onResultListener);
}
