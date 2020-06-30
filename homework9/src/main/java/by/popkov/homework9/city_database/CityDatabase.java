package by.popkov.homework9.city_database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CityEntity.class}, version = 1)
public abstract class CityDatabase extends RoomDatabase {
    private static volatile CityDatabase INSTANCE;
    private static final String CITY_DATABASE_NAME = "db_cites";
    private static final int CORE_NUMBER = Runtime.getRuntime().availableProcessors();

    public static CityDatabase getInstance(@NonNull final Context context) {
        if (INSTANCE == null) {
            synchronized (CityDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, CityDatabase.class, CITY_DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private ExecutorService executorService = Executors.newFixedThreadPool(CORE_NUMBER);

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public abstract CityDao getCityDao();
}
