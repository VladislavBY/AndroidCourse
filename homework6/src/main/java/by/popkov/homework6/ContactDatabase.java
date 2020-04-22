package by.popkov.homework6;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ContactEntity.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactDao getContactDao();
}
