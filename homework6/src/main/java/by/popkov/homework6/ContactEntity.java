package by.popkov.homework6;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
class ContactEntity {
    ContactEntity(@NonNull String id) {
        this.id = id;
    }

    @PrimaryKey @NonNull
    String id;
    @ColumnInfo
    String name;
    @ColumnInfo
    String data;
    @ColumnInfo
    int imageID;
    @ColumnInfo
    String type;

    @NonNull
    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getData() {
        return data;
    }

    int getImageID() {
        return imageID;
    }

    String getType() {
        return type;
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
