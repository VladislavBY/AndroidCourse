package by.popkov.homework3;

import java.io.Serializable;

interface Contact extends Serializable {
    String getName();

    String getDate();

    int getImageID();

    void setName(String name);

    void setDate(String date);

    void setImageID(int imageID);
}
