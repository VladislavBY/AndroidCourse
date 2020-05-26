package by.popkov.cryptoportfolio.repositories.database_repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.repositories.database_repository.database.CoinDao;
import by.popkov.cryptoportfolio.repositories.database_repository.database.CoinDatabase;
import by.popkov.cryptoportfolio.repositories.database_repository.database.CoinEntity;

public class DatabaseRepositoryImp implements DatabaseRepository {
    private CoinDao coinDao;
    private ExecutorService executorService;
    private LiveData<List<CoinEntity>> coinEntityListLiveData;

    public DatabaseRepositoryImp(@NonNull final Context context) {
        CoinDatabase coinDatabase = CoinDatabase.getInstance(context);
        coinDao = coinDatabase.getCoinDao();
        executorService = coinDatabase.getExecutorService();
    }

    @Override
    public LiveData<List<Coin>> getCoinList() {
        if (coinEntityListLiveData == null) {
            coinEntityListLiveData = coinDao.getAll();
        }
        return Transformations.map(coinEntityListLiveData, coinEntityList -> coinEntityList.stream()
                .map(coinEntity -> new Coin.Builder(coinEntity.getSymbol(), coinEntity.getNumber())
                        .build()
                ).collect(Collectors.toList())
        );
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
