package by.popkov.homework3;

import java.io.Serializable;

interface Contact extends Serializable {
    String getName();

    String getData();

    int getImageID();

    void setName(String name);

    void setData(String data);

    void setImageID(int imageID);
}
