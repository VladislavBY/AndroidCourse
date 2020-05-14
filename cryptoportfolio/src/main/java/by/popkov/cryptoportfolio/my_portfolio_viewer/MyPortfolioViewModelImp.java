package by.popkov.cryptoportfolio.my_portfolio_viewer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import by.popkov.cryptoportfolio.Coin;

public class MyPortfolioViewModelImp extends ViewModel implements MyPortfolioViewModel {
    private MutableLiveData<Coin> newCoinLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Coin>> coinListLiveData = new MutableLiveData<>();

    @Override
    public LiveData<Coin> getNewCoinLiveData() {
        return newCoinLiveData;
    }

    @Override
    public LiveData<List<Coin>> getCoinListLiveData() {
        return coinListLiveData;
    }

    @Override
    public void setNewCoinLiveData(Coin newCoin) {
        newCoinLiveData.setValue(newCoin);
    }

    @Override
    public void setCoinListLiveData(List<Coin> coinList) {
        coinListLiveData.setValue(coinList);
    }

}
