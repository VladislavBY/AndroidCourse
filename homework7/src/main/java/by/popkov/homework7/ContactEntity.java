package by.popkov.homework7;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
class ContactEntity {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String data;
    private int imageID;
    private String type;

    ContactEntity(@NonNull String id) {
        this.id = id;
    }

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

    void setName(String name) {
        this.name = name;
    }

    void setData(String data) {
        this.data = data;
    }

    void setImageID(int imageID) {
        this.imageID = imageID;
    }

    void setType(String type) {
        this.type = type;
    }
}
