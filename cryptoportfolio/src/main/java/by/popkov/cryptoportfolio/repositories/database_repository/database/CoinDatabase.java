package by.popkov.cryptoportfolio.repositories.database_repository.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CoinEntity.class}, version = 1)
public abstract class CoinDatabase extends RoomDatabase {
    static private volatile CoinDatabase INSTANCE;
    private static final String CITY_DATABASE_NAME = "db_coin";
    private static final int CORE_NUMBER = Runtime.getRuntime().availableProcessors();
    private ExecutorService executorService = Executors.newFixedThreadPool(CORE_NUMBER);


    public static CoinDatabase getInstance(@NonNull final Context context) {
        if (INSTANCE == null) {
            synchronized (CoinDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, CoinDatabase.class, CITY_DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract CoinDao getCoinDao();

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
