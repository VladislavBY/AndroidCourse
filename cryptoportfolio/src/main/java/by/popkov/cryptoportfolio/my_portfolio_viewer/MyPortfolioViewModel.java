package by.popkov.cryptoportfolio.my_portfolio_viewer;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.popkov.cryptoportfolio.Coin;

public interface MyPortfolioViewModel {
    LiveData<Coin> getNewCoinLiveData();

    LiveData<List<Coin>> getCoinListLiveData();

    void setNewCoinLiveData(Coin newCoin);

    void setCoinListLiveData(List<Coin> coinList);
}
