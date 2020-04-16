package by.popkov.homework5;

import java.io.Serializable;

public class Song implements Serializable {
    private int id;
    private String name;

    Song(int id, String name) {
        this.id = id;
        this.name = name;
    }

    int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
