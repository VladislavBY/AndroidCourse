package by.popkov.homework7;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ContactEntity.class}, version = 1)
abstract class ContactDatabase extends RoomDatabase {
    private static final String CONTACT_DATABASE_NAME = "db_contacts";
    private static final int CORE_NUMBER = Runtime.getRuntime().availableProcessors();
    private static volatile ContactDatabase INSTANCE;

    static ContactDatabase getInstance(@NonNull final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ContactDatabase.class, CONTACT_DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private ExecutorService executorService = Executors.newFixedThreadPool(CORE_NUMBER);

    ExecutorService getExecutorService() {
        return executorService;
    }

    abstract ContactDao getContactDao();
}
