package by.popkov.homework8.city_database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class CityEntity {
    @PrimaryKey
    @NonNull
    private String name;

    @NonNull
    public String getName() {
        return name;
    }

    public CityEntity(@NonNull String name) {
        this.name = name;
    }
}
