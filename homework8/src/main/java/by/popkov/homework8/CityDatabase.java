package by.popkov.homework8;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CityEntity.class}, version = 1)
abstract class CityDatabase extends RoomDatabase {
    abstract CityDao getCityDao();
}
