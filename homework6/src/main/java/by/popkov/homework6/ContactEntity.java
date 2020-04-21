package by.popkov.homework6;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
public class ContactEntity {
    @PrimaryKey @NonNull
    public String id;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public String data;
    @ColumnInfo
    public int imageID;
    @ColumnInfo
    public String type;
}
