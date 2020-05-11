package by.popkov.homework8.city_database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CityEntity.class}, version = 1)
public abstract class CityDatabase extends RoomDatabase {
    public abstract CityDao getCityDao();
}
