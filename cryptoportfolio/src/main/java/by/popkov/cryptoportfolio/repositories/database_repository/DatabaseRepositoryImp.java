package by.popkov.cryptoportfolio.repositories.database_repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.stream.Collectors;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.repositories.database_repository.database.CoinDao;
import by.popkov.cryptoportfolio.repositories.database_repository.database.CoinDatabase;
import by.popkov.cryptoportfolio.repositories.database_repository.database.CoinEntity;

public class DatabaseRepositoryImp implements DatabaseRepository {
    private CoinDao coinDao;
    private ExecutorService executorService;
    private LiveData<List<CoinEntity>> coinEntityListLiveData;
    private Function<CoinEntity, Coin> mapper;

    public DatabaseRepositoryImp(@NonNull final Context context, Function<CoinEntity, Coin> mapper) {
        CoinDatabase coinDatabase = CoinDatabase.getInstance(context);
        this.coinDao = coinDatabase.getCoinDao();
        this.executorService = coinDatabase.getExecutorService();
        this.mapper = mapper;
    }

    @Override
    public LiveData<List<Coin>> getCoinList() {
        if (coinEntityListLiveData == null) {
            coinEntityListLiveData = coinDao.getAllLiveData();
        }
        return Transformations.map(coinEntityListLiveData, input -> input
                .stream()
                .map(mapper)
                .collect(Collectors.toList()));
    }

    @Override
    public Future<List<Coin>> getCoinListFuture() {
        return executorService.submit(() -> coinDao.getAll()
                .stream()
                .map(mapper)
                .collect(Collectors.toList()));
    }

    @Override
    public Future<Coin> getCoin(String symbol) {
        return executorService.submit(() -> mapper.apply(coinDao.getCoin(symbol)));
    }

    @Override
    public void addNewCoin(Coin coin) {
        executorService.execute(() -> coinDao.insert(new CoinEntity(coin.getSymbol(), coin.getNumber())));
    }

    @Override
    public void deleteCoin(Coin coin) {
        executorService.execute(() -> coinDao.delete(new CoinEntity(coin.getSymbol(), coin.getNumber())));
    }

    @Override
    public void updateCoin(Coin coin) {
        executorService.execute(() -> coinDao.update(new CoinEntity(coin.getSymbol(), coin.getNumber())));
    }
}
