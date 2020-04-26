package by.popkov.homework6;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
class ContactEntity {
    @PrimaryKey
    @NonNull
    String id;
    @ColumnInfo
    String name;
    @ColumnInfo
    String data;
    @ColumnInfo
    int imageID;
    @ColumnInfo
    String type;

    ContactEntity() {
    }

    private ContactEntity(@NonNull String id, String name, String data, int imageID, String type) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.imageID = imageID;
        this.type = type;
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

    static class Builder {
        private String id;
        private String name;
        private String data;
        private int imageID;
        private String type;

        Builder(String id) {
            this.id = id;
        }

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        Builder setData(String data) {
            this.data = data;
            return this;
        }

        Builder setImageID(int imageID) {
            this.imageID = imageID;
            return this;
        }

        Builder setType(String type) {
            this.type = type;
            return this;
        }

        ContactEntity build() {
            return new ContactEntity(id, name, data, imageID, type);
        }
    }
}
