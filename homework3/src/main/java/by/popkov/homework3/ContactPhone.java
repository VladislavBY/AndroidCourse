package by.popkov.homework3;

import java.io.Serializable;

public class ContactPhone implements Contact, Serializable {
    private String name;
    private String date;
    private int imageID;

    public ContactPhone(String name, String date, int imageID) {
        this.name = name;
        this.date = date;
        this.imageID = imageID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public int getImageID() {
        return imageID;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
