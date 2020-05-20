package by.popkov.cryptoportfolio.repositories.data_base_repository.database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import by.popkov.cryptoportfolio.Coin;

public interface CoinDao {
    @Query("SELECT * FROM coin")
    LiveData<List<Coin>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CoinEntity coinEntity);

    @Update
    void update(CoinEntity coinEntity);

    @Delete
    void delete(CoinEntity coinEntity);
}
