package by.popkov.homework6;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
public class ContactEntity {
    ContactEntity(@NonNull String id) {
        this.id = id;
    }

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

    ContactEntity setId(@NonNull String id) {
        this.id = id;
        return this;
    }

    ContactEntity setName(String name) {
        this.name = name;
        return this;
    }

    ContactEntity setData(String data) {
        this.data = data;
        return this;
    }

    ContactEntity setImageID(int imageID) {
        this.imageID = imageID;
        return this;
    }

    ContactEntity setType(String type) {
        this.type = type;
        return this;
    }
}
