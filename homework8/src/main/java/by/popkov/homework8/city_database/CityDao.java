package by.popkov.homework8.city_database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertCity(CityEntity... cityEntity);

    @Delete
    void deleteCity(CityEntity... cityEntity);

    @Update
    void updateCity(CityEntity... cityEntity);

    @Query("SELECT * FROM CityEntity")
    CityEntity[] loadAllCity();

    @Query("SELECT * FROM CityEntity WHERE name = :cityName")
    CityEntity loadCity(String cityName);
}
