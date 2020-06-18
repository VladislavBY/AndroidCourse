package by.popkov.cryptoportfolio.repositories.database_repository.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import by.popkov.cryptoportfolio.domain.Coin;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface CoinDao {
    @Query("SELECT * FROM coin")
    LiveData<List<CoinEntity>> getAllLiveData();

    @Query("SELECT * FROM coin")
    List<CoinEntity> getAll();

    @Query("SELECT * FROM coin")
    Flowable<List<CoinEntity>> getAllFlowable();

    @Query("SELECT * FROM coin WHERE symbol =:symbol")
    LiveData<CoinEntity> getCoinLiveData(String symbol);

    @Query("SELECT * FROM coin WHERE symbol =:symbol")
    CoinEntity getCoin(String symbol);

    @Query("SELECT * FROM coin WHERE symbol =:symbol")
    Flowable<CoinEntity> getCoinFlowable(String symbol);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CoinEntity coinEntity);

    @Update
    void update(CoinEntity coinEntity);

    @Delete
    void delete(CoinEntity coinEntity);
}
