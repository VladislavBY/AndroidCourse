package by.popkov.cryptoportfolio.repositories.database_repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.repositories.database_repository.database.CoinDao;
import by.popkov.cryptoportfolio.repositories.database_repository.database.CoinDatabase;
import by.popkov.cryptoportfolio.repositories.database_repository.database.CoinEntity;
import io.reactivex.Observable;

public class DatabaseRepositoryImp implements DatabaseRepository {
    private CoinDao coinDao;
    private ExecutorService executorService;
    private LiveData<List<CoinEntity>> coinEntityListLiveData;
    private LiveData<CoinEntity> coinEntityLiveData;
    private Function<CoinEntity, Coin> mapper;

    public DatabaseRepositoryImp(@NonNull final Context context, Function<CoinEntity, Coin> mapper) {
        CoinDatabase coinDatabase = CoinDatabase.getInstance(context);
        this.coinDao = coinDatabase.getCoinDao();
        this.executorService = coinDatabase.getExecutorService();
        this.mapper = mapper;
    }

    @Override
    public LiveData<List<Coin>> getCoinListLiveData() {
        if (coinEntityListLiveData == null) {
            coinEntityListLiveData = coinDao.getAllLiveData();
        }
        return Transformations.map(coinEntityListLiveData, input -> input
                .stream()
                .map(mapper)
                .collect(Collectors.toList()));
    }

    @Override
    public LiveData<Coin> getCoinLiveData(String id) {
        if (coinEntityLiveData == null) {
            coinEntityLiveData = coinDao.getCoinLiveData(id);
        }
        return Transformations.map(coinEntityLiveData, input -> mapper.apply(input));
    }

    @Override
    public Future<List<Coin>> getCoinListFuture() {
        return executorService.submit(() -> coinDao.getAll()
                .stream()
                .map(mapper)
                .collect(Collectors.toList()));
    }

    @Override
    public Future<Coin> getCoin(String id) {
        return executorService.submit(() -> mapper.apply(coinDao.getCoin(id)));
    }

    @Override
    public Observable<List<Coin>> getCoinListObservable() {
        return coinDao.getAllObservable().map(coinEntities ->
                coinEntities.stream()
                        .map(mapper)
                        .collect(Collectors.toList()));
    }

    @Override
    public Observable<Coin> getCoinObservable(String id) {
        return coinDao.getCoinObservable(id).map(mapper::apply);
    }

    @Override
    public void addNewCoin(Coin coin) {
        executorService.execute(() -> coinDao.insert(new CoinEntity(coin.getId(), coin.getSymbol(), coin.getNumber())));
    }

    @Override
    public void deleteCoin(Coin coin) {
        executorService.execute(() -> coinDao.delete(new CoinEntity(coin.getId(), coin.getSymbol(), coin.getNumber())));
    }

    @Override
    public void updateCoin(Coin coin) {
        executorService.execute(() -> coinDao.update(new CoinEntity(coin.getId(), coin.getSymbol(), coin.getNumber())));
    }
}
