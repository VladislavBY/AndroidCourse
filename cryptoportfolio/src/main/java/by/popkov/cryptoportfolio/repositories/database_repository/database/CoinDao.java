package by.popkov.cryptoportfolio.repositories.database_repository.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CoinDao {
    @Query("SELECT * FROM coin")
    LiveData<List<CoinEntity>> getAllLiveData();

    @Query("SELECT * FROM coin")
    List<CoinEntity> getAll();

    @Query("SELECT * FROM coin WHERE symbol =:symbol")
    CoinEntity getCoin(String symbol);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CoinEntity coinEntity);

    @Update
    void update(CoinEntity coinEntity);

    @Delete
    void delete(CoinEntity coinEntity);
}
