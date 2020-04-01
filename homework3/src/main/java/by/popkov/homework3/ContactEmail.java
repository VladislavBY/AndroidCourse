package by.popkov.homework3;

import java.io.Serializable;

public class ContactEmail implements Contact, Serializable {
    private String name;
    private String data;
    private int imageID;


    public ContactEmail(String name, String date, int imageID) {
        this.name = name;
        this.data = date;
        this.imageID = imageID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getData() {
        return data;
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
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
