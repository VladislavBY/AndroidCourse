package by.popkov.homework6;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertContact(ContactEntity... contacts);

    @Delete
    public void deleteContact(ContactEntity... contacts);

    @Update
    public void updateContact(ContactEntity... users);

    @Query("SELECT * FROM contactentity")
    public ContactEntity[] loadAddContacts();

    @Query("SELECT * FROM contactentity WHERE id = :id")
    public ContactEntity loadContact(String id);
}
