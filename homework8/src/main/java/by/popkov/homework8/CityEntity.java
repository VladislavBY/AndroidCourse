package by.popkov.homework8;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name"}, unique = true)})
class CityEntity {
    @PrimaryKey
    @NonNull
    String name;

    @NonNull
    String getName() {
        return name;
    }

    CityEntity setName(@NonNull String name) {
        this.name = name;
        return this;
    }
}
