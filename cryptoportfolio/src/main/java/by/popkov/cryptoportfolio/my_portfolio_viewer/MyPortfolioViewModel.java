package by.popkov.cryptoportfolio.my_portfolio_viewer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import by.popkov.cryptoportfolio.Coin;

public class MyPortfolioViewModel extends ViewModel {
    private MutableLiveData<Coin> newCoinLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Coin>> coinListLiveData = new MutableLiveData<>();

    public LiveData<Coin> getNewCoinLiveData() {
        return newCoinLiveData;
    }

    public LiveData<List<Coin>> getCoinListLiveData() {
        return coinListLiveData;
    }

    public void setNewCoinLiveData(Coin newCoin) {
        newCoinLiveData.setValue(newCoin);
    }

    public void setCoinListLiveData(List<Coin> coinList) {
        coinListLiveData.setValue(coinList);
    }
}
