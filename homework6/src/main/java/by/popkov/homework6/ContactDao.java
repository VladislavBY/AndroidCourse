package by.popkov.homework6;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContact(ContactEntity... contacts);

    @Delete
    void deleteContact(ContactEntity... contacts);

    @Update
    void updateContact(ContactEntity... users);

    @Query("SELECT * FROM contactentity")
    ContactEntity[] loadAddContacts();

    @Query("SELECT * FROM contactentity WHERE id = :id")
    ContactEntity loadContact(String id);
}
