package by.popkov.cryptoportfolio.my_portfolio_view;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import by.popkov.cryptoportfolio.data_classes.PortfolioInfo;
import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.data_classes.CoinForView;
import by.popkov.cryptoportfolio.data_classes.PortfolioInfoForView;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepository;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepositoryImp;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

class MyPortfolioViewModel extends ViewModel {
    interface ShowThrowable {
        void show(Throwable throwable);
    }

    private ApiRepository apiRepository;
    private DatabaseRepository databaseRepository;
    private SettingsRepository settingsRepository;
    private Function<Coin, CoinForView> coinForViewMapper;
    private Function<List<Coin>, PortfolioInfo> portfolioInfoMapper;
    private Function<PortfolioInfo, PortfolioInfoForView> portfolioInfoForViewMapper;
    private MutableLiveData<List<CoinForView>> coinForViewListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PortfolioInfoForView> portfolioInfoForViewMutableLiveData = new MutableLiveData<>();

    MyPortfolioViewModel(
            ApiRepository apiRepository,
            DatabaseRepository databaseRepository,
            SettingsRepository settingsRepository,
            Function<Coin, CoinForView> coinForViewMapper,
            Function<List<Coin>, PortfolioInfo> portfolioInfoMapper,
            Function<PortfolioInfo, PortfolioInfoForView> portfolioInfoForViewMapper
    ) {
        this.apiRepository = apiRepository;
        this.databaseRepository = databaseRepository;
        this.settingsRepository = settingsRepository;
        this.coinForViewMapper = coinForViewMapper;
        this.portfolioInfoMapper = portfolioInfoMapper;
        this.portfolioInfoForViewMapper = portfolioInfoForViewMapper;
    }

    LiveData<List<CoinForView>> getCoinForViewListLiveData() {
        return coinForViewListMutableLiveData;
    }

    LiveData<PortfolioInfoForView> getPortfolioInfoForViewLiveData() {
        return portfolioInfoForViewMutableLiveData;
    }

    void saveCoin(@NotNull String symbol, String number, @NotNull ShowThrowable showThrowable) {
        Coin coin = new Coin.Builder(symbol.toUpperCase().trim(), Double.valueOf(number)).build();
        apiRepository.getCoin(coin, settingsRepository.getFiatSetting())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coin1 -> databaseRepository.addNewCoin(coin), showThrowable::show);
    }

    void updateCoinList(@NotNull ShowThrowable showThrowable) {
        try {
            List<Coin> currentCoinListDatabase = databaseRepository.getCoinListFuture().get();
            apiRepository.getCoinsList(currentCoinListDatabase, settingsRepository.getFiatSetting())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(coinList -> {
                        setCoinForViewListLiveData(coinList);
                        setPortfolioInfoForViewMutableLiveData(coinList);
                    }, showThrowable::show);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    void connectToRepo(LifecycleOwner lifecycleOwner, ShowThrowable showThrowable) {
        databaseRepository.getCoinListLiveData().observe(
                lifecycleOwner, coins -> apiRepository.getCoinsList(coins, settingsRepository.getFiatSetting())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(coinList -> {
                            setCoinForViewListLiveData(coinList);
                            setPortfolioInfoForViewMutableLiveData(coinList);
                        }, showThrowable::show)
        );
    }


    private void setCoinForViewListLiveData(@NotNull List<Coin> coinList) {
        coinForViewListMutableLiveData
                .setValue(
                        coinList.stream()
                                .sorted(getComparator(settingsRepository.getSortSetting()))
                                .map(coinForViewMapper)
                                .collect(Collectors.toList())
                );
    }

    private Comparator<? super Coin> getComparator(@NotNull String sortSetting) {
        switch (sortSetting) {
            case SettingsRepositoryImp.ALPHABET_SORT:
                return (Comparator<Coin>) (o1, o2) -> o1.getSymbol().compareToIgnoreCase(o2.getSymbol());
            case SettingsRepositoryImp.SUM_SORT:
                return (Comparator<Coin>) (o1, o2) -> {
                    if (o1.getPrise() != null && o2.getPrise() != null) {
                        Double sumOne = o1.getPrise() * o1.getNumber();
                        Double sumTwo = o2.getPrise() * o2.getNumber();
                        return sumOne.compareTo(sumTwo);
                    } else return 0;
                };
            case SettingsRepositoryImp.TIME_ADD_SORT:
            default: return (Comparator<Coin>) (o1, o2) -> 0;
        }
    }

    private void setPortfolioInfoForViewMutableLiveData(List<Coin> coinList) {
        portfolioInfoForViewMutableLiveData
                .setValue(portfolioInfoForViewMapper.apply(portfolioInfoMapper.apply(coinList)));
    }
}
