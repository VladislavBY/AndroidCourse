package by.popkov.cryptoportfolio.coin_info_view;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.data_classes.CoinForView;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

class CoinInfoFragmentViewModel extends ViewModel {
    interface ShowThrowable {
        void show(Throwable throwable);
    }

    private ApiRepository apiRepository;
    private DatabaseRepository databaseRepository;
    private SettingsRepository settingsRepository;
    private Function<Coin, CoinForView> mapper;
    private CoinForView coinForView;
    private MutableLiveData<CoinForView> coinForViewMutableLiveData = new MutableLiveData<>();

    CoinInfoFragmentViewModel(
            CoinForView coinForView,
            ApiRepository apiRepository,
            DatabaseRepository databaseRepository,
            SettingsRepository settingsRepository,
            Function<Coin, CoinForView> mapper
    ) {
        this.apiRepository = apiRepository;
        this.databaseRepository = databaseRepository;
        this.settingsRepository = settingsRepository;
        this.mapper = mapper;
        this.coinForView = coinForView;
        connectToRepo();
    }

    private void setCoinForViewMutableLiveData(CoinForView coinForView) {
        coinForViewMutableLiveData.setValue(coinForView);
    }

    LiveData<CoinForView> getCoinForViewLiveData() {
        return coinForViewMutableLiveData;
    }

    void refreshCoinData(@NotNull ShowThrowable showThrowable) {
        try {
            Coin currentCoinDatabase = databaseRepository.getCoin(coinForView.getSymbol()).get();
            apiRepository.getCoin(currentCoinDatabase, settingsRepository.getFiatSetting())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(coin -> mapper.apply(coin))
                    .subscribe(this::setCoinForViewMutableLiveData, showThrowable::show);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("CheckResult")
    private void connectToRepo() {
        databaseRepository.getCoinObservable(coinForView.getSymbol()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        coin -> apiRepository.getCoin(coin, settingsRepository.getFiatSetting())
                                .map(coin1 -> mapper.apply(coin1))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(this::setCoinForViewMutableLiveData)
                );
    }

    void updateCoin(Double number) {
        databaseRepository.updateCoin(new Coin.Builder(coinForView.getSymbol(), number).build());
    }

    void deleteCoin() {
        databaseRepository.deleteCoin(new Coin.Builder(coinForView.getSymbol(), 0.0).build());
    }

}
