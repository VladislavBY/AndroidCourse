package by.popkov.homework5;

import java.io.Serializable;

class Song implements Serializable {
    private int id;
    private String name;
    private boolean playing;

    boolean isPlaying() {
        return playing;
    }

    void setPlaying(boolean playing) {
        this.playing = playing;
    }

    Song(int id, String name) {
        this.id = id;
        this.name = name;
    }

    int getId() {
        return id;
    }


    String getName() {
        return name;
    }


}
