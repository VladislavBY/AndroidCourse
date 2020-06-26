package by.popkov.cryptoportfolio.coin_info_fragment;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.data_classes.CoinForView;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

class CoinInfoFragmentViewModel extends AndroidViewModel {

    private ApiRepository apiRepository;
    private DatabaseRepository databaseRepository;
    private SettingsRepository settingsRepository;
    private Function<Coin, CoinForView> mapper;
    private CoinForView coinForView;
    private MutableLiveData<CoinForView> coinForViewMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();

    CoinInfoFragmentViewModel(
            Application application,
            CoinForView coinForView,
            ApiRepository apiRepository,
            DatabaseRepository databaseRepository,
            SettingsRepository settingsRepository,
            Function<Coin, CoinForView> mapper
    ) {
        super(application);
        this.apiRepository = apiRepository;
        this.databaseRepository = databaseRepository;
        this.settingsRepository = settingsRepository;
        this.mapper = mapper;
        this.coinForView = coinForView;
        connectToRepo();
    }

    LiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingMutableLiveData;
    }

    private void setIsLoadingLiveData(Boolean isLoading) {
        this.isLoadingMutableLiveData.setValue(isLoading);
    }

    private void setCoinForViewMutableLiveData(CoinForView coinForView) {
        coinForViewMutableLiveData.setValue(coinForView);
    }

    LiveData<CoinForView> getCoinForViewLiveData() {
        return coinForViewMutableLiveData;
    }

    void refreshCoinData() {
        setIsLoadingLiveData(true);
        try {
            Coin currentCoinDatabase = databaseRepository.getCoin(coinForView.getSymbol()).get();
            apiRepository.getCoin(currentCoinDatabase, settingsRepository.getFiatSetting())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(coin -> mapper.apply(coin))
                    .subscribe(this::onNextCoinForView, this::onError);
        } catch (ExecutionException | InterruptedException e) {
            onError(e);
        }
    }

    @SuppressLint("CheckResult")
    private void connectToRepo() {
        setIsLoadingLiveData(true);
        databaseRepository.getCoinObservable(coinForView.getSymbol()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        coin -> apiRepository.getCoin(coin, settingsRepository.getFiatSetting())
                                .map(coin1 -> mapper.apply(coin1))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(this::onNextCoinForView, this::onError),
                        this::onError
                );
    }

    private void onNextCoinForView(CoinForView coinForView) {
        setCoinForViewMutableLiveData(coinForView);
        setIsLoadingLiveData(false);
    }

    private void onError(@NotNull Throwable throwable) {
        setIsLoadingLiveData(false);
        showThrowable(throwable);
    }

    private void showThrowable(@NotNull Throwable throwable) {
        Toast.makeText(getApplication(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    void updateCoin(Double number) {
        databaseRepository.updateCoin(new Coin.Builder(coinForView.getId(), coinForView.getSymbol(), number).build());
    }

    void deleteCoin() {
        databaseRepository.deleteCoin(new Coin.Builder(coinForView.getId(), coinForView.getSymbol(), 0.0).build());
    }
}
